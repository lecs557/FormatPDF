package model;

import com.itextpdf.text.pdf.parser.Vector;

import java.util.ArrayList;

public class Paragraph {
	
	public enum detail {title, heading, bold, paragraph};
	private ArrayList<Word> paragraph = new ArrayList<Word>();
	private String font;
	private String position;
	private int ordDetail;
	
	
	public Paragraph(Word word, String font, Vector pos, detail detail, int size) {
		this.paragraph.add(word);
		this.font = font+" "+size;
		this.position = (int)pos.get(0)+" "+(int)pos.get(1);
		this.ordDetail = detail.ordinal();
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


}
