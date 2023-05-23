package org.example;

import com.itextpdf.text.DocumentException;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws DocumentException, FileNotFoundException {
        generator gen = new generator();
        gen.generate();
        System.out.println("Hello world!");
    }
}