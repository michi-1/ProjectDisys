package org.example;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class generator {
    public void generate(String name, String name2, Integer id, String kwh) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("Files\\"+id+".pdf"));
        Font invoiceHeadingFont = FontFactory.getFont(FontFactory.COURIER_BOLD, 20, BaseColor.BLACK);
        Chunk invoiceHeadingChunk = new Chunk("Invoice Details", invoiceHeadingFont);
        Paragraph invoiceHeadingParagraph = new Paragraph(invoiceHeadingChunk);
        invoiceHeadingParagraph.setAlignment(Element.ALIGN_CENTER);
        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("Name: "+ name+" "+name2, font);
        Paragraph chunk2 = new Paragraph("Customer ID: "+id.toString(), font);
        Paragraph chunk3 = new Paragraph("KWH usage: "+kwh , font);
        document.add(invoiceHeadingParagraph);

        document.add(chunk2);

        document.add(chunk);
        document.add(chunk3);
        document.close();
    }
}
