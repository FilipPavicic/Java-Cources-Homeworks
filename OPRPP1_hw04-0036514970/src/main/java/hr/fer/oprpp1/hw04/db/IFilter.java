package hr.fer.oprpp1.hw04.db;

public interface IFilter {
	/**
	 * Provjerava da li zapis zadovoljava dani uvijet
	 * @param record
	 * @return 
	 */
	 public boolean accepts(StudentRecord record);
	}
