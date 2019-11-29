package windowCtl;


import controller.AnalizeController;
import controller.StructureController;
import javafx.event.ActionEvent;
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
        analizePara.setText(struc.getParagaphs());

        for(Paragraph p:struc.getChapter().getParagraphs()){
        	analizeWords.appendText("\n");
        	for (Word w:p.getParagraph()){
				analizeWords.appendText(w.get()+"\n");
			}
		}

	}

	@FXML
	private void onPressClose(ActionEvent event){
		Main.getSession().closeWindow(window.AnalizeWindow);
	}

}
