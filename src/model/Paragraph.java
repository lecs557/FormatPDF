package model;

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

	public ArrayList<Word> getWordList() {
		return paragraph;
	}

	public FormatController.format getFont() {
		return font;
	}

}
