package controller;

import java.util.ArrayList;

import model.Chapter;
import model.Main;
import model.Session;

public class AnalizeController {
	private Session session = Main.getSession();
	private TextFileController tfc = session.getTextFileController();
	private Chapter today;
	
	private ArrayList<ArrayList<String>> amountOfSegments = new ArrayList<ArrayList<String>>();
	private ArrayList<String> analizeText = new ArrayList<String>();
	private ArrayList<String> analizeX = new ArrayList<String>();
	private ArrayList<String> analizeFont = new ArrayList<String>();
	private ArrayList<String> amountOfMonthSegments;
	private String todayAnalizeText;
	private String todayAnalizeX;
	private String todayAnalizeFont;
	private boolean analizeOnly = true;
	
	private String month="";
	private int k = session.getStart();
	
	
	
	public void analize(){
		today = Main.getSession().getPDFController().getToday();
		if(!analizeOnly){
			if (!tfc.isError()){
				if ( month.equals(today.getMonth()) ){
					amountOfMonthSegments.add(k +" "+ (today.getDay().size()-(today.isHasTitle()?4:3)));	
					
				} else{
					amountOfMonthSegments = new ArrayList<String>();
					amountOfSegments.add(amountOfMonthSegments);
					amountOfMonthSegments.add(k +" "+ (today.getDay().size()-(today.isHasTitle()?4:3)));
					month = today.getMonth();
				}
			}			
		}
			analizeText.add(todayAnalizeText);
			analizeX.add(todayAnalizeX);
			analizeFont.add(todayAnalizeFont);
			k++;
	}
	
	
	
	public ArrayList<String> getAnalizeFont() {
		return analizeFont;
	}
	public void setTodayAnalizeFont(String todayAnalizeFont) {
		this.todayAnalizeFont = todayAnalizeFont;
	}
	public void setTodayAnalizeText(String analizeText) {
		this.todayAnalizeText = analizeText;
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
