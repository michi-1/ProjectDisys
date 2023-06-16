package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.concurrent.TimeoutException;
import java.util.List;

public class Queue {
    private final static String QUEUE_NAME = "Red"; //Receiver customer_id
    private final static String QUEUE_NAME1 = "Purple"; //Sender
    private final static String QUEUE_NAME2 = "Green"; //Sender


    public void receive() throws IOException, TimeoutException {
        System.out.println("receive()");

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(30003);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String customerid = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received Customerid '" + customerid + "' " + LocalTime.now());
            int cid = Integer.parseInt(customerid);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(" [x] Finished '" + customerid + "' " + LocalTime.now());


            try {
                DatabaseConnection db = new DatabaseConnection();
                List <String> data = db.getData();
                this.sendGreen(channel, data,  cid);
            } catch (TimeoutException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }


    //send url and station_id to station data collector
    //ein String arr mit den Werten weitergeben und costumerid
    public void sendGreen(Channel channel, List <String> data , int customerid) throws IOException, TimeoutException, SQLException {
        System.out.println("sendGreen(): daten aus db holen");

        //db_url an receiver senden
        int length = data.size();
        for(int i = 0; i< length; i++) {
            String message = customerid+";"+data.get(i); //einzelne Werte der datenbank
            System.out.println(message);
            channel.queueDeclare(QUEUE_NAME2, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME2, null, message.getBytes(StandardCharsets.UTF_8));
            if(i==length-1) {
                sendPurple(channel, customerid, length);
            }
        }
    }


    public void sendPurple(Channel channel, int customerid, int count) throws IOException, TimeoutException, SQLException {
        System.out.println("sendPurple()");
        channel.queueDeclare(QUEUE_NAME1, false, false, false, null);
        //String message = "Customerid und Counter: ";
        String cid = String.valueOf(customerid);
        String cnt = String.valueOf(count);
        String message=cid+";"+cnt;

       // System.out.println("Customerid: " +  cid +  "Counter: " + cnt);

        channel.basicPublish("", QUEUE_NAME1, null, message.getBytes(StandardCharsets.UTF_8));
        System.out.println(message);


    }

}

