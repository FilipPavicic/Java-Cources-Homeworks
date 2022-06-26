package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BiFunction;

public class ValueWrapper {
	Object value;

	public ValueWrapper(Object value) {
		super();
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	


	private static void checkArgument(Object value) {
		if(value instanceof String || value instanceof Integer || value instanceof Double) return;
		throw new RuntimeException(value +" nie jedan od dopuštenih tipova(String, Integer,Double)");
		
	}
	
	

	public void add(Object incValue) {
		numericOperation(incValue, (a,b) -> a + b);
	}
	public void subtract(Object decValue) {
		numericOperation(decValue, (a,b) -> a - b);
	}
	
	public void multiply(Object mulValue) {
		numericOperation(mulValue, (a,b) -> a * b);
	}
	public void divide(Object divValue) {
		numericOperation(divValue, (a,b) -> a / b);
	}
	
	public int numCompare(Object value) {
		Double arg1 = getValue(this.value);
		Double arg2 = getValue(value) ;
		return arg1.compareTo(arg2);
		
	}
	
	private void numericOperation(Object value,BiFunction<Double, Double, Double> func) {
		Double arg1 = getValue(this.value);
		Double arg2 = getValue(value);
		Double rez = func.apply(arg1, arg2);
		if(value instanceof String ) {
			Class<?> Type = getClassType((String)value);
			if(Type.getName().equals("java.lang.Integer")) {
				this.value = (Integer) Integer.valueOf(rez.intValue());
				return;
			}
		}
		if(value == null || this.value == null) {
			this.value = (Integer) Integer.valueOf(rez.intValue());
			return;
		}
		if(value instanceof Integer && this.value instanceof Integer) {
			this.value = (Integer) Integer.valueOf(rez.intValue());
			return; 
		}
		
		this.value = (Double)rez;
	}

	private static Double getValue(Object value) {
		if(value == null) return 0.0;
		checkArgument(value);
		if(value instanceof String) {
			Double rez = null;
			try {
				String strValue = (String) value;
				rez = Double.parseDouble(strValue);
				
			}catch (NumberFormatException e) {
				throw new RuntimeException(value + " nije moguće pretvorit u broj");
			}
			return rez;
		}
		if(value instanceof Integer) return Double.valueOf((Integer)value * 1.0);
		return (Double) value;
	}
	private Class<?> getClassType(String text) {
		try {
			String strValue = (String) text;
			if(strValue.contains(".") || strValue.contains("E")) {
				Double rez = Double.parseDouble(strValue);
				return Double.class;
			}else {
				Integer rez = Integer.parseInt(strValue);
				return Integer.class;
			}
			
		}catch (NumberFormatException e) {
			throw new RuntimeException(value + " nije moguće pretvorit u broj");
		}
	}
}
