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
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Main {


    public static void main(String[] args) throws IOException, TimeoutException, SQLException {


        //empfängt custerid
        System.out.println("Data Collection Dispatcher: ");
        Queue queue = new Queue();
        queue.receive();

    }
}
