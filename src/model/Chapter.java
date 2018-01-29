package model;

import java.util.ArrayList;

/**
 * Class contains the different segments
 * a daily text has
 * @author Marcel
 */
public class Chapter {
	
	private ArrayList<Paragraph> paragraphs;
		
	public Chapter() {
		paragraphs = new ArrayList<Paragraph>();
	}
	
	public ArrayList<Paragraph> getParagraphs(){
		return paragraphs;
	}
	
}
