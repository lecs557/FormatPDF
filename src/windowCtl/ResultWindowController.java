package windowCtl;


import controller.StructureController;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import model.Chapter;
import model.Main;
import model.Paragraph;
import model.Session.window;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import model.Word;

public class ResultWindowController {
	StructureController struc = Main.getSession().getPDFController().getStructureController();
	@FXML
	private TabPane tabPane;
	@FXML
	private Tab startTab;


	public void initialize() {
        for(Chapter c:struc.getHeft()){
        	String title="";
        	for (Word t:c.getTitleList()){
        		title+=t.getString()+" ";
			}
        	Tab tab = new Tab(title);
        	tabPane.getTabs().add(tab);

        	TextArea html = new TextArea();

        	for(Paragraph p:c.getParagraphList()) {
				html.appendText(struc.getHtmlController().convertToHTML(p)+"\n");
			}
        	tab.setContent(html);


		}

	}

	@FXML
	private void onPressClose(ActionEvent event){
		Main.getSession().closeWindow(window.ResultWindow);
	}

}
