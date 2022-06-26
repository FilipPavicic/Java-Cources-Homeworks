package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

public abstract class CalculatorButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String textButton;
	CalcModel calcModel;

	public CalculatorButton(String text, CalcModel calcModel) {
		super();
		this.textButton = text;
		this.calcModel = calcModel;
		this.setText(text);
		this.setBackground(Color.LIGHT_GRAY);
		this.setOpaque(true);
	
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setTextButton(String text) {
		this.textButton = text;
	}

	@Override
	public void addActionListener(ActionListener l) {
		super.addActionListener(new CalculatorActionListener() {
			
			@Override
			public void calculatorAction(ActionEvent e) {
				l.actionPerformed(e);
				
			}
		});
	}







}
