package org.example;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.*;

import javax.xml.crypto.Data;
import java.lang.String;

public class Receiver {
    private final static String QUEUE_NAME = "Yellow";


    private int cnt;

    int count=0;
    public void receive() throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(30003);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {

            String recMessage=new String(delivery.getBody(), StandardCharsets.UTF_8);
            String id = recMessage.split(";")[0];
            String kwh = recMessage.split(";")[1];
            Database db = new Database();
            db.GetCustomerData(id,kwh);




        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
    }
}
