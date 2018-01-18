package controller;

import java.io.IOException;

import model.DailyText;
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
	
	private DailyText today;
	private String todayAnalizeText ="";
	private String todayAnalizeX ="";
	private String segment = "";
	
	private boolean isDate = false;
	private int oldy = 500;
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
		segment="";
		todayAnalizeText="";
		todayAnalizeX="";
		today = new DailyText();
		@SuppressWarnings("unused") // <<FobtFilter>> is invoked here
		String content = PdfTextExtractor.getTextFromPage(reader, page,
				strategy);
		today.getDay().add(segment);
		createDatum();
		todayAnalizeText += "\n------------Seite " + day + "---------\n";
		todayAnalizeX += "\n------------Seite " + day+ "---------\n";
		isDate=false;
		analize.setTodayAnalizeText(todayAnalizeText);
		analize.setTodayAnalizeX(todayAnalizeX);
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
			if (isYbigger(start) || isDate) {
				if(!isDate){
					today.getDay().add(segment);
					isDate = true;
					todayAnalizeX += "\n"+(int) start.get(0) + "  " + (int) start.get(1)+ " DATE";
					todayAnalizeText +="\n"+word;
					segment = word;
					yChanged(start);
				}
				else if(yChanged(start)){
					todayAnalizeX += " "+(int) start.get(0) + "  " + (int) start.get(1)+ " D ";
					segment += " "+word;
					todayAnalizeText +=" "+word;
				}else {
					todayAnalizeText +=word;
					segment +=word;
				}
			} else if (isParagraph(start)) {
				if (font.contains("Bold") && today.getDay().size()>=1 && !today.isHasTitle() ){
					today.getDay().add(segment);
					today.setHasTitle(true);
					todayAnalizeX +="\n"+(int) start.get(0) + "  " + (int) start.get(1);
					todayAnalizeText +="\n" +word;				
					segment = word;
				} else if(font.contains("Bold") && today.getDay().size()>=1 && today.isHasTitle()){
					todayAnalizeX +=(int) start.get(0) + "  " + (int) start.get(1)+ "\n";
					todayAnalizeText += word;				
					segment += word;	
				} else{
					today.getDay().add(segment);
					todayAnalizeX +="\n"+(int) start.get(0) + "  " + (int) start.get(1);
					todayAnalizeText +="\n" +word;				
					segment = word;					
				}
			} else{
				segment += word;
				todayAnalizeText +=word;
			}
		}
	}

	private boolean isParagraph(Vector start) {
		int x = (int) start.get(0);
		int y = (int) start.get(1);
		boolean newPara = x != 104 && x != 31 && x !=25 && y!= oldy;
		oldy = (int) start.get(1);
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

	private void createDatum() {
		int length = today.getDay().size();
		today.setDatum( today.getDay().get(length-1) );
	}
	
	public String getAnalizeFont() {
		return todayAnalizeText;
	}

	public String getAnalizeX() {
		return todayAnalizeX;
	}

	public DailyText getToday() {
		return today;
	}
}
