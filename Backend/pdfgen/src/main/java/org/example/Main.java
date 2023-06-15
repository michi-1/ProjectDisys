package org.example;

import com.itextpdf.text.DocumentException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws DocumentException, FileNotFoundException {
        String url = "jdbc:postgresql://localhost:30001/customerdb";
        String username = "postgres";
        String password = "postgres";
        int id = 0;
        String name = "";
        String name2 = "";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);

            // Create a statement object
            Statement statement = connection.createStatement();

            // Execute a query
            String query = "SELECT * FROM customer where id = 2";
            ResultSet resultSet = statement.executeQuery(query);

            // Process the results
            while (resultSet.next()) {
                // Retrieve data from the result set
                  id = resultSet.getInt("id");
                  name = resultSet.getString("first_name");
                  name2 = resultSet.getString("last_name");
                // Process the retrieved data as needed

            }
            System.out.println("ID: " + id + ", Name: " + name + name2);
            generator gen = new generator();
            gen.generate(name, name2,id);
            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}