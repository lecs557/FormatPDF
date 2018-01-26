package controller;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import model.Chapter;
import model.Main;

public class TextFileController {
	private ArrayList<Chapter> chapter;
	private int counter = 0;
	private boolean analizeOnly = false;
	private FileOutputStream bw;
	private Writer fw; 
	
	/**
	 * writes the daily-objects in different files and
	 * their segments are divided with two paragraphs
	 * @throws IOException
	 */
	public void writeDailytxt() throws IOException {
		if (!analizeOnly){
			chapter = Main.getSession().getPDFController().getChapter();
			Chapter today = chapter.get(0);
			if(counter>3){
				int length = today.getParagraphs().size();
				try{
					if(fw==null){
						bw = new FileOutputStream(Main.getSession().getDestination()+"\\productTitle.txt");
						fw = new BufferedWriter(new OutputStreamWriter(bw,				
								StandardCharsets.UTF_8));
					}
				
				for (int i = 0; i < length; i++) {
					if (today.getParagraphs().get(i).getOrdDetail()==0){
						fw.append("[{product_id:2,number:"+counter+",title:"+today.getParagraphs().get(0).getParagraph()+",text:\r\n");
					}else if(today.getParagraphs().get(i).getOrdDetail()==1)
						fw.append("<h1>"+today.getParagraphs().get(i).getParagraph()+"</h1>\r\n");
					else if(today.getParagraphs().get(i).getOrdDetail()==2)
						fw.append("<p style=\"margin-left:0cm; margin-right:0cm\">"+today.getParagraphs().get(i).getParagraph()+"</p>\r\n");
					if(i==length-1)
						fw.append("ENDEDESKAPTELS\r\n\r\n");
				}			
				
				} catch(Exception e){
					counter++;
					e.printStackTrace();
				}
			}
			if(counter==Main.getSession().getEnd()-1){
				int a=0;
				for(Chapter ch:chapter){
					if(a!=0){
						for (int i=0; i<ch.getParagraphs().size();i++){	
							if (ch.getParagraphs().get(i).getOrdDetail()==0){
								fw.append("[{product_id:2,number:"+counter+",title:"+ch.getParagraphs().get(0).getParagraph()+",text:\r\n");
							}else if(ch.getParagraphs().get(i).getOrdDetail()==1)
								fw.append("<h1>"+ch.getParagraphs().get(i).getParagraph()+"</h1>\r\n");
							else if(ch.getParagraphs().get(i).getOrdDetail()==2)
								fw.append("<p style=\"margin-left:0cm; margin-right:0cm\">"+ch.getParagraphs().get(i).getParagraph()+"</p>\r\n");
							if(i==ch.getParagraphs().size()-1)
								fw.append("ENDEDESKAPTELS\r\n\r\n");			
						}
					}
					a++;	
				}
				fw.close();
			}
		}
		counter++;
	}
	
}
