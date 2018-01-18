package model;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Class executes the application by opening the MainWindow
 * and starts the Class 'Session'
 * @author Marcel
 */
public class Main extends Application {

	private static Session ses;

	@Override 
	public void start(Stage stage) throws IOException {
		ses = new Session();
		ses.initialize(stage);
		Parent root = FXMLLoader.load(getClass().getResource("/gui/MainWindow.xml"));
//		Parent root = FXMLLoader.load(getClass().getResource("/gui/EvaluationWindow.xml")); 
		Scene scene = new Scene(root);
		stage.setTitle("PDF Filter");
		stage.setScene(scene);        
		stage.show();    
		}

	public static void main(String[] parameters) {       
		launch(parameters);   
	}

	public static Session getSession() {
		return ses;
	}


}