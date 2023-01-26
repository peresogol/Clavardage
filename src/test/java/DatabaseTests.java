import org.example.Database.DatabaseMsg;
import org.example.NetworkManager.ClientUDP;
import org.example.NetworkManager.NetworkManager;
import org.example.NetworkManager.ServerUDP;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.sql.*;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class DatabaseTests {

    private DatabaseMsg db;
    private static final String url = "jdbc:sqlite:SqliteJavaDB.db";

    @Before
    public void initTestEnvironment() throws Exception {
        String query = "DROP TABLE ";

        db = new DatabaseMsg();
        db.init();
        LinkedList<String> l = db.getTables();

        for(String table : l) {
            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement();
            ) {
                stmt.executeUpdate(query + table);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    public void testCreateTable() {
            db.createTableMessage("user1");
            LinkedList l = db.getTables();
            assertTrue(l.contains("user1"));
    }


    @Test
    public void testInsert() throws Exception {
        String msg = "message";
        String user = "user2";
        int center = 4;

        String date = null, msgInserted = null;
        int centerInserted = 0;

        db.createTableMessage(user);
        db.insertMessage(user, msg, center);

        String query = "SELECT * FROM " + user;

        try(Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)
        ) {
            while(rs.next()){
                msgInserted = rs.getString("message");
                date = rs.getString("horodatage");
                centerInserted = rs.getInt("center");
            }
        }

        assertEquals(msg, msgInserted);
        assertEquals(center, centerInserted);
        assertNotNull(date);
    }

    @Test
    public void testUpdate() throws SQLException {
        String user = "user3";

        db.createTableMessage(user);
        db.updateTable(user, "jose");

        LinkedList l = db.getTables();
        assertTrue(l.contains("jose"));
        assertFalse(l.contains(user));
    }


}
