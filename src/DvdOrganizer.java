/**
 * @date 29.06.2008 
 * @author Maarten
 * 
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class DvdOrganizer {
	int number = 0;
	String title = "";
	Vector<String> output = new Vector<String>();

	public DvdOrganizer(int number) {
		this.number = number;
		searchNumber();
	}

	public DvdOrganizer(String title, boolean search) {
		this.title = title;
		if (search) { searchTitle(); } else { addTitle(); }
	}

	public void searchNumber() {
		String query = "select NR, TITEL from [dvds$] where NR =" + number + ";";
		executeQuery(query, false);
	}

	public void searchTitle() {
		String query = "select NR, TITEL from [dvds$] where TITEL like '%" + title.substring(1) + "%';";
		executeQuery(query, false);
	}

	public void addTitle() {
		String query = "insert into [dvds$] (TITEL) values ('" + title + "');";
		executeQuery(query, true);
	}

	public void executeQuery(String query, boolean update) {
		Connection c = null;
		Statement stmnt = null;
		try {
			Class.forName( "sun.jdbc.odbc.JdbcOdbcDriver" );
			c = DriverManager.getConnection( "jdbc:odbc:dvd-list", "", "" );
			stmnt = c.createStatement();
			if (!update) {
				ResultSet rs = stmnt.executeQuery( query );
				while( rs.next() ) {
					double nr = Double.parseDouble(rs.getString( "NR" ));
					output.add( (int) nr + ") " + rs.getString( "TITEL" ));
				}
			} else {
				//get last id in list, add one to this id and use it as new id
				ResultSet rs = stmnt.executeQuery("select MAX(NR) AS X from [dvds$];");
				if (rs.next()) {
					double nr = Double.parseDouble(rs.getString( "X" ))+1;
					stmnt.executeUpdate("insert into [dvds$] (NR, TITEL) values (" + (int) nr + ", '" + title + "');");
				}
			}
		}
		catch( Exception e ) {
			System.err.println( e );
		}
		finally {
			try {
				stmnt.close();
				c.close();
			}
			catch( Exception e ) {
				System.err.println( e );
			}
		}
	}
	
	public Vector<String> getOutput() {
		return output;
	}
}