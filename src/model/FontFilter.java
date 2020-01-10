package model;

import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

public class FontFilter extends RenderFilter {
		
	
	public FontFilter(){
		
	}
	
	@Override
	public boolean allowText(TextRenderInfo tri) {
		
		Main.getSession().getPDFController().getStructureController().makeword(tri.getText(),tri);
		
		return true;
		
		
	}
}
