package controller;

import com.itextpdf.text.pdf.parser.*;
import model.FontFilter;
import model.Main;
import model.Session;

import java.io.IOException;

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
		structureController.setCurrents(structureController.getHeft());
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
