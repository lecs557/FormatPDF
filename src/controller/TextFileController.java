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
import model.Paragraph;

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
//	public void writeDailytxt() throws IOException {
//		if (!analizeOnly){
//			chapter = Main.getSession().getPDFController().getChapter();
//
//			if(chapter.size()>=2){
//				if(fw==null){
//					bw = new FileOutputStream(Main.getSession().getDestination()+"\\productTitle.txt");
//					fw = new BufferedWriter(new OutputStreamWriter(bw,
//							StandardCharsets.UTF_8));
//				}
//
//				Chapter today = chapter.get(0);
//				int length = today.getParagraphs().size();
//				for (int i = 0; i < length; i++) {
//					write(today, i);
//					if(i==length-1)
//						fw.append("ENDEDESKAPTELS\r\n\r\n");
//				}
//			}
//			if(counter==Main.getSession().getEnd()-1){
//				int a=0;
//				for(Chapter ch:chapter){
//					for (int i=0; i<ch.getParagraphs().size();i++){
//						write(ch, i);
//						if(i==ch.getParagraphs().size()-1)
//							fw.append("ENDEDESKAPTELS UND BUCHENDE\r\n\r\n");
//					}
//					System.out.println(a);
//					a++;
//				}
//				fw.close();
//			}
//		}
//		counter++;
//	}
	
//	private void write(Chapter chap, int i) throws IOException{
//		Paragraph paragraph = chap.getParagraphs().get(i);
//		switch (paragraph.getOrdDetail()){
//		case 0:
//			fw.append("[{product_id:2,number:"+counter+",title:"+paragraph.getParagraph()+",text:\r\n");
//			break;
//		case 1:
//			fw.append("<h1>"+paragraph.getParagraph()+"</h1>\r\n");
//			break;
//		case 2:
//			fw.append("<b>"+paragraph.getParagraph()+"</b>");
//			break;
//		case 3:
//			fw.append("<p style=\"margin-left:0cm; margin-right:0cm\">"+paragraph.getParagraph()+"</p>\r\n");
//			break;
//		}
//	}
}
