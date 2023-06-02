 //nimmt aus der stations db die url raus der einzelnen stations und gibt an, wie viele stations
    // es insgesamt gibt, weiter schicken an messaging queue : Einheit Datenbanken

package org.example;
//import für db connection
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import für messaging queue
import com.rabbitmq.client.Channel;
//import com.rabbitmq.client.Connection; angeblich already defined in z6.
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;


    public class DatabaseConnection {
        //Sends a message for every charging station to the Station Data Collector
        private final static String QUEUE_NAME = "SENDER"; //mq

        public static void main(String[] args) throws IOException, TimeoutException, SQLException {

            //number of stations:
            int count = 0;
            //MQ
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            factory.setPort(30003);

            String query = "SELECT * FROM station"; //prepared statemenz db

            try ( //Verbindung zur db + prepared st. executen
                  Connection conn = Database.getConnection();
                  PreparedStatement ps = conn.prepareStatement(query);
                  ResultSet rs = ps.executeQuery()
            ) {
                while (rs.next()) { //db daten speichern
                    String db_url = rs.getString("db_url");
                    float lng = rs.getFloat("lng");
                    int id = rs.getInt("id");
                    float lat = rs.getFloat("lat");
                    Station s = new Station(id, db_url, lat, lng);
                    System.out.println(s);
                    count++;

                    //db_url an receiver senden
                    try (
                            com.rabbitmq.client.Connection connection = factory.newConnection();
                            Channel channel = connection.createChannel();
                    ) {
                        String message = s.toString();
                        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                        channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
                    }
                    System.out.println(count);
                }
                System.out.println(count);
                SENDER s = new SENDER();
                s.setCount(count);

            }
        }
    }


