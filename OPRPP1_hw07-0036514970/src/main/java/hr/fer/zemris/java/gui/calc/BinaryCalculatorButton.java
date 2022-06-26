package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

public class BinaryCalculatorButton extends ReverseCalculatorButton<DoubleBinaryOperator> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public BinaryCalculatorButton(CalcModel calcModel, String text, String reverseText, DoubleBinaryOperator func, DoubleBinaryOperator reverseFunc) {
		super(calcModel, text, reverseText,func, reverseFunc);
		this.func = func;
		this.addActionListener(e -> {
			this.calcModel.setPendingBinaryOperation(this.func);
		});
	}
}
