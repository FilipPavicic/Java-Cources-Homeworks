
 package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Comparator;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider{
	String language ;
	ResourceBundle bundle;
	Locale locale;
	
	private static final LocalizationProvider instance = new LocalizationProvider();
	
	
	private LocalizationProvider() {
		setLanguage("en");
	}
	
	public static LocalizationProvider getInstance() {
		return instance;
	}
	
	public void setLanguage(String language) {
		this.language = language;
		bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi",  Locale.forLanguageTag(this.language));
		locale = new Locale(language);
		fire();
	}
	
	@Override
	public String getString(String item) {
		return bundle.getString(item);
	}
	
	@Override
	public Locale getLocale() {
		return locale;
	}
	
}
