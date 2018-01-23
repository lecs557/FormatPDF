package model;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Class is only for starting the program
 * @author Marcel
 */
public class Main extends Application {

	private static Session ses;
	
	/**
	 * executes the application by opening MainWindow.xml
	 * and creates the Class 'Session'
	 */
	@Override 
	public void start(Stage stage) throws IOException {
		ses = new Session();
		ses.initialize(stage);
		Parent root = FXMLLoader.load(getClass().getResource("/gui/MainWindow.xml"));
		Scene scene = new Scene(root);
		stage.setTitle("PDF Filter");
		stage.setScene(scene);        
		stage.show();    
		}
	
	/**
	 * executes the method start in an new
	 * Thread
	 * @param parameters
	 */
	public static void main(String[] parameters) {       
		launch(parameters);   
	}

	public static Session getSession() {
		return ses;
	}


}