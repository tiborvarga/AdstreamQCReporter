package adstreamqcreporter;

import static adstreamqcreporter.ViewController.pdfName;
import static adstreamqcreporter.ViewController.pdfPathFinal;
import static adstreamqcreporter.ViewController.trafficTeamIn;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSender {
    
    private final String hungaryEmail = "tibor.varga@adstream.com";
    private final String italyEmail = "";
    private final String greeceEmail = "";
    private String recipientsEmail = "";
    private static Properties mailServerProperties;
    private static Session getMailSession;
    private static MimeMessage generateMailMessage;
    
    private void emailRecipient(){
      try{ 
        switch(trafficTeamIn){
           case "Hungary" : recipientsEmail = hungaryEmail;
               break;
           case "Greece" : recipientsEmail = greeceEmail;
               break;
           case "Italy" : recipientsEmail = italyEmail;
               break;
        }
      }
      catch (Exception e){
          
      }
   }
    
    protected void emailSender(){   
       try{   
        emailRecipient();        
	System.out.println("\n 1st ===> setup Mail Server Properties..");
		mailServerProperties = System.getProperties();
                mailServerProperties.put("mail.smtp.host", "smtp-internal.adstream.com");
		mailServerProperties.put("mail.smtp.port", "25");
		//mailServerProperties.put("mail.smtp.auth", "true");
		//mailServerProperties.put("mail.smtp.starttls.enable", "true");
		System.out.println("Mail Server Properties have been setup successfully..");
 
		// Step2
		System.out.println("\n\n 2nd ===> get Mail Session..");
                
		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		generateMailMessage = new MimeMessage(getMailSession);
                generateMailMessage.setFrom("QCreport.HU@adstream.com");
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientsEmail));
                if (!recipientsEmail.equals(hungaryEmail))
                    generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(hungaryEmail));
		generateMailMessage.setSubject("Adstream Hungary QC Report");
                
                // Create the message part 
                BodyPart messageBodyPart = new MimeBodyPart();

                // Fill the message
                messageBodyPart.setText("Dear Adstream " + trafficTeamIn + ", \n\n" + "You'll find our QC report as an attachment. \n\n" + "Best regards, \nAdstream Hungary");
                
                // Create a multipar message
                Multipart multipart = new MimeMultipart();

                // Set text message part
                multipart.addBodyPart(messageBodyPart);

                // Part two is attachment
                messageBodyPart = new MimeBodyPart();
                String filename = pdfPathFinal;
                DataSource source = new FileDataSource(filename);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(pdfName + "_QC_report" + ".pdf");     
                multipart.addBodyPart(messageBodyPart);

                // Send the complete message parts
                generateMailMessage.setContent(multipart );
                
                
		System.out.println("Mail Session has been created successfully..");
 
		// Step3
		System.out.println("\n\n 3rd ===> Get Session and Send mail");
		/*Transport transport = getMailSession.getTransport("smtp");
 
		// Enter your correct gmail UserID and Password
		// if you have 2FA enabled then provide App Specific Password
		transport.connect("smtp-internal.adstream.com", username, password);
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();*/
                Transport.send(generateMailMessage);
                }
       catch (MessagingException e){
       
       }
   }
}
