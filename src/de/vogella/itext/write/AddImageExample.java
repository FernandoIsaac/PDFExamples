/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.vogella.itext.write;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.net.URL;

/**
 *
 * @author isaacreyes
 */
public class AddImageExample {

    /**
     * @param args the command line arguments
     */
    	public static void main(String[] args) {
		Document document = new Document(); 
		try
		{
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("AddImageExample.pdf"));
			document.open();
			document.add(new Paragraph("Image Example"));

			//Add Image
			Image image1 = Image.getInstance("/Users/isaacreyes/Downloads/JavaiTextPDFExamples/temp.png");
			//For Fixed Positioning
			//image1.setAbsolutePosition(100f, 550f);
			//Scale to new height and new width of image
			image1.scaleAbsolute(20, 20);
			//Add to document
			document.add(image1);

			String imageUrl = "http://www.eclipse.org/xtend/images/java8_logo.png";
			Image image2 = Image.getInstance(new URL(imageUrl));
			document.add(image2);

			document.close();
			writer.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
    
}
