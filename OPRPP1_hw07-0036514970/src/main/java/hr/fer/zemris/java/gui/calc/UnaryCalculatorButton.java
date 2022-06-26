package hr.fer.zemris.java.gui.calc;

import java.util.function.Function;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

public class UnaryCalculatorButton extends ReverseCalculatorButton<Function<Double, Double>> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UnaryCalculatorButton(CalcModel calcModel, String text, String reverseText, 
			Function<Double, Double> func, Function<Double, Double> reverseFunc) {
		super(calcModel, text, reverseText, func, reverseFunc);
		this.addActionListener(e -> {
			double rez = this.func.apply(calcModel.getValue());
			calcModel.setValue(rez);
		});
		
		// TODO Auto-generated constructor stub
	}
	

	public UnaryCalculatorButton(CalcModel calcModel, String text,
			Function<Double, Double> func) {
		this(calcModel, text, text,func, func);
	}
	


}
