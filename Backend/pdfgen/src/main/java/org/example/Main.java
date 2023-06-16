package org.example;

import com.itextpdf.text.DocumentException;

import java.io.Console;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) throws DocumentException, IOException, TimeoutException {
        System.out.println("pdfgen");
        Receiver rec = new Receiver();

        rec.receive();
    }
}