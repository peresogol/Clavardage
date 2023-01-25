package org.example.NetworkManager;

import org.example.CustomExceptions.UsernameManagementException;
import org.example.Database.ConnectedUsers;
import org.example.Database.DatabaseMsg;
import org.example.FormatServiceMessage;
import org.example.GUI.MainWindow;
import org.example.Managers.ThreadManager;
import org.example.PendingMessage;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class NetworkManager {

    private static HashMap<InetAddress, Integer> activConversation;

    private static LinkedList<String> existingConversation;
    private static ConnectedUsers databaseConnectedUsers;
    private static MainWindow mainWindow;

    private static Queue<PendingMessage> pendingMessages;
    public static String username;
    private static DatabaseMsg db;


    public NetworkManager(String s){
        this.activConversation = new HashMap<>();
        this.databaseConnectedUsers = new ConnectedUsers();
        this.mainWindow = new MainWindow(s);
        this.pendingMessages = new LinkedList<>();
        username = s;
        db =  new DatabaseMsg();
        db.init();
        existingConversation = db.getTables();
        initConvGUI(existingConversation);
    }

    // for local test
    public NetworkManager(String s, int destPortUDP){
        this.activConversation = new HashMap<>();
        this.databaseConnectedUsers = new ConnectedUsers();
        this.mainWindow = new MainWindow(s);
        this.pendingMessages = new LinkedList<>();
        username = s;
        db =  new DatabaseMsg();
        db.init();
        existingConversation = db.getTables();
        initConvGUI(existingConversation);

        // Differ here
        ClientUDP.setDestPort(destPortUDP);
    }

    public void msgUDPhandler(DatagramPacket packet) {
        String received = new String(packet.getData(), 0, packet.getLength());
        String code = received.substring(0,3);
        InetAddress srcAddress = packet.getAddress();
        String name;
        String msg;

        //TODO à mettre dans la class launchApp

        switch (code){
            // remote demande qui est co
            case "001":
                name = received.substring(3);

                msg = FormatServiceMessage.msgIsConnected();
                // Ajoute émetteur dans la BDD des utilisateurs connectés
                this.databaseConnectedUsers.addUser(name, String.valueOf(srcAddress));
                this.mainWindow.addConnectedUser(name);
                ClientUDP.sendMessage(msg, srcAddress);
                break;

            // remote est connecté
            case "002":
                name = received.substring(3);

                // Ajoute émetteur dans la BDD des utilisateurs connectés
                this.databaseConnectedUsers.addUser(name, String.valueOf(srcAddress));
                this.mainWindow.addConnectedUser(name);
                break;

            // Remote change username
            case "003":
                try {
                    this.databaseConnectedUsers.changeUsername(String.valueOf(srcAddress), received.substring(3),"ezc"); // TODO récupérer ancien et new nom
                } catch (UsernameManagementException e) {
                    ClientUDP.sendMessage(FormatServiceMessage.msgUsernameAlreadyUsed(), srcAddress);
                }
                break;

            // remote réponds à "003" envoyé par local :  username indisponible
            case "004":
                // TODO afficher GUI "rentrez un autre username"
                break;

            // remote se déconnecte
            case "005":
                //TODO remove try catch and exception throwing
                name = received.substring(3);

                try {
                    databaseConnectedUsers.removeUser(name);
                } catch (UsernameManagementException e) {
                    throw new RuntimeException(e);
                }
                break;

            // remote demande port TCP
            case "006":
                msg = FormatServiceMessage.msgRespondPort(String.valueOf(ThreadManager.port));
                ClientUDP.sendMessage(msg, srcAddress);
                break;

            // remote indique port TCP
            case "007": // TODO ajout dans bdd locale (à créer)
                int port = Integer.parseInt(received.substring(3));
                PendingMessage toSend = this.pendingMessages.remove();
                if(toSend.getAddr().equals(srcAddress)) {
                    String username = databaseConnectedUsers.getUsername(String.valueOf(srcAddress));
                    ClientTCP.sendMessage(toSend.getMsg(), srcAddress, port, username);
                }
                activConversation.put(srcAddress, port);
                //TODO else catch exception
                break;
            default:
                System.out.println("Error, unknown control code : " + code);
        }
    }


    public void initConvGUI(LinkedList<String> existingConversation){
        for(String s : existingConversation){
            System.out.println(s);
            mainWindow.addConversations(s);
        }
    }



    public static void handleConversation(String msg, String username){

        if(existingConversation.isEmpty() || !existingConversation.contains(username)){
            existingConversation.add(username);
            db.createTableMessage(username);
            mainWindow.addConversations(username);
        }

        db.insertMessage(username, msg, 2);


    }


    public static void sendMessage(String msg, String username){

        InetAddress address;

        handleConversation(msg, username);

        try {
            // Resolve address based on username
            address = InetAddress.getByName(databaseConnectedUsers.getAddress(username).substring(1));
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        System.out.println("adr : " + address + " msg : " + msg);

        int port;
        if(activConversation.containsKey(address)){
            port = activConversation.get(address);
            System.out.println("looking for port");
            ClientTCP.sendMessage(msg, address, port, username);
        } else {
            pendingMessages.add(new PendingMessage(address, msg));
            ClientUDP.sendMessage(FormatServiceMessage.msgAskPort(), address);
            System.out.println("send udp");
        }
    }

    // Wrapper to allow insertion of message for external class (clientTCP)
    public static void MessageSent(String username, String msg){
        db.insertMessage(username, msg, 4);

        java.util.Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        if(mainWindow.getDest().equals(username)){
            mainWindow.displayMessage(msg, dateFormat.format(date), 4);
        }
    }

    public static String getUsernameFromAddress(String address){
        return databaseConnectedUsers.getUsername(address);
    }

    public static void getMessages(String username) {
        db.selectMessages(username);
    }

    public static void displayMessage(String msg, String date, int center){
        mainWindow.displayMessage(msg, date, center);
    }

}
