package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.JLabel;

public class LJLabel extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String key;
	ILocalizationProvider prov;
	
	public LJLabel(String key, ILocalizationProvider lp) {
		super();
		this.key = key;
		prov = lp;
		updateLabel();
		prov.addLocalizationListener(() -> updateLabel());
	}

	private void updateLabel() {
		setText(prov.getString(key));
	}
	
	

}
