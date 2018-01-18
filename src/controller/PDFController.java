package controller;

import java.io.IOException;
import java.util.ArrayList;

import model.Chapter;
import model.FontFilter;
import model.Main;
import model.Session;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.FilteredTextRenderListener;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.text.pdf.parser.Vector;

/**
 * A handler which undertakes tasks which have to do the
 * selected PDF-File
 * @author Marcel
 */
public class PDFController {	
	private Session session = Main.getSession();
	private AnalizeController analize = session.getAnalizeController();
	private PdfReader reader = session.getPdfReader();
	
	private Chapter today;
	private String todayAnalizeText ="";
	private String todayAnalizeX ="";
	private String todayAnalizeFont ="";
	private ArrayList<String> segment = new ArrayList<String>();
	
	private String oldFormat = "";
	
	private boolean isDate = false;
	private boolean wasBreak = false;
	private int oldy = 500;
	private int oldX = 0;
	private int day=session.getStart();

	
	/**
	 * Scans every word of the PDF-File, invokes <<FontFilter>> to get 
	 * the render info which are given to <createText>
	 * Can be invoked only for one page a time 
	 * @param path @param page of the PDF-File
	 */
	public void readPDF(int page) throws IOException {
		RenderFilter info = new FontFilter();
		TextExtractionStrategy strategy = new FilteredTextRenderListener(
				new LocationTextExtractionStrategy(), info);
		oldy = 500;
		todayAnalizeText="";
		todayAnalizeX="";
		todayAnalizeFont ="";
		today = new Chapter();
		@SuppressWarnings("unused") // <<FobtFilter>> is invoked here
		String content = PdfTextExtractor.getTextFromPage(reader, page,
				strategy);
		today.getDay().add(segment);
		todayAnalizeText += "\n------------Seite " + day + "---------\n";
		todayAnalizeX += "\n------------Seite " + day+ "---------\n";
		todayAnalizeFont += "\n------------Seite " + day+ "---------\n";
		isDate=false;
		analize.setTodayAnalizeText(todayAnalizeText);
		analize.setTodayAnalizeX(todayAnalizeX);
		analize.setTodayAnalizeFont(todayAnalizeFont);
		day++;
	}

	/**
	 * Tries to find out the different segments
	 * and puts the into the daily-Object. 
	 * @param rinfo
	 */
	public void createText(TextRenderInfo rinfo) {
		String word = rinfo.getText().replace("-", "");
		String font = rinfo.getFont().getPostscriptFontName();
		Vector start = rinfo.getBaseline().getStartPoint();
		if (!word.equals("")) {
			if (!oldFormat.equals(font) && yChanged(start)){
				if(!todayAnalizeText.isEmpty()){
					todayAnalizeText +="\n";
					todayAnalizeX +="\n";
					todayAnalizeFont+="\n";
				}
				segment.add(word);
				oldFormat = font;
				todayAnalizeFont +=font;
				todayAnalizeText +=word;	
				todayAnalizeX +=(int) start.get(0) + "  " + (int) start.get(1);
			}
			else if (isParagraph(start) && oldFormat.equals(font)) {
					segment.add(word);
					todayAnalizeX +="\n"+(int) start.get(0) + "  " + (int) start.get(1);
					todayAnalizeText +="\n" +word;								
			} else{
				segment.add(word);
				todayAnalizeText +=" "+word;
			
			}
		}
	}
	

	private boolean isParagraph(Vector start) {
		int x = (int) start.get(0);
		int y = (int) start.get(1);
		boolean newPara = y!=oldy && ( x - oldX > 3 &&  x - oldX < 18 );
		if(y!= oldy) {		
			oldX = x;
		}
		oldy = y;
		return newPara;
	}
	
	private boolean yChanged(Vector start){
		int y = (int) start.get(1);
		boolean changed = y!= oldy;
		oldy = (int) start.get(1);
		return changed;
	}

	private boolean isYbigger(Vector start) {
		boolean bigger = (int) start.get(1) > oldy;
		return bigger;
	}


	public Chapter getToday() {
		return today;
	}
}
