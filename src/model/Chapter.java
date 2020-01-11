package model;

import java.util.ArrayList;

/**
 * Class contains the different segments
 * a daily text has
 * @author Marcel
 */
public class Chapter {

	private ArrayList<Word> title;
	private ArrayList<Paragraph> paragraphs;
		
	public Chapter() {
		title = new ArrayList<>();
		paragraphs = new ArrayList<Paragraph>();
	}

	public void add(Paragraph paragraph){
		paragraphs.add(paragraph);
	}
	
	public ArrayList<Paragraph> getParagraphs(){
		return paragraphs;
	}

	public void addToTitle(Word word){
		title.add(word);
	}

	public ArrayList<Word> getTitle() {
		return title;
	}
}
