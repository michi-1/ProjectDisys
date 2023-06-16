package com.project;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeoutException;
import java.nio.charset.StandardCharsets;
import java.lang.String;



public class Receiver {

    private final static String QUEUE_NAME = "Green";

    public void receive() throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(30003);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String recMessage=new String(delivery.getBody(), StandardCharsets.UTF_8);

            System.out.println("Received "+recMessage);
                //Sender wird definiert
                String[] messages=recMessage.split(";");
                String cusId=messages[0];
                String recURL=messages[1];
                Sender sender = new Sender();
                //Die Funktion setCustomerId() wird aufgerufen
                sender.setCustomerId(cusId);
                sender.setkwhsum(recURL);
                String[] newURL=recURL.split(":");
                //Eine Datenbankinstanz wird definiert
                Database database=new Database();
               database.setPort(Integer.parseInt(newURL[1]));

                database.selectKwh(sender);
                String message=sender.sendCustomerId();
                sender.send(message);




        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
    }







}



