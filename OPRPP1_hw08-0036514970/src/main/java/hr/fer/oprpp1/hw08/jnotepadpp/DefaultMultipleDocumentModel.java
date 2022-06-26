package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private static final long serialVersionUID = 1L;

	List<SingleDocumentModel> documents;
	List<MultipleDocumentListener> listeners;
	SingleDocumentModel currentDocumet;
	SingleDocumentModel previousDocumet;

	public DefaultMultipleDocumentModel() {
		documents = new ArrayList<>();
		listeners = new ArrayList<>();
		listeners.add(new MultipleDocumentListener() {

			@Override
			public void documentRemoved(SingleDocumentModel model) {
				DefaultMultipleDocumentModel.this.remove(
						DefaultMultipleDocumentModel.this.indexOfComponent(model.getTextComponent()) 
						);

			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
				DefaultMultipleDocumentModel.this.addTab(
						model.getFilePath() == null ? "(undefined)" : model.getFilePath().getFileName().toString(),
								model.getTextComponent());
				DefaultMultipleDocumentModel.this.setToolTipTextAt(
						DefaultMultipleDocumentModel.this.getTabCount() - 1,
						model.getFilePath() == null ? "(undefined)" : model.getFilePath().toAbsolutePath().toString()
						);
				DefaultMultipleDocumentModel.this.setIconAt(
						DefaultMultipleDocumentModel.this.getTabCount() - 1,
						new Icon().getGreenIcon()
						);
				model.addSingleDocumentListener(new SingleDocumentListener() {

					@Override
					public void documentModifyStatusUpdated(SingleDocumentModel model) {
						ImageIcon icon = model.isModified() ? new Icon().getRedIcon() : new Icon().getGreenIcon();
						DefaultMultipleDocumentModel.this.setIconAt(
								DefaultMultipleDocumentModel.this.indexOfComponent(model.getTextComponent()),
								icon
								);

					}

					@Override
					public void documentFilePathUpdated(SingleDocumentModel model) {
						DefaultMultipleDocumentModel.this.setTitleAt(
								DefaultMultipleDocumentModel.this.getSelectedIndex(),
								model.getFilePath().getFileName().toString());

					}
				});

			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel,
					SingleDocumentModel currentModel) {
				if(currentModel != null ) DefaultMultipleDocumentModel.this.setSelectedComponent(currentModel.getTextComponent());

			}
		});
		this.addChangeListener((t) -> {
			previousDocumet = currentDocumet;
			currentDocumet = this.getSelectedIndex() == -1 ? null : documents.get(this.getSelectedIndex());
			changeDocumentNotify();
		});
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return new ArrayList<SingleDocumentModel>(documents).iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		return document(null);
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocumet;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		return document(path);
	}



	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		for (SingleDocumentModel singleDocumentModel : documents) {
			if(newPath == null) break;

			if(singleDocumentModel.getFilePath() == null) continue;

			if(singleDocumentModel.getFilePath().equals(newPath)) 
				throw new IllegalStateException();
		}
		model.setFilePath(newPath);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		previousDocumet = currentDocumet;
		int indexOfCurrent = documents.indexOf(currentDocumet);
		documents.remove(indexOfCurrent);
		removeDocumentNotify();
	}



	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}

	private SingleDocumentModel document(Path path) {
		previousDocumet = currentDocumet;
		SingleDocumentModel doc  = new DefaultSingleDocumentModel(path, new JTextArea());
		if(documents.contains(doc)) {
			currentDocumet = documents.get(documents.indexOf(doc));
		} else {
			currentDocumet = doc;
			documents.add(currentDocumet);
			addedDocumentNotify();
		}
		DefaultMultipleDocumentModel.this.setSelectedComponent(currentDocumet.getTextComponent());
		return currentDocumet;
	}

	private void addedDocumentNotify() {
		for (MultipleDocumentListener multipleDocumentListener : listeners) {
			multipleDocumentListener.documentAdded(currentDocumet);
		}
	}

	private void changeDocumentNotify() {
		for (MultipleDocumentListener multipleDocumentListener : listeners) {
			multipleDocumentListener.currentDocumentChanged(previousDocumet, currentDocumet);
		}

	}
	private void removeDocumentNotify() {
		for (MultipleDocumentListener multipleDocumentListener : listeners) {
			multipleDocumentListener.documentRemoved(previousDocumet);
		}

	}

}
