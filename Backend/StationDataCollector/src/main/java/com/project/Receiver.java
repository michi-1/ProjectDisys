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

                Sender sender = new Sender();
                sender.setCustomerId(recMessage);
                Database database=new Database();
                //recMessage.split()
                database.selectKwh(sender);
                //String message= sender.sendCustomerId();
                sender.send(recMessage);



        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
    }







}



