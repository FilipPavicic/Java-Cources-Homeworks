package hr.fer.oprpp1.hw04.db;

/**
 * Razred predstavlja implementaciju komaratora za bazu podataka
 * 
 * @author Filip Pavicic
 *
 */
public class ComparisonOperators {
	public static final IComparisonOperator LESS = (v1, v2) -> v1.compareTo(v2) < 0;
	public static final IComparisonOperator LESS_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) <= 0;
	public static final IComparisonOperator GREATER = (v1, v2) -> v1.compareTo(v2) > 0;
	public static final IComparisonOperator GREATER_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) >= 0;
	public static final IComparisonOperator EQUALS = (v1, v2) -> v1.compareTo(v2) == 0;
	public static final IComparisonOperator NOT_EQUALS = (v1, v2) -> v1.compareTo(v2) != 0;
	public static final IComparisonOperator LIKE = (v1, v2) -> {
		if( v2.contains("**")) throw new IllegalArgumentException("Nesmije se pojaviti dva znaka * zaredom");
		return v1.matches(v2.replace("*", ".*"));
	};
}
