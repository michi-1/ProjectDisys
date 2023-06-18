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
    // Anzahl vom Counter von Dispatcher
    private int cnt;
    // CustomerID von Dispatcher
    int purpID;
    // wird um 1 erhöht je nachdem wie viele Nachrichten zu verarbeiten sind
    int count=0;
     // Liste für die kwhSumme aus dem Kollektor je Station
    private static List<Double> kwhSum=new ArrayList<>();

    public void receive() throws IOException, TimeoutException {
        // Verbindung zum RabbitMQ-Server herstellen
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(30003);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // Queue deklarieren, von der empfangen werden soll
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
         // Callback zum Empfangen von Nachrichten
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            // Nachricht in UTF-8 kodieren
            String recMessage=new String(delivery.getBody(), StandardCharsets.UTF_8);
            // analog zu Collector wird nachricht durch ";" getrennt, um Liste von Strings zu erhalten,
            // die aus 2 Strings besteht
            String[] messages = recMessage.split(";");
            // an Stelle 0 befindet sich kwhSumme je Station
            double message=Double.parseDouble(messages[0]);
            // an Stelle 1 befindet sich customerId
            int customerID=Integer.parseInt(messages[1]);
            //solange count < die Anzahl, die vom Dispatcher gesendet wird ist, wird in der Liste kwhSum
            // der jeweilige Wert geaddet
            if(count<=cnt) {
               kwhSum.add(message);
            }
            System.out.println("Received message from green:"+recMessage);

            count++;
            //Wenn alle Nachrichten da sind, wird der Sender aufgerufen
            if(count==cnt) {
                Sender sender=new Sender();
                // hier wird die Summe gebildet
                String messageSend=sender.summe(kwhSum,cnt,customerID,purpID);
                sender.send(messageSend);
                // der Zähler wird zurückgesetzt
                count=0;
            }

        };
        // Nachrichtenempfang starten
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
    }


    public void rec() throws IOException, TimeoutException {
       // Verbindung zum RabbitMQ-Server herstellen
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(30003);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // Queue deklarieren, von der empfangen werden soll
        channel.queueDeclare(QUEUE_NAME1, false, false, false, null);
        // Callback zum Empfangen von Nachrichten
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            // Nachricht in UTF-8 kodieren
            String counter=new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("Received message from purple:"+counter);
            // wie davor split, um die Daten von Dispatcher einzeln zu erhaltem und parsen zu können
            String[] purpleInfos=counter.split(";");
            // countervalue wird definiert und geparsed
            String counterValue=purpleInfos[1];
            cnt=Integer.parseInt(counterValue);

            try {

                String idValue=purpleInfos[0];
                //ID aus Dispatcher wird definiert und geparsed
               purpID=Integer.parseInt(idValue);
               // Funktion receive wird aufgerufen, welche die Nachrichten vom Collector verarbeitet
                receive();
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid ID format: " + e.getMessage(), e);
            } catch (TimeoutException e) {
                throw new RuntimeException(e);
            }


        };


        channel.basicConsume(QUEUE_NAME1, true, deliverCallback, consumerTag -> {});

    }
}

