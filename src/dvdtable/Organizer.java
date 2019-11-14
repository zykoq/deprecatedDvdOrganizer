/**
 * @date 22.05.2009 
 * @author Maarten
 *
 */
package dvdtable;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class Organizer {

	private static Vector<Dvd> dvds = new Vector<Dvd>();
//	private static DvdTable table = null;

	public static void main(String[] args) {
		loadDvds();
		JFrame frame = new JFrame("DVD Organizer");
		DvdTableModel model = new DvdTableModel(dvds);
		DvdTable table = new DvdTable(model);
		table.setFillsViewportHeight(true);
		JScrollPane pane = new JScrollPane();
		pane.setViewportView(table);
		frame.getContentPane().add(pane, BorderLayout.CENTER);
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void loadDvds() {
		Connection c = null;
		Statement stmnt = null;
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			c = DriverManager.getConnection("jdbc:odbc:dvd-list", "", "");
			stmnt = c.createStatement();
			ResultSet rs = stmnt.executeQuery("select NR, TITEL from [dvds$];");
			while (rs.next()) {
				Dvd dvd = new Dvd(rs.getString("NR"), rs.getString("TITEL"));
				dvds.add(dvd);
			}
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				stmnt.close();
				c.close();
			} catch (Exception e) {
				System.err.println(e);
			}
		}
		System.out.println(dvds.size() + " dvds added");
	}

}