package org.example.Database;

import java.sql.*;

public class Database {

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

    // Créer table nommé en fonction de l'adresse IP de la personne correspondant à la conversation
    public static void createTableMessage(String ipAddress) {

        String query = "CREATE TABLE IF NOT EXISTS " + ipAddress + " message text NOT NULL, horodatage bigint;";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
                stmt.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertMessage(String ipAdresse, String msg){
        //int id = 0; // TODO define id -> on fait un select, si rien id = 0 sinon id = dernier id + 1
        long epoch = System.currentTimeMillis();
        String query = "INSERT INTO " + ipAdresse + " VALUES(" + msg + ", " + epoch + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void selectMessages(String ipAddress){
        String query = "SELECT msg, horodatege FROM " + ipAddress + " ORDER BY horodatage";

        try(Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)
        ) {
            while(rs.next()){
                System.out.print("Message: " + rs.getString("msg"));
                System.out.print(", Date et heure : " + rs.getLong("horodatage"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return url;
    }
}

