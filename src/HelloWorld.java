import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

public class HelloWorld {

	 // Strings to use as list items
	  private static final String[] ITEMS = { "A", "B", "C", "D" };

	  public static void main(String[] args) {
	    Display display = new Display();
	    Shell shell = new Shell(display);
	    shell.setLayout(new FillLayout());

	    // Create a single-selection list
	    List single = new List(shell, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);

	    // Add the items, one by one
	    for (int i = 0, n = ITEMS.length; i < n; i++) {
	      single.add(ITEMS[i]);
	    }

	    shell.open();
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch()) {
	        display.sleep();
	      }
	    }
	    display.dispose();
	  }
}