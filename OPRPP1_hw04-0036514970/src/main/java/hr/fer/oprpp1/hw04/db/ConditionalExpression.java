package hr.fer.oprpp1.hw04.db;

/**
 * Razred koji predstavlja implemetaciju izraza usporedbe koji se korisiti u bazi podataka.
 * 
 * @author Filip Pavicic
 *
 */
public class ConditionalExpression {
	IFieldValueGetter fieldGetter;
	String stringLiteral;
	IComparisonOperator comparisonOperator;
	
	/**
	 * Konstrukor prima ime stupca, opertor i Litteral i to pohranjuje kao jedan upitni izraz
	 * 
	 * @param fieldGetter ime stupca
	 * @param stringLiteral literal
	 * @param comparisonOperator operator
 	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter,
			String stringLiteral, IComparisonOperator comparisonOperator) {
		super();
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}
	
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}
	
	public String getStringLiteral() {
		return stringLiteral;
	}
	
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

	/**
	 * Usporedba na temelju toga dali comparisonOperator
	 * i fieldGetter pripadu istim klasama i dali su literali jednaki
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ConditionalExpression))
			return false;
		ConditionalExpression other = (ConditionalExpression) obj;
		if (comparisonOperator == null) {
			if (other.comparisonOperator != null)
				return false;
		} else if (!comparisonOperator.getClass().equals(other.comparisonOperator.getClass()))
			return false;
		if (fieldGetter == null) {
			if (other.fieldGetter != null)
				return false;
		} else if (!fieldGetter.getClass().equals(other.fieldGetter.getClass()))
			return false;
		if (stringLiteral == null) {
			if (other.stringLiteral != null)
				return false;
		} else if (!stringLiteral.equals(other.stringLiteral))
			return false;
		return true;
	}
	
	/**
	 * to String isključivo za testne primjere
	 * 
	 */
	@Override
	public String toString() {
		return "ConditionalExpression [fieldGetter=" + fieldGetter
				+ ", stringLiteral=" + stringLiteral + ", comparisonOperator="
				+ comparisonOperator+ "]";
	}
	
	
	

	
	
	
	
	
}
