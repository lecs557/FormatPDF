package controller;

import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.text.pdf.parser.Vector;
import com.sun.xml.internal.bind.v2.TODO;
import model.Chapter;
import model.Paragraph;
import model.Word;

import java.util.ArrayList;

public class StructureController {
    private FormatController formatController = new FormatController();

    private ArrayList<Chapter> book = new ArrayList<>();
    private Chapter currentChapter;
    private Paragraph currentParagraph;
    private Word currentWord;

    private String words=""; //TODO string for every page
    private String paragaphs="";

    private boolean wordCompleted = false;

    // oldies
    private Word oldWord;
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
                     if (currentWord!=null) { // first word, second line
                         if (!currentWord.devide()) {
                             toParagraph();
                             words += "\n" + currentWord.get();
                             System.out.println(currentWord.get());
                             currentWord = new Word(st_partWord, rinfo);
                             wordCompleted = true;
                         }
                         else {
                             currentWord.addToWord(st_part);
                             wordCompleted = false;
                         }
                     }else { // first word, first line
                         currentWord = new Word(st_partWord, rinfo);
                         wordCompleted = false;
                     }
                 }
             }
             else if( y == oldY)
             {  // the same line
                if (st_part.endsWith(" ")) {
                    currentWord.addToWord(st_partWord);
                    toParagraph();
                    words += "\n" + currentWord.get();
                    currentWord = new Word("", rinfo);
                    wordCompleted = true;
                 }else if(!st_part.contains(" ")) {
                     if (oldX <= x) { // correct
                         currentWord.addToWord(st_part,rinfo);
                         System.out.println(currentWord.get());
                         wordCompleted = false;
                     } else { // incorrect
                         toParagraph();
                         words+="\n"+currentWord.get();
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

    private void toParagraph() {
        if(currentParagraph==null) {
            currentParagraph = new Paragraph(currentWord);
        } else if(oldWord!=null){
            if(newParagraph()) {
                paragaphs+="\n"+currentParagraph.get();
                currentParagraph = new Paragraph(currentWord);
            } else {
                currentParagraph.add(currentWord);
            }
        }
        oldWord = currentWord;
    }


    private boolean newParagraph() {
        if ((oldWord.getY() - currentWord.getY()) > 15) {
            return true;
        }
        else {
            return false;
        }
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

    public FormatController getFormatController() {
        return formatController;
    }

    public String getParagaphs() {
        paragaphs+="\n"+currentParagraph.get();
        return paragaphs;
    }
}
