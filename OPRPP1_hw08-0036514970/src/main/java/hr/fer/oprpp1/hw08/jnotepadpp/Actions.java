package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Path;
import java.util.function.Function;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;

import hr.fer.oprpp1.hw08.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;
import hr.fer.oprpp1.hw08.jnotepadpp.local.LocalizationProvider;




public class Actions {

	private static ILocalizationProvider lp = LocalizationProvider.getInstance();


	public static Action newDocumentAction(MultipleDocumentModel model) {
		Action action =  new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				model.createNewDocument();
			}		
		};
		lp.addLocalizationListener(() -> action.putValue(Action.NAME, lp.getString("new")));
		action.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control N")); 
		return action;
	}

	public static Action openDocumentAction(MultipleDocumentModel model, Component parent) {
		Action action = new AbstractAction() {

			private static final long serialVersionUID = 1L;



			@Override
			public void actionPerformed(ActionEvent e) {
				Path path = fileChoice(parent);
				if(path == null) return;
				model.loadDocument(path);
			}
		};
		lp.addLocalizationListener(() -> action.putValue(Action.NAME, lp.getString("open")));
		action.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control O")); 
		return action;
	}

	public static Action saveDocumentAction(MultipleDocumentModel model, Component parent) {
		Action action = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if(model.getCurrentDocument().getFilePath() == null) {
					saveAs(model, parent);
					return;
				}
				model.saveDocument(model.getCurrentDocument(), null);
			}


		};
		lp.addLocalizationListener(() -> action.putValue(Action.NAME, lp.getString("save")));
		action.setEnabled(false);
		action.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control S")); 
		return action;
	}

	public static Action saveAsDocumentAction(MultipleDocumentModel model, Component parent) {
		Action action = new AbstractAction() {

			private static final long serialVersionUID = 1L;



			@Override
			public void actionPerformed(ActionEvent e) {
				saveAs(model, parent);
			}
		};
		lp.addLocalizationListener(() -> action.putValue(Action.NAME, lp.getString("saveAs")));
		action.setEnabled(false);
		action.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control shift S")); 
		return action;
	}
	public static Action closeDocumentAction(MultipleDocumentModel model) {
		Action action = new AbstractAction() {

			private static final long serialVersionUID = 1L;



			@Override
			public void actionPerformed(ActionEvent e) {
				model.closeDocument(model.getCurrentDocument());
			}
		};
		lp.addLocalizationListener(() -> action.putValue(Action.NAME, lp.getString("close")));
		action.setEnabled(false);
		action.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control W")); 
		return action;
	}

	public static Action statisticDocumentAction(MultipleDocumentModel model, JFrame frame) {
		Action action = new AbstractAction() {

			private static final long serialVersionUID = 1L;



			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame,
						model.getCurrentDocument().statisicInfo(),
						lp.getString("statisticInfo"),
						JOptionPane.NO_OPTION);
			}
		};
		lp.addLocalizationListener(() -> action.putValue(Action.NAME, lp.getString("statisticInfo")));
		action.setEnabled(false);
		action.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control I")); 
		action.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_I);
		return action;
	}

	public static Action exitDocumentAction(JFrame frame) {
		Action action = new AbstractAction() {

			private static final long serialVersionUID = 1L;



			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		};
		lp.addLocalizationListener(() -> action.putValue(Action.NAME, lp.getString("exit")));
		return action;
	}

	public static Action copyDocumentAction(MultipleDocumentModel model) {
		Action action = new AbstractAction() {

			private static final long serialVersionUID = 1L;



			@Override
			public void actionPerformed(ActionEvent e) {
				model.getCurrentDocument().getTextComponent().copy();
			}
		};
		lp.addLocalizationListener(() -> action.putValue(Action.NAME, lp.getString("copy")));
		action.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control C")); 
		return action;
	}

	public static Action cutDocumentAction(MultipleDocumentModel model) {
		Action action = new AbstractAction() {

			private static final long serialVersionUID = 1L;



			@Override
			public void actionPerformed(ActionEvent e) {
				model.getCurrentDocument().getTextComponent().cut();
			}
		};
		lp.addLocalizationListener(() -> action.putValue(Action.NAME, lp.getString("cut")));
		action.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control X")); 
		return action;
	}

	public static Action pasteDocumentAction(MultipleDocumentModel model) {
		Action action = new AbstractAction() {

			private static final long serialVersionUID = 1L;



			@Override
			public void actionPerformed(ActionEvent e) {
				model.getCurrentDocument().getTextComponent().paste();
			}
		};
		lp.addLocalizationListener(() -> action.putValue(Action.NAME, lp.getString("paste")));
		action.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control V")); 
		return action;
	}

	private static Path fileChoice(Component parent) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Open file");
		if(fc.showOpenDialog(parent)!=JFileChooser.APPROVE_OPTION) {
			return null;
		}
		File fileName = fc.getSelectedFile();
		Path filePath = fileName.toPath();
		return filePath;
	}

	private static void saveAs(MultipleDocumentModel model, Component parent) {
		Path path = fileChoice(parent);
		if(path == null) return;
		try {
			model.saveDocument(model.getCurrentDocument(), path);
		} catch (IllegalStateException e) {
			JOptionPane.showMessageDialog(parent,
					"Datoteka na toj putanji je već otvorena",
					lp.getString("warning"),
					JOptionPane.WARNING_MESSAGE  );
		}

	}

	public static Action chancecase(MultipleDocumentModel model, Function<String, String> function,Boolean allLine) {
		Action action = new AbstractAction() {

			private static final long serialVersionUID = 1L;



			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int[] startEnd = getStartEnd(allLine, model);
					String text;
					text = model.getCurrentDocument().getTextComponent().getDocument().getText(startEnd[0], startEnd[1] - startEnd[0]);
					model.getCurrentDocument().getTextComponent().getDocument().remove(startEnd[0], startEnd[1] - startEnd[0]);
					model.getCurrentDocument().getTextComponent().getDocument().insertString(startEnd[0], function.apply(text), null);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		};
		action.setEnabled(false);
		return action;

	}
	private static int[] getStartEnd(boolean allLine, MultipleDocumentModel model) throws BadLocationException {
		int[] startEnd = new int[2];
		int one = model.getCurrentDocument().getTextComponent().getCaret().getDot();
		int two = model.getCurrentDocument().getTextComponent().getCaret().getMark();
		int startline = model.getCurrentDocument().getTextComponent().getLineOfOffset(Math.min(one, two));
		startEnd[0] = allLine ? model.getCurrentDocument().getTextComponent().getLineStartOffset(startline) : Math.min(one, two); 
		int endline = model.getCurrentDocument().getTextComponent().getLineOfOffset(Math.max(one, two));
		startEnd[1] = allLine ? model.getCurrentDocument().getTextComponent().getLineEndOffset(endline) : Math.max(one, two); 
		return startEnd;
		
 	}

}
