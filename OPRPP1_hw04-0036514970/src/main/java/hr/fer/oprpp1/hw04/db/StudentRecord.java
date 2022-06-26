package hr.fer.oprpp1.hw04.db;

import java.util.Objects;

/**
 * Razred predstavlja implementaciju zapisa o studentu koji
 * sadrži ime, prezime ,JMBAG, i završnu ocjenu.
 * 
 * @author Filip Pavicic
 *
 */
public class StudentRecord {
	private String jmbag, lastName, firstName;
	private int finalGrade;
	
	/**
	 * Konstrukor prima vrijednosti za parametre i provjerava da li je jmbag null 
	 * i dali je ocjena u rasponu od 1 do 5
	 * 
	 * @param jmbag JMBAG
	 * @param lastName Prezime studenta
	 * @param firstName Ime studenta
	 * @param finalGrade završna ocjena
	 * 
	 * @throws IllegalArgumentException ako ocjena nije korektna.
	 */
	public StudentRecord(String jmbag, String lastName, String firstName,
			int finalGrade) {
		super();
		this.jmbag = Objects.requireNonNull(jmbag);
		this.lastName = lastName;
		this.firstName = firstName;
		if(finalGrade < 1 && finalGrade >5) throw new IllegalArgumentException("Ocjena mora biti između 1 i 5");
		this.finalGrade = finalGrade;
	}
	
	public String getJmbag() {
		return jmbag;
	}
	public String getLastName() {
		return lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public int getFinalGrade() {
		return finalGrade;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}
	
	/**
	 * Uspoređuje dva zapisa o studentu na temelju JMBAG-a
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof StudentRecord))
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StudentRecord [jmbag=" + jmbag + ", lastName=" + lastName
				+ ", firstName=" + firstName + ", finalGrade=" + finalGrade
				+ "]";
	}
	
	
	
	
	
}
