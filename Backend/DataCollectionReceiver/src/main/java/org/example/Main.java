package org.example;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("Data Collection Receiver");
        Receiver receiver = new Receiver();
        receiver.rec();

    }
}