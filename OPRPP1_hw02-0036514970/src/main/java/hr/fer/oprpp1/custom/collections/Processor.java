package hr.fer.oprpp1.custom.collections;


/**
 * Sučelje predstavlja model sposoban izvoditi operacije nad danim Objektom.
 * 
 * @author Filip Pavicic
 *
 */
public interface Processor {

	/**
	 * Izvodi određenu operaciju nad danim Objektom.
	 * @param value objekt nad kojim se izvodi operacija.
	 */
	void process(Object value);
}
