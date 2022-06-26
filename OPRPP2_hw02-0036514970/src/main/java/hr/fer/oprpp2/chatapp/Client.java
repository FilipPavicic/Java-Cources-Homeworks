package hr.fer.oprpp2.chatapp;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import java.net.UnknownHostException;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


public class Client extends JFrame{
	private static final long serialVersionUID = 1L;

	ExecutorService pool;

	static int numberSend = 0;
	static int numberRecieve = -1;
	static String ime;


	JPanel jPanel1 = new JPanel();
	JTextField jTextField1 = new JTextField();
	JButton jButton1 = new JButton();
	JScrollPane jScrollPane1 = new JScrollPane();
	static JTextArea chatArea = new JTextArea();
	JLabel jLabel2 = new JLabel();
	JLabel jLabel1 = new JLabel();

	static InetAddress address = null;
	static int port = 0;
	static DatagramSocket socket = null;
	volatile Long UID = null;
	private static final long randkey = new Random().nextLong();

	public Client() {
		super();

		pool = Executors.newFixedThreadPool(2);
		connectToServer();
		listen();
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("Client");
		setLocation(20, 20);
		initGUI();
		setSize(600, 300);
		pack();
	}

	private void connectToServer() {
		long br = numberSend;
		addMessageInSendQueue(() ->new HelloMessage(ime,br,randkey).sendMessage(address, port, socket), () -> addText("[Uspješno spojen na server]"), ()->addText("[Neuspjšno spajanje na server]"),true);
	}

	private Future<?> addMessageInSendQueue(Runnable message,Runnable onSuccess, Runnable onFailure,boolean wait) {
		return pool.submit(new Runnable() {

			@Override
			public void run() {
				
				boolean connected = send(message,wait);
				if(wait == true) numberSend++;
				if(connected == false) {
					onFailure.run();
					return;
				}
				onSuccess.run();

			}


		});

	}
	private boolean send(Runnable message, boolean wait) {
		int counter = 0;
		while(counter < 10) {
			counter++;
			message.run();
			if(wait == false) return true;

			synchronized (this) {
				while(true) {
					try {
						wait(5000);

					} catch (InterruptedException e) {
						continue;
					}break;


				}
			}
			if(numberRecieve == numberSend) {
				return true;
			}
		}
		return false;

	}


	private void listen() {

		pool.submit(new Runnable() {

			@Override
			public void run() {
				listenAndNotify();
			}
		});



	}
	private void listenAndNotify() {
		while(true) {
			DatagramPacket packet = Utils.recieveMessage(address, port, socket);
			switch (packet.getData()[packet.getOffset()]) {
			case AckMessage.CODE: {
				try {
					AckMessage ack = AckMessage.parseDatagramPacket(packet);
					Utils.printRecieve(ack);
					ack.checkACK(numberRecieve + 1, UID);
					UID = ack.UID;
				}catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
					break;
				}
				numberRecieve++;
				synchronized (this) {
					notifyAll();
				}

				break;
			}
			case InmsgMessage.CODE: {
				InmsgMessage inmsg;
				try {
					inmsg = InmsgMessage.parseDatagramPacket(packet);
					Utils.printRecieve(inmsg);
					addText(inmsg.text);
				}catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
					break;
				}
				addMessageInSendQueue(() -> new AckMessage(inmsg.number, UID).sendMessage(address, port, socket),()->{}, () ->{}, false );
				break;

			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + packet.getData()[packet.getOffset()]);
			}
		}
	};




	private void initGUI() {
		jPanel1.setBackground(new java.awt.Color(0, 0, 51));
		jPanel1.setForeground(new java.awt.Color(204, 204, 204));
		jPanel1.setLayout(null);

		jTextField1.setToolTipText("text\tType your message here...");
		jTextField1.addActionListener((e)->sendText());
		jPanel1.add(jTextField1);
		jTextField1.setBounds(10, 370, 410, 40);
		//jTextField1.setEditable(false);

		jButton1.setBackground(new java.awt.Color(204, 204, 255));
		jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		jButton1.setText("Send");
		jButton1.addActionListener((e)->sendText());
		jPanel1.add(jButton1);
		jButton1.setBounds(420, 370, 80, 40);

		chatArea.setEditable(false);

		jScrollPane1.setViewportView(chatArea);

		jPanel1.add(jScrollPane1);
		jScrollPane1.setBounds(10, 80, 490, 280);

		jLabel2.setFont(new java.awt.Font("Myriad Pro", 1, 24)); // NOI18N
		jLabel2.setForeground(new java.awt.Color(255, 255, 255));
		jLabel2.setText(ime);
		jPanel1.add(jLabel2);
		jLabel2.setBounds(140, 20, 180, 40);


		jPanel1.add(jLabel1);
		jLabel1.setBounds(0, 0, 400, 400);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
				);

		setSize(new java.awt.Dimension(508, 441));
		setLocationRelativeTo(null);

		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				while(true) {
					try {
						setVisible(false);
						if(UID != null) {
							
							addMessageInSendQueue(
									()->new ByeMessage(numberSend, UID).sendMessage(address, port, socket), 
									()->System.out.println("Odjava uspješna"), 
									()->System.out.println("Odjava neuspješna"),
									false
									).get();
						}
					} catch (InterruptedException e1) {
						continue;
					} catch (ExecutionException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					pool.shutdown();
					System.exit(0);
				}
			}
		});
	}

	private void sendText() {
		if(jTextField1.getText() == "") return;
		String currentText = jTextField1.getText();
		jTextField1.setText("");

		long br = numberSend;
		addMessageInSendQueue(() ->new OutmsgMessage(br,UID, currentText).sendMessage(address, port, socket),()->{}, () ->{},true );




	}

	private static synchronized void addText(String text) {
		chatArea.setText(chatArea.getText() + text +"\n\n");
	}


	public static void main(String[] args) {


		if(args.length !=3) {
			System.err.println("Dragi korisniće, očekivao sam tri argumenta redom IP adresu, port i ime korisnika u navodnicima");
			return;
		}

		ime = args[2];
		try {
			address = InetAddress.getByName(args[0]);
			port = Integer.parseInt(args[1]);
			if(port < 1 || port > 65535) {
				System.out.println("P0rt mora biti izmedu 1 i 65535");
				return;
			}

		}catch (UnknownHostException e) {
			System.out.println("Ne može se razrješiti adresu: " + args[0]);
			return;
		}catch (NumberFormatException e) {
			System.out.println("Nije broj: "  + args[1]);
			return;
		}
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			System.out.println("Ne mogu otvoriti pristupnu točku");
			return;
		}

		SwingUtilities.invokeLater(()->{
			new Client().setVisible(true);
		});
	}

}
