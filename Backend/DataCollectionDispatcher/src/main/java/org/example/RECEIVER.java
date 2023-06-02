package org.example;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.concurrent.TimeoutException;
import org.example.SENDER;

public class RECEIVER {
    private final static String QUEUE_NAME = "CUSTOMERID";

    public static void receive() throws IOException, TimeoutException {


        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(30003);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String customerid = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + customerid + "' " + LocalTime.now());

            int id = Integer.parseInt(customerid);

            //Customerid an den SENDER weitergeben.
            SENDER s = new SENDER();
            s.setID(id);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(" [x] Finished '" + customerid + "' " + LocalTime.now());
        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });

    }
}