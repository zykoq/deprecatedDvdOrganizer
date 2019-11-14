package dvdtable;

public class Dvd {
	
	private String number = null;
	private String title = null;
	
	public Dvd() {
		
	}
	
	public Dvd(String number, String title) {
		this.number = number;
		this.title = title;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
