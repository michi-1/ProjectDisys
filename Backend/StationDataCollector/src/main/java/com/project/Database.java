package com.project;

import java.sql.*;

public class Database {
    private static String url;
    private final static String DRIVER = "postgresql";
    private final static String HOST = "localhost";
    private static int PORT;
    private final static String DATABASE_NAME = "stationdb";
    private final static String USERNAME = "postgres";
    private final static String PASSWORD = "postgres";

    public static void setPort(int port){
    PORT=port;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(getUrl());
    }

    private static String getUrl() {
        return String.format(
                "jdbc:%s://%s:%s/%s?user=%s&password=%s", DRIVER, HOST, PORT, DATABASE_NAME, USERNAME, PASSWORD
        );
    }



    public String selectKwh(Sender sender) {
        String query = "SELECT kwh FROM charge where customer_id=?";
        double kwh_sum=0;

        try (
                java.sql.Connection conn = Database.getConnection();
                PreparedStatement ps = conn.prepareStatement(query);

        ) {
            ps.setInt(1, Integer.parseInt(sender.getCustomerId()));
            ResultSet rs = ps.executeQuery();
            double kwh;
            while (rs.next()) {
                kwh = rs.getDouble("kwh");
                kwh_sum += kwh;

            }
            sender.setkwhsum(String.valueOf(kwh_sum));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sender.getkwh_sum();
    }


    public int getPort() {
        return PORT;
    }
}