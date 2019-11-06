package model;

import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.text.pdf.parser.Vector;

public class Word {

    private String word="";
    private TextRenderInfo tri;

    public Word(String partWord, TextRenderInfo tri){
        this.word=partWord;
        this.tri=tri;
    }

    public void addToWord(String partWord, TextRenderInfo tri) {
        word+=partWord;
        this.tri=tri;
    }

    public void addToWord(String partWord) {
        word+=partWord;
    }

    public String getWord(){
        String font = tri.getFont().getPostscriptFontName();
        Vector startBase = tri.getBaseline().getStartPoint();
        Vector startAscent = tri.getAscentLine().getStartPoint();
        int x = (int) startBase.get(0);
        int y = (int) startBase.get(1);
        int size = (int) (startAscent.get(1)-startBase.get(1));
        return word +" - "+font+" - "+x+" - "+y+" - "+size;
    }
}
