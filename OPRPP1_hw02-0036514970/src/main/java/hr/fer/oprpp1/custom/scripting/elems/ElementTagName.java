package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Razred koji predstavlja imentaciju Elementa koji je po tipu Varijabla.
 * 
 * @author Filip Pavicic
 *
 */
public class ElementTagName extends Element {
	
	private String name;
	
	public ElementTagName(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ElementTagName))
			return false;
		ElementTagName other = (ElementTagName) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
