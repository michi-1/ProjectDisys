package com.project;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Sender {
    private final static String QUEUE_NAME = "Blue";
    private String url;
    private String customerId;

    private String kwh_sum;



    public String getkwh_sum() {
        return kwh_sum;
    }

    public String getCustomerId(){
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void send(String message) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(30003);


        try (
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()
        ) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);

            try {
                channel.basicPublish("", QUEUE_NAME, null, messageBytes);
                System.out.println("Sent message: " + message);
            } catch (IOException e) {
                System.out.println("Fehler beim Senden der Nachricht: " + e.getMessage());
            }


        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }


    public String sendCustomerId(){
        String message=this.kwh_sum+";"+this.customerId;
        return message;
    }


    public void setkwhsum(String s) {
        this.kwh_sum=s;
    }
}
