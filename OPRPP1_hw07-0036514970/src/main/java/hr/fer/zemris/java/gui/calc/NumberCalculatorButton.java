package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

public class NumberCalculatorButton  extends CalculatorButton{
	
	private static final long serialVersionUID = 1L;
	
	public NumberCalculatorButton(CalcModel calcModel, int number) {
		super(Integer.valueOf(number).toString(), calcModel);
		this.setFont(this.getFont().deriveFont(30f));
		this.addActionListener(e -> {
			calcModel.insertDigit(number);
			
		});
	}
	
	

	
	
	

}
