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
	private StructureController structureController = new StructureController();

	/**
	 * Scans page of the PDF-File invoking "FontFilter" to get
	 * the TextRenderInfo which are given to the "FormatController"
	 * Can be invoked only for one page a time 
	 * @param page of the PDF-File
	 */
	public void readPDF(int page) throws IOException {
		RenderFilter info = new FontFilter();
		TextExtractionStrategy strategy = new FilteredTextRenderListener(
				new LocationTextExtractionStrategy(), info);
		@SuppressWarnings("unused") // <<FobtFilter>> is invoked here
		String content = PdfTextExtractor.getTextFromPage(session.getPdfReader(), page,
				strategy);
	}

	public StructureController getStructureController() {
		return structureController;
	}

}
