package controller;

import java.io.IOException;
import java.util.ArrayList;

import model.Chapter;
import model.FontFilter;
import model.Main;
import model.Paragraph;
import model.Paragraph.detail;
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
	private PdfReader reader = session.getPdfReader();
	
	private ArrayList<Chapter> chapter = new ArrayList<Chapter>();
	private Chapter currentChapter;
	
	private String oldFont = "";
	private int oldSize = 0;
	private int oldy = 500;
	private int oldX = 0;
	
	private Paragraph currentParagraph;
	
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
		int y = (int) startBase.get(1);
		int size = (int) (startAscent.get(1)-startBase.get(1));
		
		if (!word.equals("") && y!=43 ) { //y=43 : Seitenzahl
			
			if (!sameFont(font, size) && newLine(y)){
				
				if(font.contains("Bold")){
					
					if(size>10){
						currentChapter = new Chapter();
						chapter.add(currentChapter);
						startParagraph(word, font, startBase, size, detail.title);						
					} else{
						startParagraph(word, font, startBase, size, detail.heading);					
					} 				
				}else{
					startParagraph(word, font, startBase, size, detail.paragraph);					
				}
			}
			else if(!sameFont(font, size) && !newLine(y)){
				currentParagraph.setBold(currentParagraph.getOrdDetail());
				startParagraph(word, font, startBase, size, detail.paragraph);
			}
			else if (!belongsToCurrentParagraph(startBase, size)) {
				startParagraph(word, font, startBase, size, detail.paragraph);
			} else{
				currentParagraph.add(word);
			}
		}
		oldFont=font;
		oldy = y;		
		oldSize = size;
	}
	

	private boolean belongsToCurrentParagraph(Vector start, int size) {
		int x = (int) start.get(0);
		int y = (int) start.get(1);
		boolean btcp = y - oldy == 0 || -3 < x - oldX  &&  x - oldX < 10 && oldy - y < size*2;
		if(newLine(y)) {		
			oldX = x;
		}
		return btcp;
	}
	
	private boolean sameFont(String font, int size){
		return font.equals(oldFont) && size == oldSize;
	}

	private boolean newLine(int y){
		return y != oldy;
	}

	private void startParagraph(String word, String font, Vector start, int size, detail detail){
		currentParagraph = new Paragraph(word, font, start, detail, size);
		currentChapter.getParagraphs().add(currentParagraph);
		
	}
	public ArrayList<Chapter> getChapter() {
		return chapter;
	}
}
