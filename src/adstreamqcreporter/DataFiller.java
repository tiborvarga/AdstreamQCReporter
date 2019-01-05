package adstreamqcreporter;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class DataFiller {
    
    protected static final String path = "src/screenshot.jpg"; 
    
    protected static void dataFiller(){
        printScreen();
        getImgText();
        
        
        File filePrntscrn = new File(path);
        filePrntscrn.delete();
    }
    
    private static void printScreen(){
        try {
            try { 
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(DataFiller.class.getName()).log(Level.SEVERE, null, ex);
            }
            Robot r = new Robot(); 
  
            // Used to get ScreenSize and capture image 
            Rectangle capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()); 
            BufferedImage Image = r.createScreenCapture(capture);
            FileOutputStream fileOutputStream = new FileOutputStream(path, false);
            ImageIO.write(Image, "jpg", fileOutputStream); 
            System.out.println("Screenshot saved"); 
        } catch (AWTException | IOException ex) {
            System.err.println(ex);
        }
    }
        
    private static void getImgText() {
        
        
        File screenshot = new File(path);
        screenshot.delete();
    }      
    
}
