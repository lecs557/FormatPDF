package model;

import com.itextpdf.text.pdf.parser.Vector;

public class Paragraph {
	
	public enum detail {title, heading, bold, paragraph};
	private String paragraph;
	private String font;
	private String position;
	private int ordDetail;
	
	
	public Paragraph(String para, String font, Vector pos, detail detail, int size) {
		this.paragraph = para;
		this.font = font+" "+size;
		this.position = (int)pos.get(0)+" "+(int)pos.get(1);
		this.ordDetail = detail.ordinal();
	}
	
	public void add (String word){
		paragraph += word;
	}
	
	public void setBold(int o){
		if(o==1)
			ordDetail = detail.bold.ordinal();
	}

	public String getParagraph() {
		return paragraph;
	}
	public int getOrdDetail() {
		return ordDetail;
	}
	public String getFont() {
		return font;
	}
	public String getPosition() {
		return position;
	}


}
