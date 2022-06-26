package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Razred koji predstavlja imentaciju Elementa koji je po tipu String.
 * 
 * @author Filip Pavicic
 *
 */
public class ElementString extends Element {
	
	private String value;
	
	public ElementString(String value) {
		super();
		this.value = value;
	}

	public String getvalue() {
		return value;
	}
	
	@Override
	public String toString() {
		return "\""+value+"\"";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ElementString))
			return false;
		ElementString other = (ElementString) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
