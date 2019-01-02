package adstreamqcreporter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdstreamQcReporter extends Application {
 
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));
        
        Scene scene = new Scene(root);
        
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Adstream Hungary QC report generator");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
