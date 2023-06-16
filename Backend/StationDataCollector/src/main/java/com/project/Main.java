package com.project;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;
public class Main {

    public static void main(String[] args) throws IOException, TimeoutException {
        Receiver receiver = new Receiver();
        receiver.receive();



//Test

        //sender.send();

         /*try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int customerID = receiver.getCustomerID();
        String url = receiver.getUrl();
        int stationnr = receiver.getStationnr();

        System.out.println("Customer ID: " + customerID);
        System.out.println("URL: " + url);
        System.out.println("Stationsnummer: " + stationnr);*/


    }
}