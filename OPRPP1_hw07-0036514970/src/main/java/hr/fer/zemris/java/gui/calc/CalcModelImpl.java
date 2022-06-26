package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import javax.swing.event.ListDataListener;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

public class CalcModelImpl implements CalcModel {
	private List<CalcValueListener> promatraci = new ArrayList<>();
	boolean editable = true;
	boolean positive = true;
	String digits = "";
	Double value = Double.valueOf(0);
	String freez;
	DoubleBinaryOperator pendingOperation;
	Double activeOperand;
	

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		promatraci.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		promatraci.remove(l);

	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public void setValue(double value) {
		freez = null;
		this.value = value;
		positive = value > 0 ? true : false;
		digits = Double.valueOf(Math.abs(this.value)).toString();
		editable = false;
		notifier();
	}
	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		digits = "";
		positive = true;
		value = Double.valueOf(0);
		editable = true;
		freez = null;
		notifier();

	}

	@Override
	public void clearAll() {
		clear();
		activeOperand = null;
		pendingOperation = null;

	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!editable) throw new CalculatorInputException("Nije moguće promjeniti predznak ako model nije editablilan");
		
		freez = null;
		positive = !positive;
		value = value * -1;
		notifier();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!editable) throw new CalculatorInputException("Nije moguće unjeti tocku ako model nije editablilan");
		if(digits == "") throw new CalculatorInputException("Nije moguće promjeniti predznak ako nije upisana niti jedna znamenka");
		freez = null;
		if(digits.contains(".")) throw new CalculatorInputException("Točka je već unesena");
		
		digits += ".";
		notifier();

	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!editable) throw new CalculatorInputException("Nije moguće unjeti znamenku ako model nije editablilan");

		freez = null;
		try {
			digits += digit;
			value = Double.parseDouble(digits);
			if(value == Double.POSITIVE_INFINITY) throw new CalculatorInputException();
			if(!positive) value = value * -1;
		}catch (NumberFormatException e) {
			throw new CalculatorInputException(e.getMessage());
		}
		notifier();
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperand != null;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(activeOperand == null) throw new IllegalStateException("Operand nije postavljen");
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = null;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		if(pendingOperation != null  && activeOperand != null) setValue(pendingOperation.applyAsDouble(activeOperand, value));
		freez = digits;
		activeOperand = value;
		pendingOperation = op;
		if(op != null) {
			digits = "";
			positive = true;
			editable = true;
		}
		
	}
	

	@Override
	public String toString() {
		if(freez != null) return freez;
		
		String sign = positive  ? "" : "-";
		if(digits == "") return sign +"0";
		return sign + digits.replaceFirst("^0+(?=\\d)", "");
	}
	
	private void notifier() {
		for (CalcValueListener promatrac : promatraci) {
			promatrac.valueChanged(this);
		}
	}



}
