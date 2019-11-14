/**
 * @date 08.04.2009 
 * @author Maarten
 * 
 */

package dvds;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class Handler {
	private Hashtable<String, String> dvds = null;

	public Handler() {
		System.out.println("bla");
		dvds = new Hashtable<String, String>();
		loadDvds();
	}

	public String getNumber(String number) {
		System.out.println(dvds.size());
		return dvds.get(number);
	}

	public Vector<String> getTitle(String title) {
		Vector<String> v = new Vector<String>();
		if (dvds.containsValue(title)) {
			Enumeration<String> e = dvds.elements();
			while(e.hasMoreElements()) {
				v.add(e.nextElement());
			}
		}
		return v;
	}

	public void addTitle() {
	}

	public void loadDvds() {
		System.out.println("1");
		Connection c = null;
		Statement stmnt = null;
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			c = DriverManager.getConnection("jdbc:odbc:dvd-list", "", "");
			stmnt = c.createStatement();
			System.out.println("2");
			ResultSet rs = stmnt.executeQuery("select NR, TITEL from [dvds$];");
			System.out.println(rs.toString());
			while (rs.next()) {
				dvds.put(rs.getString("NR"), rs.getString("TITEL"));
			}
			System.out.println(dvds.size());
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
		System.out.println("rdy");
	}
}