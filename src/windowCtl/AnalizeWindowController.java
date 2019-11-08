package windowCtl;


import controller.AnalizeController;
import controller.StructureController;
import javafx.event.ActionEvent;
import model.Main;
import model.Session.window;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class AnalizeWindowController {
	StructureController struc = Main.getSession().getPDFController().getStructureController();
	@FXML
	private TextArea analizeWords, analizePara;
	
	public void initialize() {
		analizeWords.setText(struc.getWords()); //TODO tab for every string
        analizePara.setText(struc.getParagaphs());

	}

	@FXML
	private void onPressClose(ActionEvent event){
		Main.getSession().closeWindow(window.AnalizeWindow);
	}

}
