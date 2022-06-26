package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Razred koji predstavlja imentaciju Elementa koji je po tipu operator.
 * 
 * @author Filip Pavicic
 *
 */
public class ElementOperator extends Element {
	
	private String symbol;
	
	public ElementOperator(String symbol) {
		super();
		this.symbol = symbol;
	}

	public String getName() {
		return symbol;
	}
	
	@Override
	public String toString() {
		return symbol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ElementOperator))
			return false;
		ElementOperator other = (ElementOperator) obj;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}
}
