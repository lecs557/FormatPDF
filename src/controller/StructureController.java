package controller;

import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.text.pdf.parser.Vector;
import model.Chapter;
import model.Paragraph;
import model.Word;

import java.util.ArrayList;

public class StructureController {
    private AnalizeController analizeController = new AnalizeController();
    private FormatController formatController = new FormatController();

    public AnalizeController getAnalizeController() {
        return analizeController;
    }

    public FormatController getFormatController() {
        return formatController;
    }

    private ArrayList<Chapter> book = new ArrayList<>();
    private Chapter currentChapter;
    private Paragraph currentParagraph;
    private Word currentWord;

    private String words="";

    private boolean wordCompleted = false;

    // oldies
    private String oldFont;
    private int oldX;
    private int oldY;
    private int oldSize;

    public String getWords() {
        return words;
    }

    /**
     * Gets TextRenderInfo and puts it into words
     * @param rinfo got from "Fontfilter", can contain a random number of charakters
     */
    public void structureText(String st_part, TextRenderInfo rinfo) {
        String st_partWord = st_part;
        String font = rinfo.getFont().getPostscriptFontName();
        Vector startBase = rinfo.getBaseline().getStartPoint();
        Vector startAscent = rinfo.getAscentLine().getStartPoint();
        int x = (int) startBase.get(0);
        int y = (int) startBase.get(1);
        int size = (int) (startAscent.get(1)-startBase.get(1));

        if (y < 570 && y > 52) {        // word should be noticed

             if (st_part.contains(" ") && !st_part.endsWith(" "))
             {  // more than one word
                 String old = st_part.split(" ",2)[0];
                 String nw = st_part.split(" ",2)[1];
                 structureText(old+" ", rinfo);
                 structureText(nw, rinfo);
             }
             if (y != oldY)
             {  // new line or number(footnote)
                 if (0>oldY - y && oldY - y > -10) {
                     currentWord.addToWord("<hoch>"+st_part+"</hoch>");
                 } else {
                     if (currentWord!=null)
                         words+="\n"+currentWord.getWord();
                     currentWord = new Word(st_partWord,rinfo);
                 }
                 wordCompleted = false;
             }
             else if( y == oldY)
             {  // the same line
                 if (wordCompleted) {
                     words+="\n"+currentWord.getWord();
                     currentWord = new Word(st_part,rinfo);
                     if(st_part.endsWith(" "))
                         wordCompleted =true;
                     else
                         wordCompleted = false;
                 }
                 else if (st_part.endsWith(" ")) {
                     currentWord.addToWord(st_part);
                     wordCompleted = true;

                 }else if(!st_part.contains(" ")) {
                     if (oldX <= x) { // correct
                         currentWord.addToWord(st_part,rinfo);
                         wordCompleted = false;
                     } else { // incorrect
                         words+="\n"+currentWord.getWord();
                         currentWord = new Word(st_part,rinfo);
                         wordCompleted = false;
                     }
                 }
             }
         }
        oldFont = font;
        oldX = x;
        oldY = y;
        oldSize = size;
    }


    private boolean belongsToCurrentParagraph(Vector start, int size) {
        int x = (int) start.get(0);
        int y = (int) start.get(1);
        boolean btcp = y - oldY == 0 || -3 < x - oldX  &&  x - oldX < 10 && oldY - y < size*2;
        if(newLine(y)) {
            oldX = x;
        }
        return btcp;
    }

    private boolean sameFont(String font, int size){
        return font.equals(oldFont) && size == oldSize;
    }

    private boolean newLine(int y){
        return y != oldY;
    }

    private void startChapter () {
        currentChapter = new Chapter();
        book.add(currentChapter);
    }

    private void startParagraph(Word word, String font, Vector start, int size, Paragraph.detail detail){
        currentParagraph = new Paragraph(word, font, start, detail, size);
        if (book.size()<=0){
            currentChapter = new Chapter();
            book.add(currentChapter);
        }
        currentChapter.getParagraphs().add(currentParagraph);
        currentWord = word;
    }

    private void startWord(String st_part, TextRenderInfo rinfo) {
        currentWord = new Word(st_part,rinfo);
    }
}
