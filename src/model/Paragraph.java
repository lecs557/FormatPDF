package model;

import com.itextpdf.text.pdf.parser.Vector;

import java.util.ArrayList;

public class Paragraph {

    public enum detail {title, heading, bold, paragraph};
	private ArrayList<Word> paragraph = new ArrayList<Word>();
	private String font;
	private String position;
	private int ordDetail;
	
	
	public Paragraph(Word word) {
		this.paragraph.add(word);
	}
	
	public void add (Word word){
		this.paragraph.add(word);
	}

	public ArrayList<Word> getParagraph() {
		return paragraph;
	}

	public int getOrdDetail() {
		return ordDetail;
	}

	public String getFont() {
		return font;
	}

	public String getPosition() {
		return position;
	}

    public String get() {
	    String words="";
	    for (Word w:paragraph){
	        words+=w.getWord()+" ";
        }
	    return words;

    }


}
