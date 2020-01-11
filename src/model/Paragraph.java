package model;

import com.itextpdf.text.pdf.parser.Vector;
import controller.FormatController;

import java.util.ArrayList;

public class Paragraph {

	private FormatController.format font;
	private ArrayList<Word> paragraph = new ArrayList<Word>();

	
	public Paragraph(Word word) {
		this.paragraph.add(word);
		font=word.getFont();
	}
	
	public void add (Word word){
		this.paragraph.add(word);
	}

	public ArrayList<Word> getParagraph() {
		return paragraph;
	}

	public FormatController.format getFont() {
		return font;
	}
}
