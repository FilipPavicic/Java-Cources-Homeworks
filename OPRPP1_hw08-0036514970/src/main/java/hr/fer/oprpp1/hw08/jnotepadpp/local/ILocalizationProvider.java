package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Comparator;
import java.util.Locale;

public interface ILocalizationProvider {
	
	public void addLocalizationListener(ILocalizationListener lisener);
	public void removeLocalizationListener(ILocalizationListener lisener);
	public String getString(String item);
	public Locale getLocale(); 
	
}
