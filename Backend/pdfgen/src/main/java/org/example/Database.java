package org.example;

import com.itextpdf.text.DocumentException;

import java.io.FileNotFoundException;
import java.sql.*;

public class Database {
    public void GetCustomerData(String id, String kwh) {
        String url = "jdbc:postgresql://localhost:30001/customerdb";
        String username = "postgres";
        String password = "postgres";
        int id2 = 0;
        String name = "";
        String name2 = "";
        if (kwh.equals("0.0")) {
            System.out.println("Kein valider Customer.");
        } else {
            try {
                Connection connection = DriverManager.getConnection(url, username, password);

                // Create a statement object
                Statement statement = connection.createStatement();

                // Execute a query
                String query = "SELECT * FROM customer where id = " + id;
                ResultSet resultSet = statement.executeQuery(query);

                // Process the results
                while (resultSet.next()) {
                    // Retrieve data from the result set
                    id2 = resultSet.getInt("id");
                    name = resultSet.getString("first_name");
                    name2 = resultSet.getString("last_name");
                    // Process the retrieved data as needed

                }
                System.out.println("ID: " + id + ", Name: " + name + name2);
                generator gen = new generator();

                gen.generate(name, name2, id2, kwh);
                // Close the resources
                resultSet.close();
                statement.close();
                connection.close();


            } catch (SQLException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
