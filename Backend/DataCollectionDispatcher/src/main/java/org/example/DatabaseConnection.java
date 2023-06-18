package org.example;//funktion wurde in SENDER: sendGreen()

import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.concurrent.TimeoutException;
import java.util.List;


public class DatabaseConnection{


    //Sends a message for every charging station to the Station Data Collector

    public List<String> getData() throws IOException, TimeoutException, SQLException {
        System.out.println("getData()");
        List<String> data = new LinkedList<>();

        //number of stations:
        int count = 0;
        //MQ

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
                //daten hier im String arr speichern:
                data.add(db_url);
                count++;
                //array an db urls weitergeben an queue
            }

        }

        return data;
    }



}

