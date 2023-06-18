package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class Sender {
    private final static String QUEUE_NAME = "Yellow";
    // Summe der kwhSummen aus dem Collector wird gebildet
    public String summe(List<Double> kwhSum, int cnt, int customerID, int purpleID){
        double summe=0;
        for (double kwh :kwhSum) {
            summe += kwh;
        }
        // wenn die customerID aus dem Collector nicht gleich ist,
        // wie die CustomerID aus dem Dispatcher, kommt ein Fehler und die Summe wird auf 0 gesetzt
        // damit keine pdf geprintet wird
        if(customerID!=purpleID){
            System.out.println("Es ist ein Fehler aufgetreten. " +
                    "Die ID aus dem StationDataCollector und aus dem DataCollectionReceiver stimmen nicht überein");
            summe=0;
        }
        // Format für pdf gebildet
        String message=customerID+";"+summe;
        //Liste kwhSume wird danach geleert
        if(kwhSum.size()==cnt){
            kwhSum.clear();
        }

        return message;
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
                // Nachricht an die Queue senden
                channel.basicPublish("", QUEUE_NAME, null, messageBytes);
                System.out.println("Sent message: " + message);
            } catch (IOException e) {
                System.out.println("Fehler beim Senden der Nachricht: " + e.getMessage());
            }


        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

}
