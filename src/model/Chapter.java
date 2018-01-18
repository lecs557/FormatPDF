package model;

import java.util.ArrayList;

/**
 * Class contains the different segments
 * a daily text has
 * @author Marcel
 */
public class Chapter {

	private ArrayList<ArrayList<String>> chapterText;
	private String datum;
	private boolean hasTitle;
	
	public Chapter() {
		chapterText = new ArrayList<ArrayList<String>>();
	}
	
	public ArrayList<ArrayList<String>> getDay(){
		return chapterText;
	}
	
	public void setDatum(String datum) {
		this.datum = datum;
	}

	public String getDatum(){
		return datum;
	}
	
	public String getMonth(){
		return datum.split(" ")[0];
	}

	public boolean isHasTitle() {
		return hasTitle;
	}

	public void setHasTitle(boolean hasTitle) {
		this.hasTitle = hasTitle;
	}
}
