/**
 * @date 22.05.2009 
 * @author Maarten
 */
package dvdtable;

import javax.swing.JTable;

public class DvdTable extends JTable {
	
	private static final long serialVersionUID = -4444727189760878420L;
	
	private DvdTableModel model = null;

	public DvdTable(DvdTableModel model) {
		super(model);
		this.model = model;
	}
	
	public boolean isCellEditable(int row, int column) {
		return model.isCellEditable(row, column);
	}
	
	
}
