package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class TestReceiver {


        private final static String QUEUE_NAME = "Yellow";

        public void receiving() throws IOException, TimeoutException {

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            factory.setPort(30003);

            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String recMessage = new String(delivery.getBody(), StandardCharsets.UTF_8);

                System.out.println("Received " + recMessage);
            };
           channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
        }
    }

