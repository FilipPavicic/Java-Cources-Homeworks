package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.CollationKey;
import java.text.Collator;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;

import hr.fer.oprpp1.hw08.jnotepadpp.local.*;





public class JNotepadPP extends JFrame{

	private static final long serialVersionUID = 1L;
	DefaultMultipleDocumentModel model;
	Action save;

	private FormLocalizationProvider flp;
	ILocalizationProvider provider = LocalizationProvider.getInstance();

	public JNotepadPP() {

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(50, 50);
		setSize(800, 800);
		setTitle("JNotepad++");

		initGUI();
	}



	private void initGUI() {

		flp = new FormLocalizationProvider(provider, this);

		model = new DefaultMultipleDocumentModel();
		add(model);
		createMenus();
		createWindowsListeners();
		createStatusBar();

	}


	boolean returnValue = false;

	private void createWindowsListeners() {

		WindowListener lw = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				String[] opcije = new String[] { 
						provider.getString("yes"),
						provider.getString("no"),
						provider.getString("cancel")
				};
				
				for (SingleDocumentModel singleModel : model) {
					String name = singleModel.getFilePath() == null ? "(undefined)" : singleModel.getFilePath().getFileName().toString();

					if(singleModel.isModified() == true) {
						int  rezultat = JOptionPane.showOptionDialog(JNotepadPP.this, 
								provider.getString("thereIsUnsavedChangesInTheFile") 
								+ " " + name + " " + 
								provider.getString("doYouWantToSaveChanges"),
								provider.getString("warning"), 
								JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opcije, opcije[0]);
						switch (rezultat) {
							case JOptionPane.CLOSED_OPTION, 2: {
								returnValue = true;
								return;
							}
							case 0 : {
								save.actionPerformed(null);
								model.closeDocument(singleModel);
								break;
							}
							case 1 : {
								model.closeDocument(singleModel);
								break;
							}
							default :
								throw new IllegalArgumentException(
										"Unexpected value: " + rezultat);
						}
					}
				}

				
				if(returnValue) {
					returnValue = false;
					return;
				}
				dispose();
				return;

			}
		};
		this.addWindowListener(lw);

	}



	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu(new LocalizableAction("file", flp));
		fileMenu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(fileMenu);

		Action close = Actions.closeDocumentAction(model);
		save = Actions.saveDocumentAction(model, this);
		Action saveAs = Actions.saveAsDocumentAction(model, this);
		Action statistic = Actions.statisticDocumentAction(model, this);

		model.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void documentRemoved(SingleDocumentModel singleDocumentModel) {
				if(model.getCurrentDocument() == null) {
					close.setEnabled(false);
					save.setEnabled(false);
					saveAs.setEnabled(false);
					statistic.setEnabled(false);
				}

			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
				close.setEnabled(true);
				save.setEnabled(true);
				saveAs.setEnabled(true);
				statistic.setEnabled(true);

			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel,
					SingleDocumentModel currentModel) {
				if(currentModel == null) {
					setTitle("JNotepad++");
					return;
				}

				setTitle(
						(currentModel.getFilePath() == null ? "(undefined)" : currentModel.getFilePath().toAbsolutePath().toString())
						+ " - JNotepad++"
						);
			}
		});

		fileMenu.add(new JMenuItem(Actions.newDocumentAction(model)));
		fileMenu.add(new JMenuItem(Actions.openDocumentAction(model, this)));
		fileMenu.add(new JMenuItem(close));
		fileMenu.add(new JMenuItem(save));
		fileMenu.add(new JMenuItem(saveAs));
		fileMenu.add(new JMenuItem(statistic));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(Actions.exitDocumentAction(this)));

		JMenu editMenu = new JMenu(new LocalizableAction("edit", flp));
		editMenu.setMnemonic(KeyEvent.VK_E);
		menuBar.add(editMenu);
		editMenu.add(new JMenuItem(Actions.copyDocumentAction(model)));
		editMenu.add(new JMenuItem(Actions.cutDocumentAction(model)));
		editMenu.add(new JMenuItem(Actions.pasteDocumentAction(model)));

		JMenu languageMenu = new JMenu(new LocalizableAction("languages", flp));

		JMenuItem hr = new JMenuItem("Hrvatski");
		JMenuItem en = new JMenuItem("English");
		JMenuItem de = new JMenuItem("Deutsche");

		hr.addActionListener(e -> LocalizationProvider.getInstance().setLanguage("hr"));
		en.addActionListener(e -> LocalizationProvider.getInstance().setLanguage("en"));
		de.addActionListener(e -> LocalizationProvider.getInstance().setLanguage("de"));

		languageMenu.add(hr);
		languageMenu.add(en);
		languageMenu.add(de);

		menuBar.add(languageMenu);

		createTools(menuBar);


		this.setJMenuBar(menuBar);

		JToolBar t = new JToolBar();
		t.add(new JButton(Actions.newDocumentAction(model)));
		t.add(new JButton(Actions.openDocumentAction(model, this)));
		t.add(new JButton(save));
		t.add(new JButton(saveAs));
		t.add(new JButton(close));
		t.add(new JButton(statistic));
		t.add(new JButton(Actions.exitDocumentAction(this)));


		this.add(t, BorderLayout.PAGE_START);


	}

	private void createTools(JMenuBar menuBar) {

		JMenu toolsMenu = new JMenu(new LocalizableAction("tools", flp));
		Action tuUpper = Actions.chancecase(model, String::toUpperCase, false);
		provider.addLocalizationListener(() -> tuUpper.putValue(Action.NAME, provider.getString("tuUpper")));
		Action tuLower = Actions.chancecase(model, String::toLowerCase, false);
		provider.addLocalizationListener(() -> tuLower.putValue(Action.NAME, provider.getString("tuLower")));
		Action invert = Actions.chancecase(model, (s) -> {
			char[] znakovi = s.toCharArray();
			for(int i = 0; i < znakovi.length; i++) {
				char c = znakovi[i];
				if(Character.isLowerCase(c)) {
					znakovi[i] = Character.toUpperCase(c);
				} else if(Character.isUpperCase(c)) {
					znakovi[i] = Character.toLowerCase(c);
				}
			}
			return new String(znakovi);
		}, false);
		provider.addLocalizationListener(() -> invert.putValue(Action.NAME, provider.getString("invert")));

		Action ascending = Actions.chancecase(model, s -> sortString(s, false), true);
		provider.addLocalizationListener(() -> ascending.putValue(Action.NAME, provider.getString("ascending")));
		Action descending = Actions.chancecase(model, s -> sortString(s, true), true);
		provider.addLocalizationListener(() -> descending.putValue(Action.NAME, provider.getString("descending")));

		Action unique = Actions.chancecase(model, s -> uniqueLine(s), true);
		provider.addLocalizationListener(() -> unique.putValue(Action.NAME, provider.getString("uniqueLine")));
		
		
		JMenu changeCase = new JMenu(new LocalizableAction("changeCase", flp));

		changeCase.add(new JMenuItem(tuUpper));
		changeCase.add(new JMenuItem(tuLower));
		changeCase.add(new JMenuItem(invert));
		toolsMenu.add(changeCase);
		
		JMenu sort = new JMenu(new LocalizableAction("sort", flp));
		sort.add(ascending);
		sort.add(descending);
		toolsMenu.add(sort);
		
		toolsMenu.add(unique);


		menuBar.add(toolsMenu);

		model.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void documentRemoved(SingleDocumentModel singleModel) {
				// TODO Auto-generated method stub

			}

			@Override
			public void documentAdded(SingleDocumentModel singleModel) {
				singleModel.getTextComponent().addCaretListener(t -> setCarretInfo(singleModel));

			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel,
					SingleDocumentModel currentModel) {
				if(currentModel != null) setCarretInfo(currentModel);

			}

			private void setCarretInfo(SingleDocumentModel currentModel) {
				JTextArea editor = currentModel.getTextComponent();
				Document doc = editor.getDocument();
				Element root = doc.getDefaultRootElement();
				int sel = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
				if(sel == 0) {
					setEnableTools(false);
				}else {
					setEnableTools(true);

				}


			}

			private void setEnableTools(boolean b) {
				tuUpper.setEnabled(b);
				tuLower.setEnabled(b);
				invert.setEnabled(b);
				ascending.setEnabled(b);
				descending.setEnabled(b);
				unique.setEnabled(b);
			}
		});

	}





	



	private void createStatusBar() {
		JStatusBar bar = new JStatusBar(this);
		add(bar, BorderLayout.SOUTH);
		model.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void documentRemoved(SingleDocumentModel singleModel) {
				if(model.getCurrentDocument() == null) bar.setVisible(false);


			}

			@Override
			public void documentAdded(SingleDocumentModel singleModel) {
				bar.setVisible(true);
				singleModel.getTextComponent().getDocument().addDocumentListener(new DocumentAdapter() {

					@Override
					protected void textChanged() {
						bar.setLength(singleModel.getTextComponent().getText().length());

					}


				});
				singleModel.getTextComponent().addCaretListener(t -> setCarretInfo(singleModel.getTextComponent()));


			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel,
					SingleDocumentModel currentModel) {
				if(currentModel == null) return;

				bar.setLength(currentModel.getTextComponent().getText().length());
				setCarretInfo(currentModel.getTextComponent());



			}
			private void setCarretInfo(JTextArea textComponent) {
				JTextArea editor = textComponent;
				int sel = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
				try {
					int ln = editor.getLineOfOffset(editor.getCaretPosition()) ;
					int col = editor.getCaretPosition() - editor.getLineStartOffset(ln);
					bar.setStatus(ln +1, col +1, sel);

				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

	}

	private String sortString(String s, Boolean reverse) {

		String[] parts = s.split("\r\n|\r|\n");
		return Stream.of(parts).map((String s1) -> {
			String[] parts1 = s1.split("\t| ");
			Comparator<Object> comparator = reverse ? 
					Collator.getInstance(provider.getLocale()).reversed() :
						Collator.getInstance(provider.getLocale());
			return Stream.of(parts1).sorted(comparator).collect(Collectors.joining(" "));
		}).collect(Collectors.joining("\n"));
	}
	
	private String uniqueLine(String s) {
		String[] parts = s.split("\r\n|\r|\n");
		return Stream.of(parts).distinct().collect(Collectors.joining("\n"));
	}



	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new JNotepadPP().setVisible(true);
				LocalizationProvider.getInstance().setLanguage("en");
			}
		});
	}
}
