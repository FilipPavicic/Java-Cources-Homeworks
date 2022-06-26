package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

public class LocalizableAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String key;
	ILocalizationProvider prov;
	
	
	public LocalizableAction(String key, ILocalizationProvider lp) {
		super();
		prov = lp;
		this.key = key;
		putValue(Action.NAME, prov.getString(key));
		prov.addLocalizationListener(() -> putValue(Action.NAME, prov.getString(key)));
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}
