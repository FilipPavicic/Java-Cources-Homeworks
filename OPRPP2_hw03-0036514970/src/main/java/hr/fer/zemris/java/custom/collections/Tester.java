package hr.fer.zemris.java.custom.collections;

/**
 * Sučelje namjenjeno za testiranje vrijednosti.
 * 
 * @author Filip Pavicic
 *
 */
public interface Tester {
	
	/**
	 * provjera dali je test istinit, ili lažan
	 * 
	 * @param obj objekt nad kojim se provodi test.
	 * @return <code>true</code> ako je test isitini, inače <code>false</code>
	 */
	boolean test(Object obj);
}
