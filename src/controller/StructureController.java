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
    private ArrayList<Paragraph> chapter = new ArrayList<>();
    private ArrayList<Word> paragraph = new ArrayList<>();

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


    /**
     * Gets TextRenderInfo and puts it into words -> paragraphs -> chapter -> book
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

        //word is in the range which should be noticed
        if (y < 560 && y > 52) {

             // takes first part which contains no space
             if (st_part.contains(" ") && !st_part.endsWith(" "))
             {
                 String old = st_part.split(" ",2)[0];
                 String nw = st_part.split(" ",2)[1];
                 structureText(old+" ", rinfo);
                 structureText(nw, rinfo);
             }
             // word starts new line
             if (y != oldY)
             {
                 if (0>oldY - y && oldY - y > -10) {
                     currentWord.addToWord("<hoch>"+st_part+"</hoch>");
                 } else {
                     if (currentWord!=null) {
                         if (!currentWord.devide()) {
                             startingNewLine();
                             words += "\n" + currentWord.get();
                             currentWord = new Word(st_partWord, rinfo);
                             wordCompleted = true;
                         }
                         else {
                             currentWord.addToWord(st_part);
                             wordCompleted = false;
                         }
                     }else {
                         currentWord = new Word(st_partWord, rinfo);
                         currentParagraph = new Paragraph(currentWord);
                         currentChapter = new Chapter(currentParagraph);
                         wordCompleted = false;
                     }
                 }
             }
             // in the same line
             else if( y == oldY)
             {
                if (st_part.endsWith(" ")) {
                    currentWord.addToWord(st_partWord);
                    currentParagraph.add(currentWord);
                    words += "\n" + currentWord.get();
                    currentWord = new Word("", rinfo);
                    wordCompleted = true;
                 }else if(!st_part.contains(" ")) {
                     if (oldX <= x) { // correct
                         currentWord.addToWord(st_part,rinfo);
                         wordCompleted = false;
                     } else { // incorrect
                         currentParagraph.add(currentWord);
                         words+="\nFEHLER"+currentWord.get();
                         currentWord = new Word(st_part,rinfo);
                         wordCompleted = false;
                     }
                 }
             }
         }
        oldWord = currentWord;
        oldFont = font;
        oldX = x;
        oldY = y;
        oldSize = size;
    }

    private void startingNewLine() {
        if((oldY - currentWord.getY()) > 15) {
            currentParagraph = new Paragraph(currentWord);
            currentChapter.add(currentParagraph);
        } else if ( oldWord.getFont().ordinal() != currentWord.getFont().ordinal()){
            currentParagraph = new Paragraph(currentWord);
            currentChapter.add(currentParagraph);
        }
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

    public FormatController getFormatController() {
        return formatController;
    }

    public String getParagaphs() {
        paragaphs+="\n"+currentParagraph.get();
        return paragaphs;
    }

    public String getWords() {
        return words;
    }

    public Chapter getChapter() {
        return currentChapter;
    }
}
