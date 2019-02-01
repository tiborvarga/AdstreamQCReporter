package adstreamqcreporter;

import static adstreamqcreporter.ViewController.audioTextIn;
import static adstreamqcreporter.ViewController.advertiserIn;
import static adstreamqcreporter.ViewController.clockNumberIn;
import static adstreamqcreporter.ViewController.file;
import static adstreamqcreporter.ViewController.ingestOperatorIn;
import static adstreamqcreporter.ViewController.notesTextIn;
import static adstreamqcreporter.ViewController.orderReferenceIn;
import static adstreamqcreporter.ViewController.pdfPathFinal;
import static adstreamqcreporter.ViewController.pdfPathTemp;
import static adstreamqcreporter.ViewController.productIn;
import static adstreamqcreporter.ViewController.videoTextIn;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PdfGenerator {
        protected void generatePdf(){
        try{
            PdfReader pdfTemplate = new PdfReader(getClass().getResourceAsStream("/QcReportSample.pdf"));
            FileOutputStream fileOutputStream = new FileOutputStream(pdfPathTemp, false);
            //ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfStamper stamper = new PdfStamper(pdfTemplate, fileOutputStream);
            stamper.setFormFlattening(true);

            DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd. HH:mm:ss");
            Date date = new Date();

            stamper.getAcroFields().setField("orderReference", orderReferenceIn);
            stamper.getAcroFields().setField("clockNumber", clockNumberIn);
            stamper.getAcroFields().setField("advertiser", advertiserIn);
            stamper.getAcroFields().setField("product", productIn);
            stamper.getAcroFields().setField("ingestOperator", ingestOperatorIn);
            stamper.getAcroFields().setField("video", videoTextIn);
            stamper.getAcroFields().setField("audio", audioTextIn);
            stamper.getAcroFields().setField("notes", notesTextIn);
            stamper.getAcroFields().setField("date_af_date", dateFormat.format(date));	

            stamper.close();

            pdfTemplate.close();
	}
    
        catch (DocumentException | IOException ex){    
        }
}
   
    protected void pdfAddPictures(){
        try{
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfPathFinal));
        
        document.open();
        PdfContentByte cb = writer.getDirectContent();

        PdfReader reader = new PdfReader(pdfPathTemp);
        PdfImportedPage page = writer.getImportedPage(reader, 1); 
           
        document.newPage();
        cb.addTemplate(page, 0, 0);
        
        if (file != null){
            for (int i=0; i<file.size(); i++){
                String imageFile = "" + file.get(i);
                Image img = Image.getInstance(imageFile);
                img.setBorder(0);
                img.setAbsolutePosition(0,0);             
                document.setPageSize(img);
                document.newPage();
                document.add(img);
            }        
        }
        document.close();
        reader.close();

        File fileTemp = new File(pdfPathTemp);
        fileTemp.delete();
        file = null;
    }
        catch (DocumentException | IOException e){           
        }
}
}
