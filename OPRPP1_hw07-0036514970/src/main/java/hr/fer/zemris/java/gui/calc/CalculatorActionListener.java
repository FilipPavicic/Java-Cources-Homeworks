package hr.fer.zemris.java.gui.calc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

public interface CalculatorActionListener extends ActionListener{

	public void calculatorAction(ActionEvent e);
	
	@Override
	public default void actionPerformed(ActionEvent e) {
		try {
			calculatorAction(e);
		}catch (CalculatorInputException e1) {
			return;
		}
		
	}
	

}
