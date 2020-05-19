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
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
 
 
public class StampPageXofY {
 
    public static final String SRC = "/Users/isaacreyes/Desktop/FIRM/Joel/bugsTest.pdf";
    public static final String DEST = "/Users/isaacreyes/Desktop/FIRM/Joel/SecondPdf.pdf";
 
    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new StampPageXofY().manipulatePdf(SRC, DEST);
    }
 
    public void manipulatePdf(String src, String dest) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        int n = reader.getNumberOfPages();
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        PdfContentByte pagecontent;
        for (int i = 0; i < n; ) {
            pagecontent = stamper.getOverContent(++i);
            ColumnText.showTextAligned(pagecontent, Element.ALIGN_LEFT,
                    new Phrase(String.format("page %s of %s", i, n)), 20, 20, 0);
            ColumnText.showTextAligned(pagecontent, Element.ALIGN_BOTTOM, new Phrase("Esto va en el centro"), 250, 20, 0);
            ColumnText.showTextAligned(pagecontent, Element.ALIGN_RIGHT, new Phrase("Esto va por la derecha"), 575, 20, 0);
        }
        stamper.close();
        reader.close();
    }
}
