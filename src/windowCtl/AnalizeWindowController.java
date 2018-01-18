package windowCtl;


import controller.AnalizeController;
import model.Main;
import model.Session.window;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class AnalizeWindowController {
	AnalizeController analize = Main.getSession().getAnalizeController();
	@FXML
	private TextArea analizeText;
	@FXML
	private TextArea analizeX;
	@FXML
	private TextArea analizeFont;
	
	public void initialize(){
		
		for (String font:analize.getAnalizeFont()){
			analizeFont.setText(analizeFont.getText()+font);
		}
		for (String x:analize.getAnalizeX()){
			analizeX.setText(analizeX.getText()+x);
		}
		for (String text:analize.getAnalizeText()){
			analizeText.setText(analizeText.getText()+text);
		}
		analizeText.setEditable(false);
		analizeX.setEditable(false);
		analizeFont.setEditable(false);
	}
	
	@FXML
	private void onPressClose(){
		Main.getSession().closeWindow(window.AnalizeWindow);
	}

}
