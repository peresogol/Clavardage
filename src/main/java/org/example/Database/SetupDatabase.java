package org.example.Database;

import java.sql.*;

public class SetupDatabase {

    private static final String url = "jdbc:sqlite:SqliteJavaDB.db";

    public static void createNewDatabase() {


            try (Connection conn = DriverManager.getConnection(url)) {
                if (conn != null) {
                    DatabaseMetaData meta = conn.getMetaData();
                    System.out.println("The driver name is " + meta.getDriverName());
                    System.out.println("A new database has been created.");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    public static void createTableMessage(String tableName) {

        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " id integer PRIMARY KEY, message text NOT NULL, horodatage real;";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        createNewDatabase();
    }

    public String getUrl() {
        return url;
    }
}

