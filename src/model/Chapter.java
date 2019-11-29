package model;

import java.util.ArrayList;

/**
 * Class contains the different segments
 * a daily text has
 * @author Marcel
 */
public class Chapter {
	
	private ArrayList<Paragraph> paragraphs;
		
	public Chapter(Paragraph paragraph) {
		paragraphs = new ArrayList<Paragraph>();
		paragraphs.add(paragraph);
	}

	public void add(Paragraph paragraph){
		paragraphs.add(paragraph);
	}
	
	public ArrayList<Paragraph> getParagraphs(){
		return paragraphs;
	}
	
}
