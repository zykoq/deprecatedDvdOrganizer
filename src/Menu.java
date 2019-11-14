/**
 * @date 29.06.2008 
 * @author Maarten
 *
 */

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Menu {
	private static Display display = new Display();
	private static Shell shell = new Shell(display);
	private static Text number = null;
	private static Text titel = null;
	private static DvdOrganizer org = null;
	private static List list = null;
	private static Composite pageComposite = null;

	public static void main(String[] args) {
		shell.setText("Dvd Organizer");
		GridLayout layout = new GridLayout(3, false);
		layout.horizontalSpacing = 20;
		shell.setLayout(layout);

		setLocation();
		loadTextFields();
		loadButtons();
		new Label(shell, SWT.NULL);
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	public static void setLocation() {
		Monitor primary = display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();    
		int x = bounds.x + (bounds.width - rect.width);
		int y = bounds.y + (bounds.height - rect.height);    
		shell.setLocation(x, y);
	}

	public static void loadTextFields() {
		GridData textData = new GridData();
		textData.widthHint = 85;
		textData.heightHint = 15;
		new Label(shell, SWT.NULL).setText("Nummer: ");
		new Label(shell, SWT.NULL);
		number = new Text(shell, SWT.BORDER);
		number.setLayoutData(textData);
		number.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent event) {
				if (event.keyCode == SWT.CR || event.keyCode == SWT.KEYPAD_CR) {
					searchEvent();
				}
			}
			@Override
			public void keyReleased(KeyEvent event) {
			}
		});
		verifyDigits();
		new Label(shell, SWT.NULL).setText("Titel: ");
		new Label(shell, SWT.NULL);
		titel = new Text(shell, SWT.BORDER);
		titel.setLayoutData(textData);
		titel.setFocus();
		titel.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent event) {
				if (event.keyCode == SWT.CR || event.keyCode == SWT.KEYPAD_CR) {
					searchEvent();
				}
			}
			@Override
			public void keyReleased(KeyEvent event) {
			}
		});

	}

	public static void loadButtons() {
		Button dvdsearch = new Button(shell, SWT.PUSH);
		new Label(shell, SWT.NULL);
		Button dvdadd = new Button(shell, SWT.PUSH);
		dvdsearch.setText("DVD Zoeken");
		dvdadd.setText("DVD Toevoegen");
		dvdsearch.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					searchEvent();
					break;
				}
			}
		});
		dvdadd.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.Selection:
					addEvent();
					break;
				}
			}
		});
	}

	public static void verifyDigits() {
		number.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if (!('0' <= chars[i] && chars[i] <= '9')) {
						e.doit = false;
						return;
					}
				}
			}
		});
	}

	public static void searchEvent() {
		if (number.getText() != "" ) {
			int nr = Integer.parseInt(number.getText());
			org = new DvdOrganizer(nr);
		} else if (titel.getText() != "") {
			org = new DvdOrganizer(titel.getText(), true);
		}
		if (org != null && org.getOutput() != null) {
			loadList(org.getOutput());
		}
	}

	public static void loadList(Vector<String> output) {
		if (list != null) {
			list.removeAll();
		}
		if ((pageComposite != null) && (!pageComposite.isDisposed())) {
			pageComposite.dispose();
		}
		pageComposite = new Composite(shell, SWT.NONE);
		pageComposite.setLayout(new GridLayout());
		pageComposite.setLayoutData(new GridData());
		list = new List(pageComposite, SWT.V_SCROLL);
		GridData data = new GridData();
		data.heightHint = 270;
		data.widthHint = 225;
		list.setLayoutData(data);
		while (output != null && !output.isEmpty()) {
			list.add(output.remove(0));
		}
		shell.setSize(500,450);
		shell.layout();
	}

	public static void addEvent() {
		String name = titel.getText();
		if (name != "") {
			org = new DvdOrganizer(name, false);
		}
	}
}