package model;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Class starts the "PDFFilter"
 * @author Marcel
 */
public class Main extends Application {

	private static Session ses;
	
	/**
	 * executes "PDFFilter" by opening "MainWindow.fxml"
	 * and creates the Class "Session"
	 */
	@Override 
	public void start(Stage stage) throws IOException {
		ses = new Session();
		ses.initialize(stage);
		Parent root = FXMLLoader.load(getClass().getResource("/gui/MainWindow.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("PDF Filter");
		stage.setScene(scene);        
		stage.show();    
	}
	
	/**
	 * executes the method start in an new
	 * Applicationthread
	 * @param parameters
	 */
	public static void main(String[] parameters) {
		launch(parameters);   
	}

	public static Session getSession() {
		return ses;
	}


}