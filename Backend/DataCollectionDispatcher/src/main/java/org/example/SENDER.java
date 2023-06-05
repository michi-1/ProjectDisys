package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class SENDER {
    //customer id und count weiterleiten, die ich vom receiver bekomme
    private final static String QUEUE_NAME = "Green"; //mq
    private static int custerid;
    private static int counter; //number of stations

    //Constructor
    public void setID(int cid){ //customer id aus mq
        this.custerid = cid;
        System.out.println("CUSTOMERID:" + custerid);
    }
    public void setCount(int count){ //number of stations aus main
        this.counter = count;
            System.out.println("COUNTER:" + count);
    }

    //send customer id und number of stations

    public static void main(String[] args) throws IOException, TimeoutException, SQLException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(30003);

        try (
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();
        ) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            Scanner scanner = new Scanner(System.in);

            String message = "Customerid und Counter: ";

            String cid = String.valueOf(custerid);

            String cnt = String.valueOf(counter);

                channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
                channel.basicPublish("", QUEUE_NAME, null, cid.getBytes(StandardCharsets.UTF_8));
                channel.basicPublish("", QUEUE_NAME, null, cnt.getBytes(StandardCharsets.UTF_8));

        }

    }
    }


