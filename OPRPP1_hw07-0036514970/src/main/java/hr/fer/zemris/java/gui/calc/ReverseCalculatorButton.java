package hr.fer.zemris.java.gui.calc;

import java.awt.Dimension;
import java.util.function.Consumer;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

public class ReverseCalculatorButton<T> extends CalculatorButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String reverseText;
	T func;
	T reverseFunc;

	public ReverseCalculatorButton(CalcModel calcModel, String text, String reverseText, T func, T reverseFunc) {
		super(text, calcModel);
		this.reverseText = reverseText;
		this.func = func;
		this.reverseFunc = reverseFunc;
		int textButtonSize = (int) (new JButton(text)).getPreferredSize().getWidth();
		int reverseRextButtonSize = (int) (new JButton(reverseText)).getPreferredSize().getWidth();
		this.setPreferredSize(new Dimension(Math.max(textButtonSize, reverseRextButtonSize),(int) this.getPreferredSize().getHeight()));
		revalidate();
		// TODO Auto-generated constructor stub
		
	}
	
	public void setReverseText(String reverseText) {
		this.reverseText = reverseText;
	}

	public void setFunc(T func) {
		this.func = func;
	}

	public void setReverseFunc(T reverseFunc) {
		this.reverseFunc = reverseFunc;
	}

	public void reverse() {
		reverseOne(func, reverseFunc, this::setFunc, this::setReverseFunc);
		reverseOne(textButton, reverseText, this::setTextButton, this::setReverseText);
		this.setText(textButton);
	}

	private <E> void  reverseOne(E a, E b, Consumer<E> conA, Consumer<E> conB) {
		var t = a;
		conA.accept(b);
		conB.accept(t);
	}
	
}
