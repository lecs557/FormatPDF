package controller;

import java.util.ArrayList;

import model.Chapter;
import model.Main;

public class AnalizeController {
	private ArrayList<Chapter> chapter;
	
	private ArrayList<String> analizeText = new ArrayList<String>();
	private ArrayList<String> analizeX = new ArrayList<String>();
	private ArrayList<String> analizeFont = new ArrayList<String>();
	
	private int counter = 0;
	
	public void analize(){
		chapter= Main.getSession().getPDFController().getChapter();
		if (counter>3){
			showChapter();
			for (int i=1; i<chapter.get(0).getParagraphs().size();i++){	
				analizeText.add(chapter.get(0).getParagraphs().get(i).getParagraph()+"\n");
				analizeFont.add(chapter.get(0).getFonts().get(i)+"\n");
				analizeX.add(chapter.get(0).getPositions().get(i)+"\n");	
			}
			chapter.remove(0);			
		}
		if(counter==Main.getSession().getEnd()-1){
			for(Chapter ch:chapter){
				showChapter(ch);
				for (int i=1; i<ch.getParagraphs().size();i++){	{
					analizeText.add(ch.getParagraphs().get(i).getParagraph()+"\n");
					analizeFont.add(ch.getFonts().get(i)+"\n");
					analizeX.add(ch.getPositions().get(i)+"\n");							
					}
				}
			}
		}
		counter++;
	}
	
	private void showChapter(){
		String chapterTitle = chapter.get(0).getParagraphs().get(0).getParagraph();
		analizeText.add("--------"+chapterTitle+"\n");
		analizeFont.add("--------"+chapterTitle+" \n");
		analizeX.add("--------"+chapterTitle+"  \n");
	}
	
	private void showChapter(Chapter ch){
		String chapterTitle = ch.getParagraphs().get(0).getParagraph();
		analizeText.add("--------"+chapterTitle+"\n");
		analizeFont.add("--------"+chapterTitle+" \n");
		analizeX.add("--------"+chapterTitle+"  \n");
	}
	
	public ArrayList<String> getAnalizeFont() {
		return analizeFont;
	}
	public ArrayList<String> getAnalizeText() {
		return analizeText;
	}
	public ArrayList<String> getAnalizeX() {
		return analizeX;
	}
}
