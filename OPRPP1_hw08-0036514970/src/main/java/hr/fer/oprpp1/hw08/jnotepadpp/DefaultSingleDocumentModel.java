package hr.fer.oprpp1.hw08.jnotepadpp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;




public class DefaultSingleDocumentModel implements SingleDocumentModel{

	Path path;
	JTextArea textArea;
	boolean modified = false;
	String originalText;
	List<SingleDocumentListener> listeners;

	public DefaultSingleDocumentModel(Path path, JTextArea textArea) {
		listeners = new ArrayList<>();
		this.path = path;
		this.textArea = textArea;

		if(path == null) {
			originalText = "";
		}else {
			if(!Files.isReadable(path)) throw new IllegalArgumentException("Datoteka: " + path.toAbsolutePath() + "ne postoji!");

			try {
				byte[] bytes = Files.readAllBytes(path);
				originalText = new String(bytes, StandardCharsets.UTF_8);
				this.textArea.setText(originalText);
			} catch (IOException e) {
				throw new IllegalArgumentException("Pogreška pri učitavanju datoteke: " + path.toAbsolutePath());
			}
		}
		this.textArea.getDocument().addDocumentListener(new DocumentAdapter() {

			@Override
			protected void textChanged() {
				String text = textArea.getText();

				//radi bržeg zaključivanja
				if(text.length() != originalText.length()) {
					modificateTrue();
					return;
				}

				if(!text.equals(originalText)) {
					modificateTrue();
					return;
				}
				
				if(modified == true) {
					modified = false;
					modificationNotify();
				}
			}

			private void modificateTrue() {
				
				if(modified == false ) {
					modified = true;
					modificationNotify();
				}
				
				
			}
		});

	}

	@Override
	public JTextArea getTextComponent() {
		return textArea;
	}

	@Override
	public Path getFilePath() {
		return path;
	}

	@Override
	public void setFilePath(Path path) {
		if(path == null) path = this.path;
		byte[] podatci = textArea.getText().getBytes(StandardCharsets.UTF_8);
		
		if(!path.getFileName().toString().contains(".")) path = Paths.get(path.toString() + ".txt");
		
		try {
			Files.write(path, podatci);
		} catch (IOException e1) {
			throw new IllegalArgumentException("Nije moguće spremiti datoteku na putanju: " + path);
		}
		this.path = path;
		originalText = textArea.getText();
		setModified(false);
		pathNotify();

	}



	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		modificationNotify();

	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);

	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);

	}

	protected void modificationNotify() {
		for (SingleDocumentListener singleDocumentListener : listeners) {
			singleDocumentListener.documentModifyStatusUpdated(this);
		}

	}

	private void pathNotify() {
		for (SingleDocumentListener singleDocumentListener : listeners) {
			singleDocumentListener.documentFilePathUpdated(this);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof DefaultSingleDocumentModel))
			return false;
		DefaultSingleDocumentModel other = (DefaultSingleDocumentModel) obj;
		if (path == null || other.path == null) {
			return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}
	
	@Override
	public String statisicInfo() {
		String text = textArea.getText();
		int characters = text.length();
		int lines = text.split("\r\n|\r|\n").length;
		int nonBlankCharacters = characters - text.split("\r\n|\r|\n|\t| ").length + 1;
		return "Your document has "+characters+
				" characters, "+nonBlankCharacters+
				" non-blank characters and "+lines+
				" lines.";
	}


}
