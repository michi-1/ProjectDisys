package org.example;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) throws IOException, TimeoutException {
        Receiver receiver = new Receiver();
        receiver.rec();
        //TestReceiver receiving=new TestReceiver();
        //receiving.receiving();

    }
}