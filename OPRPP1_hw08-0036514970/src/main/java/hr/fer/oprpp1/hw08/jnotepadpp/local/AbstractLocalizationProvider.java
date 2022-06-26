package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLocalizationProvider implements ILocalizationProvider{
	List<ILocalizationListener> liseners = new ArrayList<>();
	
	@Override
	public void addLocalizationListener(ILocalizationListener lisener) {
		liseners.add(lisener);
	}
	
	@Override
	public void removeLocalizationListener(ILocalizationListener lisener) {
		liseners.remove(lisener);
	}
	
	public void fire() {
		for (ILocalizationListener iLocalizationListener : liseners) {
			iLocalizationListener.localizationChanged();
		}
	}
}
