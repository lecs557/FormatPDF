package windowCtl;


import controller.AnalizeController;
import model.Main;
import model.Session.window;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class AnalizeWindowController {
	AnalizeController analize = Main.getSession().getAnalizeController();
	@FXML
	private TextArea analizeFont;
	@FXML
	private TextArea analizeX;
	
	public void initialize(){
		
		for (String text:analize.getAnalizeText()){
			analizeFont.setText(analizeFont.getText()+text);
		}
		for (String x:analize.getAnalizeX()){
			analizeX.setText(analizeX.getText()+x);
		}
		analizeFont.setEditable(false);
		analizeX.setEditable(false);
	}
	
	@FXML
	private void onPressClose(){
		Main.getSession().closeWindow(window.AnalizeWindow);
	}

}
