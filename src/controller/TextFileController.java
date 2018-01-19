package controller;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import model.Chapter;
import model.Main;

public class TextFileController {
	private Chapter today;
	private int j = Main.getSession().getStart();
	private int errorCounter = 0;
	private boolean isError;
	private boolean analizeOnly = true;
	
	/**
	 * writes the daily-objects in different files and
	 * their segments are divided with two paragraphs
	 * @throws IOException
	 */
	public void writeDailytxt() throws IOException {
		isError = false;
		if (!analizeOnly){
//			try{
//				today = Main.getSession().getPDFController().getToday();
//				int length = today.getDay().size();
//				FileOutputStream bw = new FileOutputStream(Main.getSession().getDestination()+ j +" " +today.getDatum() + ".txt");
//				Writer fw = new BufferedWriter(new OutputStreamWriter(bw,
//						StandardCharsets.UTF_8));
//				for (int i = 0; i < length-1; i++) {
//					if (i==0){
//						fw.append("VERS    "+today.getDay().get(i) + " VERS\r\n\r\n");
//					}else if (i==1){
//						fw.append("Stelle    "+today.getDay().get(i) + " Stelle\r\n\r\n");
//					} else if(i==2 && today.isHasTitle()){
//						fw.append("Title    "+today.getDay().get(i) + " Title\r\n\r\nTEXT\r\n");
//					}else if(i==2){
//						fw.append("TEXT\r\nPARAGRAPH    "+today.getDay().get(i) + "\r\n");
//					}else if(i==length-2){
//						fw.append("PARAGRAPH    "+today.getDay().get(i) + "\r\nTEXT");
//					}else
//						fw.append("PARAGRAPH    "+today.getDay().get(i) + "\r\n");
//				}
//				j++;
//				fw.close();
//				
//			} catch(Exception e){
//				j++;
//				errorCounter++;
//				isError =true;
//			}
		}
	}
	
	public boolean isError() {
		return isError;
	}

	public int getDays(){
		return j;
	}

	public int getErrorCounter() {
		return errorCounter;
	}

}
