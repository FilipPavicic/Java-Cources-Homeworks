package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Locale;

public class LocalizationProviderBridge extends AbstractLocalizationProvider{
	boolean connected;
	ILocalizationProvider parent;
	ILocalizationListener lisener;
	
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = parent;
		this.lisener = () -> {
			for (ILocalizationListener iLocalizationListener : liseners) {
				iLocalizationListener.localizationChanged();
			}
		};
	}
	
	public void connect() {
		parent.addLocalizationListener(lisener);
	}
	
	public void disconnect() {
		parent.removeLocalizationListener(lisener);	
	}

	@Override
	public String getString(String item) {
		return parent.getString(item);
		
	}
	
	@Override
	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
