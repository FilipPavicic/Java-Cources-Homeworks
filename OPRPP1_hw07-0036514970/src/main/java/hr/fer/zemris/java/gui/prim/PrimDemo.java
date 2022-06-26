package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


public class PrimDemo extends JFrame{

	private static final long serialVersionUID = 1L;

	public PrimDemo() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Prozor1");
		setLocation(20, 20);
		setSize(500, 200);
		initGUI();
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		PrimListModel model = new PrimListModel();
		
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);
		
		JPanel central = new JPanel(new GridLayout(1, 0));
		central.add(new JScrollPane(list1));
		central.add(new JScrollPane(list2));
		
		cp.add(central, BorderLayout.CENTER);
		
		JButton sljedeci = new JButton("Sljedeci");
		
		sljedeci.addActionListener(e -> {
			model.next();
		});
		
		cp.add(sljedeci, BorderLayout.PAGE_END);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new PrimDemo().setVisible(true);
		});
	}
}
