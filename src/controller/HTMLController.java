package controller;

import model.Paragraph;
import model.Word;

public class HTMLController {

    public String convertToHTML(Paragraph para){
        String html="";
        switch (para.getFont().ordinal()){

            case 3:html+="<h3>";
                for(Word w:para.getWordList()){
                    html+=w.getString()+" ";
                }
                html+="</h3>"; break;

            case 4:html+="<i>";
                for(Word w:para.getWordList()){
                    html+=w.getString()+" ";
                }
                html+="</i>"; break;

            case 5:html+="<p>";
                for(Word w:para.getWordList()){
                    switch (w.getFont().ordinal()) {
                        case 3:html+="<b>"+w.getString()+"</b>";break;
                        case 4:html+="<i>"+w.getString()+"</i>";break;
                        case 5:html+=w.getString()+" ";break;
                        }
                }

        }
        return html;
    }
}
