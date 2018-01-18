package windowCtl;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import model.Main;
import model.Session;
import model.Session.window;
import controller.AnalizeController;
import controller.TextFileController;
	

public class EvaluationWindowController {
	
	private Session sess;
	private TextFileController tfc;
	private AnalizeController analize;
	@FXML
	private TextField sum;
	@FXML 
	private HBox hb;
	@FXML 
	private TextArea crntTA	;
	
	public void initialize(){
		sess = Main.getSession();
		tfc = Main.getSession().getTextFileController();
		analize = Main.getSession().getAnalizeController();
		
		sum.setText("Gesamt: "+ tfc.getDays()+" Tage(+"+tfc.getErrorCounter()+" Fehler)");
		
		for(ArrayList<String> aos : analize.getAmountOfSegments()){
			createTA();
			for(String aoms: aos){
				editTA(aoms+"\n");			
			}
		}		
	}
	
	
	@FXML
	private void onPressClose(){
		sess.closeWindow(window.EvaluationWindow);
	}
	
	private void createTA(){
		crntTA = new TextArea();
		crntTA.setEditable(false);
		hb.getChildren().add(crntTA);
	}
	
	private void editTA(String text){
		crntTA.setText(crntTA.getText()+text);
	}

}
