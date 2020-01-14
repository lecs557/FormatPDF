package windowCtl;


import controller.StructureController;
import javafx.event.ActionEvent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
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


	public void initialize() {
        for(Chapter c:struc.getHeft()){
        	String title="";
            TextArea words = new TextArea();
        	for (Word t:c.getTitleList()){
        		title+=t.getString()+" ";
        		words.appendText(t.getDetialString()+"\n");
			}
        	Tab tab = new Tab(title);
        	tabPane.getTabs().add(tab);


            HBox vbox = new HBox();
        	TextArea html = new TextArea();
        	html.setPrefWidth(250);

            words.setLayoutX(260);
            words.setPrefWidth(300);
            vbox.getChildren().addAll(html,words);

        	for(Paragraph p:c.getParagraphList()) {
				html.appendText(struc.getHtmlController().convertToHTML(p)+"\n");
				for(Word w:p.getWordList()){
				    words.appendText(w.getDetialString()+"\n");
                }
			}
        	tab.setContent(vbox);

		}

	}

	@FXML
	private void onPressClose(ActionEvent event){
		Main.getSession().closeWindow(window.ResultWindow);
	}

}
