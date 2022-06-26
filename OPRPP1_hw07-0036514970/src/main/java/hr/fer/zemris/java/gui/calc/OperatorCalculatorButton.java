package hr.fer.zemris.java.gui.calc;

import java.util.function.BiFunction;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

public class OperatorCalculatorButton extends CalculatorButton {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	DoubleBinaryOperator func;

	public OperatorCalculatorButton(CalcModel calcModel, String text, DoubleBinaryOperator func) {
		super(text, calcModel);
		this.func = func;
		this.addActionListener(e -> {
			this.calcModel.setPendingBinaryOperation(this.func);
		});
	}
}
