package com.project;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;
public class Main {
    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("Station Data Collector: ");
        Receiver receiver = new Receiver();
        receiver.receive();
    }
}