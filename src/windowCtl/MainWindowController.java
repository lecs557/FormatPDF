package windowCtl;

import java.io.File;
import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import model.Main;
import model.Session;
import model.Session.window;

import com.itextpdf.text.pdf.PdfReader;

import controller.AnalizeController;
import controller.PDFController;
import controller.TextFileController;

public class MainWindowController {
	private Session session = Main.getSession();;
	private PDFController pdfctrl = session.getPDFController();

	
	@FXML
	private TextField tf_absolutePath, tf_pages, tf_pathDes ;
	@FXML
	private Button okBtn, analizeBtn, browseDesBtn;
	@FXML
	private ProgressBar bar;
	@FXML
	private TextField tf_startPage, tf_endPage;
		
	private PdfReader reader;
	private int pages;
	private int start;
	private int i=start;
	private int end;
	
	@FXML
	/**
	 * reads data from "mainWindow" and stores it in "Session"
	 * reads pdf-file in second thread
	 * sets progressBar's progress in third thread
	 */
	private void onPressOk() {
		start = Integer.parseInt(tf_startPage.getText());
		end = Integer.parseInt(tf_endPage.getText());
		session.refreshStages();
		okBtn.setDisable(true);
		readDataSendtoSession();
		new Thread(() -> {
			try {
				new Thread(() -> {
					while(i<=end){
						bar.setProgress((i-start)/(float) (end-start));
					}
				} ).start();
				for (i = start; i <= end; i++) {
					int page = i;
					pdfctrl.readPDF(page);
				}
				Platform.runLater(new Thread(()-> {
                    try {
                        analizeBtn.setDisable(false);
                        okBtn.setDisable(false);
                        session.openWindow(window.AnalizeWindow);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }) ) ;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
	}
	
	@FXML
	private void onPressAnalize() throws IOException{
		session.openWindow(window.AnalizeWindow);		
	}

	@FXML
	private void onPressBrowse() throws IOException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File file = fileChooser.showOpenDialog(Main.getSession()
				.getStage(window.MainWindow));
		tf_absolutePath.setText(file == null ? "" : file.getAbsolutePath());
		if (tf_absolutePath != null) {
			okBtn.setDisable(false);
			tf_startPage.setDisable(false);
			tf_endPage.setDisable(false);
			reader = new PdfReader(tf_absolutePath.getText());
			pages = reader.getNumberOfPages();	
			tf_pages.setText(""+pages);
			tf_pathDes.setText("C:\\Users\\User\\Desktop\\Russisch\\");
			browseDesBtn.setDisable(false);
			bar.setProgress(0);
		} else{
			tf_startPage.setDisable(true);
			tf_endPage.setDisable(true);
			okBtn.setDisable(true);
			browseDesBtn.setDisable(true);
			tf_pathDes.setText("");
		}
	}

	@FXML
	private void onPressBrowseDes() throws IOException {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Open Resource File");
		File file = directoryChooser.showDialog(Main.getSession()
				.getStage(window.MainWindow));
		tf_pathDes.setText(file == null ? "" : file.getAbsolutePath());
		if (tf_pathDes == null) {
			tf_pathDes.setText("C:\\Users\\User\\Desktop\\Russisch\\");
		}
	}
	
	@FXML
	private void onPressClose(){
		System.exit(0);
	}	
	
	@FXML
	private void validatePages(){
		try{
			if( Integer.parseInt(tf_startPage.getText()) > Integer.parseInt(tf_endPage.getText()) )
				okBtn.setDisable(true);
			else if(Integer.parseInt(tf_endPage.getText()) > pages || Integer.parseInt(tf_startPage.getText())==0)
				okBtn.setDisable(true);
			else
				okBtn.setDisable(false);	
		}catch(Exception e){
			okBtn.setDisable(true);
		}
	}
	
	
	private void readDataSendtoSession(){
		session.setDestination(tf_pathDes.getText());
		session.setPdfReader(reader);
	}
}
