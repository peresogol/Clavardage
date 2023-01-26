package org.example.Database;

import org.example.NetworkManager.NetworkManager;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class DatabaseMsg {

    private static final String url = "jdbc:sqlite:SqliteJavaDB.db";

    public void init() {
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
    public void createTableMessage(String username) {

        String query = "CREATE TABLE IF NOT EXISTS " + username + "(message varchar(2000) NOT NULL, horodatage varchar(100) NOT NULL, center integer NOT NULL);";

        System.out.println(query);
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();) {
                stmt.execute(query);
        } catch (SQLException e) {
            System.out.println("create table " + e.getMessage());
        }
    }

    public void insertMessage(String username, String msg, int center){
        java.util.Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String query = "INSERT INTO " + username + " VALUES (\"" + msg + "\", \"" + dateFormat.format(date) +"\", " + center + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println("insert " + e.getMessage());
        }
    }

    public void selectMessages(String username){
        String query = "SELECT * FROM " + username;

        try(Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)
        ) {
            while(rs.next()){
                NetworkManager.displayMessage(rs.getString("message"), rs.getString("horodatage"), rs.getInt("center"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updateTable(String oldUsername, String newUsername){
        String query = "ALTER TABLE " + oldUsername + " RENAME To " + newUsername;

        try(Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
        ) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    Retourne une liste des tables existantes (on enlève la table sqlite_schema)
     */
    public LinkedList<String> getTables(){

        String  tmp;
        LinkedList<String> table = new LinkedList();

        try (Connection conn = DriverManager.getConnection(url)){
             DatabaseMetaData md = conn.getMetaData();
             ResultSet rs = md.getTables(null, null, "%", null);
             while (rs.next()) {
                 tmp = rs.getString(3);
                 if(!tmp.equals("sqlite_schema")){
                    table.add(tmp);
                 }
             }
        } catch (SQLException e) {
            System.out.println("getTable " + e.getMessage());
        }
        return table;
    }
}

