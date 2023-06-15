package org.example;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class generator {
    public void generate(String name, String name2, Integer id) throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("Files\\"+id+".pdf"));

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("Name: "+ name+" "+name2, font);
        Paragraph chunk2 = new Paragraph("Customer ID: "+id.toString(), font);
        Paragraph chunk3 = new Paragraph("KWH usage: 5", font);

        document.add(chunk2);

        document.add(chunk);
        document.close();
    }
}
