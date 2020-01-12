package controller;

import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.text.pdf.parser.Vector;
import model.Chapter;
import model.Paragraph;
import model.Word;

import java.util.ArrayList;

public class StructureController {
    private FormatController formatController = new FormatController();
    private HTMLController htmlController= new HTMLController();

    private ArrayList<Chapter> fmnHeft;

    private Chapter currentChapter;
    private Paragraph currentParagraph;
    private Word currentWord;

    // oldies
    private Word wordToProcess;
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
                             wordToProcess = currentWord;
                             currentWord = new Word(st_partWord, rinfo);
                             processWord();
                         }
                         else {
                             currentWord.addToWord(st_part);
                         }
                     }else {
                         currentWord = new Word(st_partWord, rinfo);
                    }
                 }
             }
             // in the same line
             else if( y == oldY)
             {
                if (st_part.endsWith(" ")) {
                    currentWord.addToWord(st_partWord.trim());
                    wordToProcess = currentWord;
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
        if(!wordToProcess.getString().isEmpty()){

            if (wordToProcess.getFont().ordinal() ==  1) {
                if(!currentChapter.getParagraphList().isEmpty()){
                    currentParagraph=null;
                    currentChapter = new Chapter();
                    currentChapter.addToTitle(wordToProcess);
                    fmnHeft.add(currentChapter);
                }else {
                    currentChapter.addToTitle(wordToProcess);
                }
            }
            else if(wordToProcess.getY() - y <-15 || wordToProcess.getY() - y > 15 || currentParagraph==null){
                if(currentParagraph!=null){
                    currentParagraph.add(wordToProcess);
                    currentParagraph = null;
                } else{
                    currentParagraph = new Paragraph(wordToProcess);
                    currentChapter.add(currentParagraph);
                }
            }
            else if(currentParagraph.getFont().ordinal()!=5 && currentParagraph.getFont()!=wordToProcess.getFont()){
                currentParagraph = new Paragraph(wordToProcess);
                currentChapter.add(currentParagraph);
            }
            else {
                currentParagraph.add(wordToProcess);
            }
        }


    }

    public FormatController getFormatController() {
        return formatController;
    }

    public ArrayList<Chapter> getHeft() {
        return fmnHeft;
    }

    public HTMLController getHtmlController() {
        return htmlController;
    }

    public void setCurrents(ArrayList<Chapter> heft){
        if (heft==null){
            fmnHeft = new ArrayList<Chapter>();
            currentChapter = new Chapter();
            fmnHeft.add(currentChapter);
        }

    }
}
