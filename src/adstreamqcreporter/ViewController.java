package adstreamqcreporter;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.ChoiceBox;

public class ViewController extends AdstreamQcReporter implements Initializable {
    
//<editor-fold defaultstate="collapsed" desc="Class variables">
    protected static String orderReferenceIn;
    protected static String clockNumberIn;
    protected static String clientIn;
    protected static String brandIn;
    protected static String productIn;
    protected static String ingestOperatorIn;
    protected static String videoTextIn;
    protected static String audioTextIn;
    protected static String notesTextIn;
    protected static String pdfName="";
    private int j = 0;
    protected static List<File> file = new ArrayList<>();
    protected static String pdfPathTemp = "";
    protected static String pdfPathFinal = "";
    protected static String trafficTeamIn;    
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="@FXML">
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Pane basePane;
    @FXML
    private Pane alertPane;
    @FXML
    private Pane alertPane2;
    @FXML
    private Label alertText;
    @FXML
    private Label alertText2;
    @FXML
    private TextField orderReference;
    @FXML
    private TextField clockNumber;
    @FXML
    private TextField client;
    @FXML
    private TextField brand;
    @FXML
    private TextField product;
    @FXML
    private TextField ingestOperator;
    @FXML
    private TextArea videoText;
    @FXML
    private TextArea audioText;
    @FXML
    private TextArea notesText;
    @FXML
    private Label attachmentLabel;
    @FXML
    private ChoiceBox<String> trafficTeam;
    
    
    @FXML
    private void handleAlertButton(ActionEvent event) {
        basePane.setDisable(false);
        basePane.setOpacity(1);
        alertPane.setVisible(false);
        alertText.setText("");
    }
    
    @FXML
    private void attachButtonAction(ActionEvent event) {   
        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("Add attachments");
        Stage stage = (Stage)anchorPane.getScene().getWindow();
        
        file = filechooser.showOpenMultipleDialog(stage);
        if (file != null)
            attachmentLabel.setText("(" + file.size() + " added)");
    }
    
    @FXML
    private void generateButtonAction(ActionEvent event){
        gatherFields();
        if (!basePane.isDisabled()){
        PdfGenerator pg = new PdfGenerator();
        pg.generatePdf();
        pg.pdfAddPictures();
        EmailSender es = new EmailSender();
        es.emailSender();
        alertSent("The QC report has been sent to \n Adstream " + trafficTeamIn);
        }
    }
    
    @FXML
    private void resetButtonAction(ActionEvent event){
        reset();
    }
//</editor-fold>
        
    private void reset(){       
        Stage stage = (Stage) anchorPane.getScene().getWindow();    
        stage.close();
        Platform.runLater( () -> {
            try {
                new AdstreamQcReporter().start( new Stage() );
            } catch (Exception ex) {
                Logger.getLogger(ViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } );      
    }
    
    private void alert(String text){
        basePane.setDisable(true);
        basePane.setOpacity(0.3);
        alertPane.setVisible(true);
        alertText.setText(text);
    }
    
    protected void alertSent(String text){
        basePane.setDisable(true);
        basePane.setOpacity(0.3);
        alertPane2.setVisible(true);
        alertText2.setText(text);
    }
    
    private void gatherFields(){
        orderReferenceIn = "";
        clockNumberIn = "";
        clientIn = "";
        brandIn = "";
        productIn = "";
        ingestOperatorIn = "";
        videoTextIn = "";
        audioTextIn = "";
        notesTextIn = "";
        pdfName = "";
        pdfPathTemp = "";
        trafficTeamIn = "";
        
        try{
         orderReferenceIn = orderReference.getText();
         clockNumberIn = clockNumber.getText();
         clientIn = client.getText();
         brandIn = brand.getText();
         productIn = product.getText();
         ingestOperatorIn = ingestOperator.getText();
         videoTextIn = videoText.getText();
         audioTextIn = audioText.getText();
         notesTextIn = notesText.getText();        
         trafficTeamIn = trafficTeam.getValue();       
                
        if (orderReferenceIn.length() < 1 || clockNumberIn.length() < 1 || clientIn.length() < 1 || brandIn.length() < 1 || productIn.length() < 1 || ingestOperatorIn.length() < 1){
            alert("The underlined fields are mandatory!");
            return;   
        }
        
        if (trafficTeamIn == null){
            alert("Please select a traffic team!");
            return;
        }
        
        for (int i = 0; i < clockNumberIn.length(); i++)
            if (clockNumberIn.substring(j,j+1).equals("/")){
                pdfName = pdfName + "_";
                j++;
            }          
            else{
                pdfName = pdfName + clockNumberIn.substring(j,j+1);
                j++;
            }
        
        pdfPathTemp = "f:/QC_reports/" + pdfName + "_temp" + ".pdf";
        pdfPathFinal = "f:/QC_reports/" + pdfName + "_QC_report" + ".pdf";     
        }
        
        catch (Exception e){        
        }
    }        
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }        
}
