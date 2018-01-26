package model;

import java.util.ArrayList;

/**
 * Class contains the different segments
 * a daily text has
 * @author Marcel
 */
public class Chapter {
	
	private ArrayList<Paragraph> paragraphs;
	private ArrayList<String> fonts;
	private ArrayList<String> positions;
		
	public Chapter() {
		paragraphs = new ArrayList<Paragraph>();
		fonts = new ArrayList<String>() ;
		positions = new ArrayList<String>();
	}
	
	public ArrayList<Paragraph> getParagraphs(){
		return paragraphs;
	}

	public ArrayList<String> getFonts() {
		return fonts;
	}

	public ArrayList<String> getPositions() {
		return positions;
	}
	
	
}
