package org.example;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.*;

import java.lang.String;

public class Receiver {
    private final static String QUEUE_NAME = "Blue";
    private final static String QUEUE_NAME1 = "Purple";

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
            Sender sender=new Sender();
            count++;
            List<Double> list=sender.list(recMessage,count,cnt);


            if(count==cnt) {
                String message=sender.summe(list,cnt);
                sender.send(message);
                count=0;
            }

        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
    }


    public void rec() throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(30003);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME1, false, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {

            String counter=new String(delivery.getBody(), StandardCharsets.UTF_8);
            String[] purpleInfos=counter.split(";");
            String counterValue=purpleInfos[1];
            //String counterValue = counter.replaceAll("^.Counter: (\\d+).$", "$1");
            cnt=Integer.parseInt(counterValue);
            try {
                receive();
            } catch (TimeoutException e) {
                throw new RuntimeException(e);
            }



        };

        channel.basicConsume(QUEUE_NAME1, true, deliverCallback, consumerTag -> {});
    }
}

