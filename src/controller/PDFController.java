package controller;

import java.io.IOException;
import java.util.ArrayList;

import model.Chapter;
import model.FontFilter;
import model.Main;
import model.Paragraph;
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
	
	private ArrayList<Chapter> chapter = new ArrayList<Chapter>();
	private Chapter currentChapter;
	private String todayAnalizeText ="";
	private String todayAnalizeX ="";
	private String todayAnalizeFont ="";
	private ArrayList<String> segment;
	
	private String oldFormat = "";
	
	private Paragraph currentParagraph;
	private boolean isTitle = false;
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
		@SuppressWarnings("unused") // <<FobtFilter>> is invoked here
		String content = PdfTextExtractor.getTextFromPage(reader, page,
				strategy);
	}

	/**
	 * Tries to find out the different segments
	 * and puts the into the daily-Object. 
	 * @param rinfo
	 */
	public void createText(TextRenderInfo rinfo) {
		String word = rinfo.getText().replace("-", "");
		String font = rinfo.getFont().getPostscriptFontName();
		Vector startBase = rinfo.getBaseline().getStartPoint();
		Vector startAscent = rinfo.getAscentLine().getStartPoint();
		int size = (int) (startAscent.get(1)-startBase.get(1));
		
		if (!word.equals("") && (int)startBase.get(1)!=43 ) {
			
			if (!oldFormat.equals(font) && yChanged(startBase) ){
				
				if(font.contains("Bold")){
					currentChapter = new Chapter();
					chapter.add(currentChapter);
					paragraph(word, font, startBase, size);
					
				} else {
					paragraph(word, font, startBase, size);
				}
				oldFormat = font;
			}
			else if (isParagraph(startBase, size)) {
				paragraph(word, font, startBase, size);
			} else{
				currentParagraph.add(word);
			}
		}
	}
	

	private boolean isParagraph(Vector start, int size) {
		int x = (int) start.get(0);
		int y = (int) start.get(1);
		boolean newPara = (y-oldy!=0 && 3 < x - oldX  &&  x - oldX < 18 ) || oldy - y > size*2;
		if(y!= oldy) {		
			oldX = x;
		}
		oldy = y;
		return newPara;
	}
	
	private boolean yChanged(Vector start){
		int y = (int) start.get(1);
		boolean changed = y!= oldy;
		oldy = y;
		return changed;
	}

	private boolean isYbigger(Vector start) {
		boolean bigger = (int) start.get(1) > oldy;
		return bigger;
	}


	public ArrayList<Chapter> getChapter() {
		return chapter;
	}
	
	private void paragraph(String word, String font, Vector start, int size){
		currentParagraph = new Paragraph(word);
		currentChapter.getParagraphs().add(currentParagraph);
		currentChapter.getFonts().add(font+" "+size);
		currentChapter.getPositions().add((int) start.get(0)+"  "+(int) start.get(1));
		
	}
}
