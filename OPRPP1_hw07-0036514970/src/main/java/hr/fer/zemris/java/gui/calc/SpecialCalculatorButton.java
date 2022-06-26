package hr.fer.zemris.java.gui.calc;

import java.awt.event.ActionListener;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

public class SpecialCalculatorButton extends CalculatorButton {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SpecialCalculatorButton(String text, CalcModel calcModel, ActionListener al) {
		super(text, calcModel);
		this.addActionListener(al);
	}

}
