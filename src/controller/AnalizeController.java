package controller;

import java.util.ArrayList;

import model.Chapter;
import model.Main;
import model.Session;

public class AnalizeController {
	private Session session = Main.getSession();
	private TextFileController tfc = session.getTextFileController();
	private ArrayList<Chapter> chapter;
	
	private ArrayList<ArrayList<String>> amountOfSegments = new ArrayList<ArrayList<String>>();
	private ArrayList<String> analizeText = new ArrayList<String>();
	private ArrayList<String> analizeX = new ArrayList<String>();
	private ArrayList<String> analizeFont = new ArrayList<String>();
	private ArrayList<String> amountOfMonthSegments;
	private String todayAnalizeX;
	private String todayAnalizeFont;
	private boolean analizeOnly = true;
	
	private String month="";
	private int k = session.getStart();
	
	
	
	public void analize(){
		chapter= Main.getSession().getPDFController().getChapter();
		if(!analizeOnly){
//			if (!tfc.isError()){
//				if ( month.equals(today.getMonth()) ){
//					amountOfMonthSegments.add(k +" "+ (today.getDay().size()-(today.isHasTitle()?4:3)));	
//					
//				} else{
//					amountOfMonthSegments = new ArrayList<String>();
//					amountOfSegments.add(amountOfMonthSegments);
//					amountOfMonthSegments.add(k +" "+ (today.getDay().size()-(today.isHasTitle()?4:3)));
//					month = today.getMonth();
//				}
//			}			
		}
		analizeText.add(k+"--------CHAPTER  \n");
		analizeFont.add(k+"--------CHAPTER  \n");
		analizeX.add(k+"--------CHAPTER  \n");
		for (int i=0; i<chapter.get(0).getParagraphs().size();i++){	
			if (k>3){
				analizeText.add(chapter.get(0).getParagraphs().get(i).getParagraph()+"\n");
				analizeFont.add(chapter.get(0).getFonts().get(i)+"\n");
				analizeX.add(chapter.get(0).getPositions().get(i)+"\n");	
			}
		}
		if (k>3){
			chapter.remove(0);			
		}
		if(k==Main.getSession().getEnd()){
			for(Chapter ch:chapter){
				analizeText.add(k+"--------CHAPTER  \n");
				analizeFont.add(k+"--------CHAPTER  \n");
				analizeX.add(k+"--------CHAPTER  \n");
				for (int i=0; i<ch.getParagraphs().size();i++){	{
					analizeText.add(ch.getParagraphs().get(i).getParagraph()+"\n");
					analizeFont.add(ch.getFonts().get(i)+"\n");
					analizeX.add(ch.getPositions().get(i)+"\n");							
					}
				}
			}
		}
		k++;
	}
	
	
	
	public ArrayList<String> getAnalizeFont() {
		return analizeFont;
	}
	public void setTodayAnalizeFont(String todayAnalizeFont) {
		this.todayAnalizeFont = todayAnalizeFont;
	}
	public void setTodayAnalizeX(String analizeX) {
		this.todayAnalizeX = analizeX;
	}
	public ArrayList<ArrayList<String>> getAmountOfSegments() {
		return amountOfSegments;
	}
	public ArrayList<String> getAnalizeText() {
		return analizeText;
	}
	public ArrayList<String> getAnalizeX() {
		return analizeX;
	}
}
