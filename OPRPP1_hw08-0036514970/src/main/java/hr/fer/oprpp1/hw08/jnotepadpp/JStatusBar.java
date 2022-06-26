package hr.fer.oprpp1.hw08.jnotepadpp;



import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class JStatusBar extends JPanel {

	JFrame parent;
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	JLabel dateLabel;
	JLabel statusLabel;
	JLabel lengthLabel;
	Thread thread;

	public JStatusBar(JFrame parent) {
		super();
		this.parent = parent;
		this.setPreferredSize(new Dimension(parent.getWidth(), 16));
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		setOpaque(true);
		setBackground(Color.lightGray);
		initGUI();
	}

	private void initGUI() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		add(Box.createHorizontalGlue());

		lengthLabel = new JLabel();
		lengthLabel.setBorder(BorderFactory.createMatteBorder(
				0, 1, 0, 0, Color.gray));
		lengthLabel.setVisible(false);
		add(lengthLabel);

		add(Box.createHorizontalGlue());

		//add(new JSeparator(SwingConstants.VERTICAL));add(Box.createHorizontalGlue());
		statusLabel = new JLabel();
		statusLabel.setBorder(BorderFactory.createMatteBorder(
				0, 1, 0, 0, Color.gray));
		statusLabel.setVisible(false);
		add(statusLabel);

		add(Box.createHorizontalGlue());

		dateLabel = new JLabel();
		setTime();
		dateLabel.setBorder(BorderFactory.createMatteBorder(
				0, 1, 0, 0, Color.gray));
		add(dateLabel);

		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while(true) {
					setTime();

					while(true) {
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							continue;
						}
						break;
					}
				}
			}
		});
		thread.setDaemon(true);
		thread.start();


	}

	private void setTime() {
		dateLabel.setText(dtf.format(LocalDateTime.now()));  

	}
	public void setVisible(boolean visible) {
		lengthLabel.setVisible(visible);
		statusLabel.setVisible(visible);
	}
	
	public void setLength(int len ) {
		lengthLabel.setText(" length : "+ len);
	}
	
	public void setStatus(int ln, int col, int sel ) {
		statusLabel.setText(" Ln : "+ln+" Col : "+col+" Sel : "+sel);
	}


}
