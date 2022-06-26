package hr.fer.oprpp1.hw04.db;

public interface IFieldValueGetter {
	/**
	 * Dohvaća dani podatak za zapis
	 * @param record zapis
	 * @return dani podatak
	 */
	public String get(StudentRecord record);
	
}
