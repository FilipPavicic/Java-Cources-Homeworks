package hr.fer.oprpp1.hw04.db;

/**
 * Sučelje koje implementira svaki Operator u bazi podataka
 * 
 * @author Filip Pavicic
 *
 */
@FunctionalInterface
public interface IComparisonOperator {
	/**
	 * uspoređuje prvu i drugu vrijednost i ispituje da
	 * li zadovoljavaju dani uvijet,
	 * 
	 * @param value1 prva vrijednost
	 * @param value2 druga vrijednost
	 * @return true ako zadovoljavaju, inače false
	 */
	public boolean satisfied(String value1, String value2);

}
