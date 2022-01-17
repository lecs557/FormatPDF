package model;

import controller.FormatController;

public class Word {

    private String word="";
    private FormatController form = Main.getSession().getPDFController().getStructureController().getFormatController();
    private int x,y,size;
    private FormatController.format font;

/*
    public Word(String partWord, TextRenderInfo tri){
        this.word=partWord;
        Vector startBase = tri.getBaseline().getStartPoint();
        Vector startAscent = tri.getAscentLine().getStartPoint();
        x = (int) startBase.get(0);
        y = (int) startBase.get(1);
        size = (int) (startAscent.get(1)-startBase.get(1));
        font = form.formatWord(tri.getFont().getPostscriptFontName(), size, word.length()>2);
    }

    public void addToWord(String partWord, TextRenderInfo tri) {
        word+=partWord;
    }

*/
    public void addToWord(String partWord) {
        word+=partWord;
    }

    public String getDetialString(){
        return word +" - "+font.name()+" - "+x+" - "+y+" - "+size;
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

   public String getString() {
        return word;
    }

    public FormatController.format getFont() {
        return font;
    }
}
