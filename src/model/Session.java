package model;

import java.io.IOException;

import controller.StructureController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.itextpdf.text.pdf.PdfReader;

import controller.AnalizeController;
import controller.PDFController;
import controller.TextFileController;

/**
 * Class contains general variables and methods
 * @author Marcel
 */
public class Session {

	public enum window {MainWindow,EvaluationWindow,AnalizeWindow};
	private Stage[] stages = new Stage[3];
	private PDFController pdfController;
	private int start, end;
	private String destinationPath, filePath;
	private PdfReader pdfReader;
	
	public Session() {}

	/**
	 * sets the first stage
	 * the other stages are null (look at "openWindow")
	 * @param stage
	 */
	public void initialize(Stage stage){
		this.stages[0] = stage;
		pdfController = new PDFController();
	}

	/**
	 * creates new stage of the window if not exists
	 * shows stored stage if exists
	 * @param window
	 * @throws IOException
	 */
	public void openWindow(window window) throws IOException {
		if (stages[window.ordinal()] == null){
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/gui/"+window.name()+".fxml"));
			Scene scene = new Scene(root);
			stage.setTitle("PDF Filter");
			stage.setScene(scene);
			if (window.ordinal()==2)
				stage.setX(280);
			if (window.ordinal()==1)
				stage.setX(820);
			stage.show();
			stages[window.ordinal()]=stage;
		} else
			stages[window.ordinal()].show();
	}
	
	public void closeWindow(window window) {
		stages[window.ordinal()].close();
	}

	public PDFController getPDFController() {
		return pdfController;
	}
	
	public Stage getStage(window window){
		return stages[window.ordinal()];
	}

	public PdfReader getPdfReader() {
		return pdfReader;
	}

	public void setPdfReader(PdfReader pdfReader) {
		this.pdfReader = pdfReader;
	}

	public String getDestination() {
		return destinationPath;
	}

	public void setDestination(String destination) {
		this.destinationPath = destination;
	}

	public void refreshStages() {
		this.stages[1] = null;
		this.stages[2] = null;
	}
}
