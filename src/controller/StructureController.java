package controller;

import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.text.pdf.parser.Vector;
import model.Chapter;
import model.Paragraph;
import model.Word;

import java.awt.print.Book;
import java.util.ArrayList;

public class StructureController {
    private FormatController formatController = new FormatController();

    private ArrayList<Chapter> fmnHeft = new ArrayList<>();

    private Chapter currentChapter;
    private Paragraph currentParagraph;
    private Word currentWord;

    private String words=""; //TODO string for every page
    private String paragaphs="";

    // oldies
    private Word lastCompletedWord;
    private int oldY;

    // current letter
    int x, y, size;


    /**
     * Gets TextRenderInfo and puts it into words -> paragraphs -> chapter -> book
     * @param rinfo got from "Fontfilter", can contain a random number of charakters
     */
    public void makeword(String st_part, TextRenderInfo rinfo) {
        String st_partWord = st_part;
        String font = rinfo.getFont().getPostscriptFontName();
        Vector startBase = rinfo.getBaseline().getStartPoint();
        Vector startAscent = rinfo.getAscentLine().getStartPoint();
         x = (int) startBase.get(0);
         y = (int) startBase.get(1);
        size = (int) (startAscent.get(1)-startBase.get(1));

        //word is in the range which should be noticed
        if (y < 560 && y > 52) {

             // takes first part which contains no space
             if (st_part.contains(" ") && !st_part.endsWith(" "))
             {
                 String old = st_part.split(" ",2)[0];
                 String nw = st_part.split(" ",2)[1];
                 makeword(old+" ", rinfo);
                 makeword(nw, rinfo);
             }
             // word starts new line
             if (y != oldY)
             {
                 if (0>oldY - y && oldY - y > -10) {
                     currentWord.addToWord("<hoch>"+st_part+"</hoch>");
                 } else {
                     if (currentWord!=null) {
                         if (!currentWord.devide()) {
                             lastCompletedWord = currentWord;
                             currentWord = new Word(st_partWord, rinfo);
                             processWord();
                         }
                         else {
                             currentWord.addToWord(st_part);
                         }
                     }else {
                         currentWord = new Word(st_partWord, rinfo);
                         currentParagraph = new Paragraph(currentWord);
                         currentChapter = new Chapter(currentParagraph);
                         fmnHeft.add(currentChapter);
                 }
                 }
             }
             // in the same line
             else if( y == oldY)
             {
                if (st_part.endsWith(" ")) {
                    currentWord.addToWord(st_partWord);
                    lastCompletedWord = currentWord;
                    currentWord = new Word("", rinfo);
                    processWord();
                 }else if(!st_part.contains(" ")) {
                     currentWord.addToWord(st_part,rinfo);
                 }
             }
         }
        oldY = y;
    }

    private void processWord(){
        if (false) {
            currentParagraph = new Paragraph(currentWord);
            currentChapter = new Chapter(currentParagraph);
            fmnHeft.add(currentChapter);
        }
        else if(lastCompletedWord.getY() - y > 15){
            currentParagraph = new Paragraph(currentWord);
            currentChapter.add(currentParagraph);
        } else {
            currentParagraph.add(currentWord);
        }


    }

    private void startingNewLine() {
        if((oldY - currentWord.getY()) > 15) {
            currentParagraph = new Paragraph(currentWord);
            currentChapter.add(currentParagraph);
        } else if ( lastCompletedWord.getFont().ordinal() != currentWord.getFont().ordinal()){
            currentParagraph = new Paragraph(currentWord);
            currentChapter.add(currentParagraph);
        }
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

    public ArrayList<Chapter> getHeft() {
        return fmnHeft;
    }
}
