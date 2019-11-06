package controller;

import java.util.ArrayList;

import model.Chapter;
import model.Main;
import model.Paragraph;

public class AnalizeController {
	private ArrayList<Chapter> chapter;
	
	private ArrayList<String> analizeText = new ArrayList<String>();
	private ArrayList<String> analizeX = new ArrayList<String>();
	private ArrayList<String> analizeFont = new ArrayList<String>();
	
	private int counter = 0;
	
//	public void analize(){
//	chapter = Main.getSession().getPDFController().getChapter();
//
//		if (chapter.size()>=2){
//			for (int i=0; i<chapter.get(0).getParagraphs().size();i++){
//				addAnalizeData(chapter.get(0), i);
//			}
//			chapter.remove(0);
//		}
//
//		if(counter==Main.getSession().getEnd()-1){
//			for(Chapter ch:chapter){
//				for (int i=0; i<ch.getParagraphs().size();i++){	{
//					addAnalizeData(ch, i);
//					}
//				}
//			}
//		}
//		counter++;
//	}
	
	private void addAnalizeData(Chapter chap, int i){
		Paragraph paragraph = chap.getParagraphs().get(i);
		switch (paragraph.getOrdDetail()){
		case 0:
			analizeText.add("||||"+paragraph.getParagraph()+"||||\n");
			analizeFont.add("||||"+paragraph.getFont()+"|||| \n");
			analizeX.add("||||"+paragraph.getPosition()+"||||  \n");
			break;
		case 1:
			analizeText.add("----"+paragraph.getParagraph()+"\n");
			analizeFont.add("----"+paragraph.getFont()+" \n");
			analizeX.add("----"+paragraph.getPosition()+"  \n");
			break;
		case 2:
			analizeText.add("->"+paragraph.getParagraph()+"<-\n");
			analizeFont.add("->"+paragraph.getFont()+"<- \n");
			analizeX.add("->"+paragraph.getPosition()+"<-  \n");		
			break;
		case 3:
			analizeText.add(""+paragraph.getParagraph()+"\n");
			analizeFont.add(""+paragraph.getFont()+" \n");
			analizeX.add(""+paragraph.getPosition()+"  \n");
			break;			
		}
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
