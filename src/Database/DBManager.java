package Database;

import java.sql.*;
import java.util.List;

public class DBManager {
    public static String url;

    public DBManager(String url_given) {
        DBManager.url = url_given;
    }

    public static void createWeatherTable() {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE IF NOT EXISTS weather (\n")
                .append("fetchedAt INTEGER NOT NULL,\n")
                .append("city VARCHAR(100) NOT NULL,\n")
                .append("current_temperature DOUBLE NOT NULL,\n")
                .append("wind_speed DOUBLE,\n")
                .append("PRIMARY KEY (fetchedAt, city)\n")
                .append(");\n");

        // Printing the query to be sure of the syntax
        System.out.println(query);

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                Statement s = conn.createStatement();
                s.execute(query.toString());
                System.out.println("Table successfully created");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertValues(List<Object> list_values) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO weather(\n")
                .append("fetchedAt,\n")
                .append("city,\n")
                .append("current_temperature,\n")
                .append("wind_speed\n")
                .append(")\n")
                .append("VALUES(?,?,?,?)");

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                PreparedStatement s = conn.prepareStatement(query.toString());
                s.setInt(1, (int) list_values.get(0));
                s.setString(2, (String) list_values.get(1));
                s.setDouble(3, (double) list_values.get(2));
                s.setDouble(4, (double) list_values.get(3));
                s.executeUpdate();
                System.out.println("Values have been added to database");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dropTable() {
        String query = "DROP TABLE IF EXISTS weather";

        try {
            Connection conn = DriverManager.getConnection(url);
            Statement s = conn.createStatement();
            s.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}