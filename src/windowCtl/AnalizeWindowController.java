package windowCtl;


import controller.AnalizeController;
import controller.StructureController;
import javafx.event.ActionEvent;
import model.Chapter;
import model.Main;
import model.Paragraph;
import model.Session.window;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import model.Word;

public class AnalizeWindowController {
	StructureController struc = Main.getSession().getPDFController().getStructureController();
	@FXML
	private TextArea analizeWords, analizePara;
	
	public void initialize() {
        for(Chapter c:struc.getHeft()){
        	analizePara.appendText("TITLE: ");
        	for(Word t:c.getTitle()){
				analizePara.appendText(t.getString()+" ");
				analizeWords.appendText(t.get()+"\n");
			}

			analizePara.appendText("\n");
			analizeWords.appendText("\nNEUER PARAGRAPH\n");

        	for(Paragraph p:c.getParagraphs()){
        		if(p.getFont()!=null)
					analizePara.appendText(p.getFont().name()+": ");
				for (Word w:p.getParagraph()){
					analizePara.appendText(w.getString()+" ");
					analizeWords.appendText(w.get()+"\n");
				}
				analizeWords.appendText("\nNEUER PARAGRAPH\n");
				analizePara.appendText("\n");
        }


		}

	}

	@FXML
	private void onPressClose(ActionEvent event){
		Main.getSession().closeWindow(window.AnalizeWindow);
	}

}
