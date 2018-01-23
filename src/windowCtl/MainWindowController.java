package windowCtl;

import java.io.File;
import java.io.IOException;

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
	private TextFileController tfctrl;
	private PDFController pdfctrl;
	private AnalizeController analizectrl;
	
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
	private void onPressOk() {
		new Thread(){
			public void run(){
				try {
					start = Integer.parseInt(tf_startPage.getText());
					end = Integer.parseInt(tf_endPage.getText());
					okBtn.setDisable(true);
					setVariables();
					new Thread(){
						public void run(){		
							while(i<=end){		
								bar.setProgress((i-start)/(float) (end-start));
							}
						}
					}.start();;
					for (i = start; i <= end; i++) {
						int page = i;
						pdfctrl.readPDF(page);
						tfctrl.writeDailytxt();
						analizectrl.analize();
					}	
					analizeBtn.setDisable(false);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();;
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
	
	
	private void setVariables(){
		session.refreshStages();
		session.setStart(start);
		session.setEnd(end);
		session.setDestination(tf_pathDes.getText());
		session.setPdfReader(reader);
		tfctrl = new TextFileController();
		session.setTextFileController(tfctrl);
		analizectrl = new AnalizeController();
		session.setAnalizeController(analizectrl);
		pdfctrl = new PDFController();
		session.setPdfController(pdfctrl);
		
	}
}
