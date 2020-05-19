/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author isaacreyes
 */
package de.vogella.itext.write;

import java.io.FileOutputStream;
import java.util.Date;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;


public class FirstPdf {
    private static String FILE = "/Users/isaacreyes/Desktop/FIRM/Joel/TestingComplete.pdf"; 
    
    private static String FILE2 = "/Users/isaacreyes/Desktop/FIRM/Joel/TestingCompleteNumbered.pdf"; 
    
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    private static int pageCount = 0;
//    private static ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
//    private static byte[] pdfAsBytes = pdfOutputStream.toByteArray();

    private static PdfTemplate template;

    
    static class MyFooter extends PdfPageEventHelper {
        Font ffont = new Font(Font.FontFamily.UNDEFINED, 10, Font.ITALIC);
            
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();
            Phrase header = new Phrase("this is a header", ffont);
            Phrase footer = new Phrase("this is a footer "+ writer.getCurrentPageNumber(), ffont);
            pageCount = writer.getCurrentPageNumber();
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                    header,
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.top() + 10, 0);
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                    footer,
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.bottom() - 10, 0);
        }
        

//        private void addFooter(PdfWriter writer){
//            PdfPTable footer = new PdfPTable(3);
//            // set defaults
//            //footer.setWidths(new int[]{24, 2, 1});
//            //footer.setTotalWidth(527);
//            //footer.setLockedWidth(true);
//            //footer.getDefaultCell().setFixedHeight(40);
//            //footer.getDefaultCell().setBorder(Rectangle.TOP);
//            //footer.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
//            
//            // add copyright
//            //footer.addCell(new Phrase("\u00A9 Memorynotfound.com", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
//            
//            // add current page count
//            footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
//            footer.addCell(new Phrase(String.format("Page %d of", writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)));
//            // add placeholder for total page count
//            //PdfPCell totalPageCount = new PdfPCell(total);
//            //totalPageCount.setBorder(Rectangle.TOP);
//            //totalPageCount.setBorderColor(BaseColor.LIGHT_GRAY);
//            //footer.addCell(totalPageCount);
//            
//            // write page
//            PdfContentByte canvas = writer.getDirectContent();
//            //canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
//            footer.writeSelectedRows(0, -1, 34, 50, canvas);
//            canvas.endMarkedContentSequence();
//        }        
        
        public void onCloseDocument(PdfWriter writer, Document document) {
            int totalLength = String.valueOf(writer.getPageNumber()).length();
            int totalWidth = totalLength * 5;
            ColumnText.showTextAligned(template, Element.ALIGN_RIGHT,
                new Phrase(String.valueOf(writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)),
                totalWidth, 6, 0);
        }
    }
    
    public static void main(String[] args) {
        try {
            Document document = new Document();
            //PdfWriter.getInstance(document, new FileOutputStream(FILE));
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE));
            MyFooter event = new MyFooter();
            writer.setPageEvent(event);
            document.open();
            template = writer.getDirectContent().createTemplate(30, 16);
            addMetaData(document);
            addTitlePage(document);
            addContent(document);
            document.close();
            
            ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
            
            byte[] pdfAsBytes = pdfOutputStream.toByteArray();

//add footer
           // PdfReader reader = new PdfReader(pdfAsBytes);
            PdfReader reader = new PdfReader(FILE);
            //document = new Document();
            document.open();
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(FILE2));
            for (int i = 1; i <= pageCount; i++) {
              ColumnText.showTextAligned(stamper.getOverContent(i), 
                Element.ALIGN_CENTER, new Phrase(String.format("page %s of %s", i, pageCount)), 550, 30, 0);
            }
            stamper.close();
            File f = new File(FILE);
            f.delete();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // iText allows to add metadata to the PDF which can be viewed in your Adobe
    // Reader
    // under File -> Properties
    private static void addMetaData(Document document) {
        document.addTitle("My first PDF");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Lars Vogel");
        document.addCreator("Lars Vogel");
    }

    private static void addTitlePage(Document document)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph("Title of the document", catFont));

        addEmptyLine(preface, 1);
        // Will create: Report generated by: _name, _date
        preface.add(new Paragraph(
                "Report generated by: " + System.getProperty("user.name") + ", " + new Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                smallBold));
        addEmptyLine(preface, 3);
        preface.add(new Paragraph(
                "This document describes something which is very important ",
                smallBold));

        addEmptyLine(preface, 8);

        preface.add(new Paragraph(
                "This document is a preliminary version and not subject to your license agreement or any other agreement with vogella.com ;-).",
                redFont));

        document.add(preface);
        // Start a new page
        document.newPage();
    }

    private static void addContent(Document document) throws DocumentException {
        Anchor anchor = new Anchor("First Chapter", catFont);
        anchor.setName("First Chapter");

        // Second parameter is the number of the chapter
        Chapter catPart = new Chapter(new Paragraph(anchor), 1);

        Paragraph subPara = new Paragraph("Subcategory 1", subFont);
        Section subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Hello"));

        subPara = new Paragraph("Subcategory 2", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Paragraph 1"));
        subCatPart.add(new Paragraph("Paragraph 2"));
        subCatPart.add(new Paragraph("Paragraph 3"));

        // add a list
        createList(subCatPart);
        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 5);
        subCatPart.add(paragraph);

        // add a table
        createTable(subCatPart);

        // now add all this to the document
        document.add(catPart);

        // Next section
        anchor = new Anchor("Second Chapter", catFont);
        anchor.setName("Second Chapter");

        // Second parameter is the number of the chapter
        catPart = new Chapter(new Paragraph(anchor), 1);

        subPara = new Paragraph("Subcategory", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("This is a very important message"));
        
        
        // now add all this to the document
        document.add(catPart);

    }

    private static void createTable(Section subCatPart)
            throws BadElementException {
        PdfPTable table = new PdfPTable(3);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c1 = new PdfPCell(new Phrase("Casa"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Arbol"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Persona"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        table.addCell("1. Rasgos Normales \nPocas borraduras \nSimetria");
        table.addCell("1. Rasgos Normales \nPocas borraduras \nSimetria \nLineas ni demasiado debiles ni demasiado marcadas");
        table.addCell("1. Rasgos Normales \nSe dibujaron las pupilas \nNariz sin orificios ");
        table.addCell("2. Tamaño \nGrande");
        table.addCell("2. Tamaño \nPequeño");
        table.addCell("2.Tamaño \nPequeña");

        subCatPart.add(table);

    }

    private static void createList(Section subCatPart) {
        List list = new List(true, false, 10);
        list.add(new ListItem("First point"));
        list.add(new ListItem("Second point"));
        list.add(new ListItem("Third point"));
        subCatPart.add(list);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}