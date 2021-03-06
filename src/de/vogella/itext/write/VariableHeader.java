/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.vogella.itext.write;

/**
 *
 * @author isaacreyes
 */
 
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
//import sandbox.WrapToTest;
 
//@WrapToTest
public class VariableHeader {
    public static final String DEST = "/Users/isaacreyes/NetBeansProjects/PDFExample1/variable_header.pdf";
    
    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new VariableHeader().createPdf(DEST);
    }
    
    public class Header extends PdfPageEventHelper {
 
        protected Phrase header;
        
        public void setHeader(Phrase header) {
            this.header = header;
        }
        
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte canvas = writer.getDirectContentUnder();
            ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, header, 559, 806, 0);
        }
    }
    
    public void createPdf(String filename) throws IOException, DocumentException {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
        Header event = new Header();
        writer.setPageEvent(event);
        // step 3
        document.open();
        // step 4
        List<Integer> factors;
        for (int i = 2; i < 301; i++) {
            factors = getFactors(i);
            if (factors.size() == 1) {
                document.add(new Paragraph("This is a prime number!"));
            }
            for (int factor : factors) {
                document.add(new Paragraph("Factor: " + factor));
            }
            event.setHeader(new Phrase(String.format("THE FACTORS OF %s", i)));
            document.newPage();
        }
        // step 5
        document.close();
    }
    
    public static List<Integer> getFactors(int n) {
        List<Integer> factors = new ArrayList<Integer>();
        for (int i = 2; i <= n; i++) {
          while (n % i == 0) {
            factors.add(i);
            n /= i;
          }
        }
        return factors;
    }
}

