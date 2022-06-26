package hr.fer.oprpp1.hw08.jnotepadpp;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public abstract class DocumentAdapter implements DocumentListener {

	@Override
	public void insertUpdate(DocumentEvent e) {
		textChanged();

	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		textChanged();

	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		textChanged();

	}

	protected abstract void textChanged();
	
	
	
	

}
