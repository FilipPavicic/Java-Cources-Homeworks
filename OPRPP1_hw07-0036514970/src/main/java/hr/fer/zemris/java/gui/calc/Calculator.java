package hr.fer.zemris.java.gui.calc;

import java.awt.Checkbox;
import java.awt.Component;
import java.awt.Container;
import java.awt.Label;
import java.util.Stack;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;



public class Calculator extends JFrame {
	private static final long serialVersionUID = 1L;

	public Calculator() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Java Calculator v1.0");
		setLocation(20, 20);
		initGUI();
		setSize(600, 300);
		pack();
	}
	
	private void initGUI() {
		Stack<Double> stack = new Stack<>();
		CalcModel calcModel = new CalcModelImpl();
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		JLabel jLabel = new JLabel();
		
		cp.add(new CalculatorLabel(calcModel),new RCPosition(1, 1));
		cp.add(new OperatorCalculatorButton(calcModel, "=", null), new RCPosition(1,6));
		cp.add(new SpecialCalculatorButton("clr", calcModel, e -> calcModel.clear()),new RCPosition(1, 7));
		
		
		cp.add(new UnaryCalculatorButton(calcModel, "1/x", e -> 1.0  / e),new RCPosition(2, 1));
		cp.add(new UnaryCalculatorButton(calcModel, "sin", "arcsin", e -> Math.sin(e), e -> Math.asin(e)),new RCPosition(2, 2));
		cp.add(new NumberCalculatorButton(calcModel, 7), new RCPosition(2, 3));
		cp.add(new NumberCalculatorButton(calcModel, 8), new RCPosition(2, 4));
		cp.add(new NumberCalculatorButton(calcModel, 9), new RCPosition(2, 5));
		cp.add(new OperatorCalculatorButton(calcModel, "/", (a,b) -> a / b),new RCPosition(2, 6));
		cp.add(new SpecialCalculatorButton("reset", calcModel, e -> calcModel.clearAll()), new RCPosition(2, 7));
		
		
		cp.add(new UnaryCalculatorButton(calcModel, "log", "10^x", e -> Math.log10(e), e -> Math.pow(10, e)),new RCPosition(3, 1));
		cp.add(new UnaryCalculatorButton(calcModel, "cin", "arccos", e -> Math.cos(e), e -> Math.acos(e)),new RCPosition(3, 2));
		cp.add(new NumberCalculatorButton(calcModel, 4), new RCPosition(3, 3));
		cp.add(new NumberCalculatorButton(calcModel, 5), new RCPosition(3, 4));
		cp.add(new NumberCalculatorButton(calcModel, 6), new RCPosition(3, 5));
		cp.add(new OperatorCalculatorButton(calcModel, "*", (a,b) -> a * b),new RCPosition(3, 6));
		cp.add(new SpecialCalculatorButton("push", calcModel, e ->{
			stack.push(calcModel.getValue());
			calcModel.clear();
		}), new RCPosition(3, 7));
		
		
		cp.add(new UnaryCalculatorButton(calcModel, "ln", "e^x", e -> Math.log(e), e -> Math.pow(Math.E, e)),new RCPosition(4, 1));
		cp.add(new UnaryCalculatorButton(calcModel, "tan", "arctan", e -> Math.tan(e), e -> Math.atan(e)),new RCPosition(4, 2));
		cp.add(new NumberCalculatorButton(calcModel, 1), new RCPosition(4, 3));
		cp.add(new NumberCalculatorButton(calcModel, 2), new RCPosition(4, 4));
		cp.add(new NumberCalculatorButton(calcModel, 3), new RCPosition(4, 5));
		cp.add(new OperatorCalculatorButton(calcModel, "-", (a,b) -> a - b),new RCPosition(4, 6));
		cp.add(new SpecialCalculatorButton("pop", calcModel, e ->{
			calcModel.setValue(stack.pop());
		}), new RCPosition(4, 7));
		
		cp.add(new BinaryCalculatorButton(calcModel, "x^n", "x^(1/n)", (a,b) -> Math.pow(a,b), (a, b) -> Math.pow(a, 1 / b)),new RCPosition(5, 1));
		cp.add(new UnaryCalculatorButton(calcModel, "ctg", "arcctg", e -> 1 / Math.tan(e), e -> 1 / Math.atan(e)),new RCPosition(5, 2));
		cp.add(new NumberCalculatorButton(calcModel, 0), new RCPosition(5, 3));
		cp.add(new  SpecialCalculatorButton("+/-", calcModel, e -> calcModel.swapSign()),new RCPosition(5, 4));
		cp.add(new  SpecialCalculatorButton(".", calcModel, e -> calcModel.insertDecimalPoint()),new RCPosition(5, 5));
		cp.add(new OperatorCalculatorButton(calcModel, "+", (a,b) -> a + b),new RCPosition(5, 6));
		JCheckBox checkbox = new JCheckBox("Inv");
		cp.add(checkbox, new RCPosition(5, 7));
		checkbox.addActionListener(l -> {
			
			for (Component comp: cp.getComponents()) {
				if(comp instanceof ReverseCalculatorButton) {
					((ReverseCalculatorButton<?>)  comp).reverse();
					
				}
			}
		});
		
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new Calculator().setVisible(true);
		});
	}
}
