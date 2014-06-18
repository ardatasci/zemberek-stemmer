package Repesentation;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Label;

import KeywordExtraction.NamedEntityTurkish.Word;

public class ContentAugmentationWİndow {

	protected Shell shell;
	protected static ContentAugmentationResource contentAugmentationResource;
	private Text entityInfoArea;

//	/**
//	 * Launch the application.
//	 * @param args
//	 * @wbp.parser.entryPoint
//	 */
//	
//	public ContentAugmentationWİndow(){
//		contentAugmentationResource = new ContentAugmentationResource();
//	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public static void main() {
		try {
			contentAugmentationResource = new ContentAugmentationResource();
			ContentAugmentationWİndow window = new ContentAugmentationWİndow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(900, 629);
		shell.setText("Content Augmentation DEMO");
		
		final StyledText styledText = new StyledText(shell, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI | SWT.WRAP);
		styledText.setBounds(10, 43, 380, 475);
		
		entityInfoArea = new Text(shell, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL);
		entityInfoArea.setBounds(604, 44, 230, 230);
		
		final List list = new List(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		list.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int selectedIndex = list.getSelectionIndex();
				String info = contentAugmentationResource.getEntityInfo(list.getItem(selectedIndex));
				entityInfoArea.setText(info);
			}
		});

		
		Button btnAButton = new Button(shell, SWT.NONE);
		btnAButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String inputText = styledText.getText();
				try {
					contentAugmentationResource.setQueryTextToWikiParser(inputText);
					contentAugmentationResource.createEntityInfoMap(inputText);
					ArrayList<String> wordList = contentAugmentationResource.getEntityList();
					for (String wordContent : wordList) {
						list.add(wordContent);
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnAButton.setBounds(54, 541, 150, 29);
		btnAButton.setText("Augment the World");
		
		list.setBounds(413, 43, 175, 475);
		
		Label lblNewLabel = new Label(shell, SWT.CENTER);
		lblNewLabel.setBounds(604, 21, 230, 29);
		lblNewLabel.setText("Information About Selected Item");
		
		Label lblNamedEntities = new Label(shell, SWT.CENTER);
		lblNamedEntities.setBounds(431, 21, 130, 17);
		lblNamedEntities.setText("Named Entities");
		

		
		Button btnClearInput = new Button(shell, SWT.NONE);
		btnClearInput.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				styledText.setText("");
			}
		});
		btnClearInput.setBounds(226, 541, 91, 29);
		btnClearInput.setText("Clear Input");
		
		Label lblInputText = new Label(shell, SWT.NONE);
		lblInputText.setBounds(161, 21, 91, 17);
		lblInputText.setText("Input Text");
		
		Button btnClearEntities = new Button(shell, SWT.NONE);
		btnClearEntities.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				list.removeAll();
				entityInfoArea.setText("");
			}
		});
		btnClearEntities.setBounds(431, 541, 101, 29);
		btnClearEntities.setText("Clear Entities");
		


	}
}
