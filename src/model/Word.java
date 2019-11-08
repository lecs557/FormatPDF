package model;

import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.text.pdf.parser.Vector;
import controller.FormatController;

public class Word {

    private String word="", word2;
    private TextRenderInfo tri;
    private FormatController form = Main.getSession().getPDFController().getStructureController().getFormatController();
    private int x,y,size;
    private String font;

    public Word(String partWord, TextRenderInfo tri){
        this.word=partWord;
        this.word2=partWord;
        this.tri=tri;
        font = tri.getFont().getPostscriptFontName();
        Vector startBase = tri.getBaseline().getStartPoint();
        Vector startAscent = tri.getAscentLine().getStartPoint();
        x = (int) startBase.get(0);
        y = (int) startBase.get(1);
        size = (int) (startAscent.get(1)-startBase.get(1));
    }

    public void addToWord(String partWord, TextRenderInfo tri) {
        word+=partWord;
        this.tri=tri;
        word2=tri.getText();
    }

    public void addToWord(String partWord) {
        word+=partWord;
    }

    public String get(){
        if (word.isEmpty()){
            return "------------------------------------";
        }
        return word +" - "+form.formatWord(font,size).name()+" - "+x+" - "+y+" - "+size;
    }

    public boolean devide() {
        if(word.endsWith("-")){
           word = word.replace("-","");
            return true;
        } else {
            return false;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public String getWord() {
        return word;
    }
}
