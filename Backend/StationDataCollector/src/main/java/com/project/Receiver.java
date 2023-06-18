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
        // Connection Factory erstellen
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(30003);

        // Verbindung zur RabbitMQ erstellen
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // Callback für empfangene Nachrichten
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String recMessage=new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("Received "+recMessage);
                // die empfangene message wird an ";" gesplittet
                String[] messages=recMessage.split(";");
                //cusId ist Wert an Stelle 0 und recURL ist Wert an Stelle 1
                String cusId=messages[0];
                String recURL=messages[1];
                // sender definiert
                Sender sender = new Sender();
                sender.setCustomerId(cusId);
                sender.setkwhsum(recURL);
                //recURL wird noch enmal gesplittet, damit nur Zahl übrig bleibt
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



