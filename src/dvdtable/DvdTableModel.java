/**
 * @date 22.05.2009 
 * @author Maarten
 */
package dvdtable;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class DvdTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -6572411562720125338L;

	private String columnNames[] = { "Nummer", "Titel" };
	private Vector<Dvd> data = new Vector<Dvd>();

	public DvdTableModel() {
	}

	public DvdTableModel(Vector<Dvd> data) {
		this.data = data;
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return data.size();
	}

	public Object getValueAt(int row, int column) {
		switch (column) {
		case 0:
			return data.get(row).getNumber();
		case 1:
			return data.get(row).getTitle();
		default:
			return "no value";
		}
	}

	public Class<?> getColumnClass(int arg0) {
		return Dvd.class;
	}

	public String getColumnName(int column) {
		return columnNames[column];
	}

	public boolean isCellEditable(int arg0, int arg1) {
		return true;
	}

	 public void setValueAt(Object arg0, int arg1, int arg2) {
		 super.setValueAt("Test", arg1, arg2);
	 }

	 /*
	 * Don't need to implement this method unless your table's data can
	 change.
	 */
	// public void setValueAt(Object value, int row, int col) {
	// data[row][col] = value;
	// fireTableCellUpdated(row, col);
	//
	// }
}
