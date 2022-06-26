package hr.fer.oprpp1.hw04.db.demo;


import hr.fer.oprpp1.hw04.db.FieldValueGetters;
import hr.fer.oprpp1.hw04.db.StudentRecord;

public class DemoFieldValueGetter {
	public static void main(String[] args) {
		StudentRecord record = new StudentRecord("0012345678", "Pavičić", "Filip", 5);
		System.out.println("First name: " + FieldValueGetters.FIRST_NAME.get(record));
		System.out.println("Last name: " + FieldValueGetters.LAST_NAME.get(record));
		System.out.println("JMBAG: " + FieldValueGetters.JMBAG.get(record));
	}
}
