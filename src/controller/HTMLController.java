package controller;

import model.Paragraph;
import model.Word;

public class HTMLController {

    public String convertToHTML(Paragraph para){
        String html="";
        switch (para.getFont().ordinal()){

            case 0:html+="\n\n\n";
                for(Word w:para.getWordList()){
                    html+=w.getString()+" ";
                }
                html+=""; break;

            case 3:html+="<h3>";
                for(Word w:para.getWordList()){
                    html+=w.getString()+" ";
                }
                html+="</h3>"; break;

            case 4:html+="<em>";
                for(Word w:para.getWordList()){
                    html+=w.getString()+" ";
                }
                html+="</em>"; break;

            case 5:html+="<p>";
                for(Word w:para.getWordList()){
                    switch (w.getFont().ordinal()) {
                        case 3:html+="<strong>"+w.getString()+" </strong>";break;
                        case 4:html+="<em>"+w.getString()+" </em>";break;
                        case 5:html+=w.getString()+" ";break;
                        }
                }
                html+="</p>";

        }
        return html;
    }
}
