package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SplitingUtil {
	
	public static String[] spaceSpliting(String text) {
		if (text == null) return new String[] {}; 
		return text.split(" ");
	}
	/**
	 * Razdavaja text u polje tako da tekst u navodnicima očuva
	 * dok nad ostalim primjeni spaceSpliting
	 * 
	 * @param text tekst koji se razdvaja
	 * @return polje razdvojenih Stringova
	 * @throws IllegalArgumentException ako se iza navodnika nalazi bilo što
	 * osim razmaka
	 */
	public static String[] quotationSpliting(String text){
		if (text == null) return new String[] {};
		String[] parts = text.split("\"");
		List<String> partsByQuoSpace = new ArrayList<>();
		for(int i = 0; i<parts.length; i++) {
			if(i %2 != 0) partsByQuoSpace.add(parts[i]);
			else {
				if(i != 0 && !parts[i].startsWith(" ")) throw new IllegalArgumentException("Poslije navodnika se smije nalaziti samo praznina");
				parts[i] = parts[i].trim();
				if(parts[i].length() != 0) {
					String[] spaceSpliting = spaceSpliting(parts[i]);
					partsByQuoSpace.addAll(Arrays.asList(spaceSpliting));
				}	
			}
		}
		return partsByQuoSpace.toArray(new String[] {""});
	}
	
}
