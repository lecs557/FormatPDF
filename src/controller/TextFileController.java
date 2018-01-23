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
			try{
				today = Main.getSession().getPDFController().getChapter().get(0);
				int length = today.getParagraphs().size();
				FileOutputStream bw = new FileOutputStream(Main.getSession().getDestination()+"\\productTitle.txt");
				Writer fw = new BufferedWriter(new OutputStreamWriter(bw,
						StandardCharsets.UTF_8));
				for (int i = 0; i < length-1; i++) {
					if (i==0){
						fw.append("[{product_id:2,number:1,title:"+today.getParagraphs().get(0)+",\r\n\r\n");
					}else
						fw.append("text:<p style=\"margin-left:0cm; margin-right:0cm\">"+today.getParagraphs().get(i)+"</p>\r\n");
				}
				j++;
				fw.close();
				
			} catch(Exception e){
				j++;
				errorCounter++;
				isError =true;
				e.printStackTrace();
			}
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
