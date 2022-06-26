package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Razred koji predstavlja imentaciju Elementa koji je po tipu Double konstanta.
 * 
 * @author Filip Pavicic
 *
 */
public class ElementConstantDouble extends Element {
	
	private double value;
	
	public ElementConstantDouble(double value) {
		super();
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ElementConstantDouble))
			return false;
		ElementConstantDouble other = (ElementConstantDouble) obj;
		if (Double.doubleToLongBits(value) != Double
				.doubleToLongBits(other.value))
			return false;
		return true;
	}
	@Override
	public String getName() {
		return "" + value;
	}
	
	
}
