package org.example.NetworkManager;

import org.example.CustomExceptions.UsernameManagementException;
import org.example.Database.ConnectedUsers;
import org.example.Database.DatabaseMsg;
import org.example.GUI.AlertWindow;
import org.example.utils.FormatServiceMessage;
import org.example.GUI.MainWindow;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;


public class NetworkManager {


    private static LinkedList<String> existingConversation;
    private static ConnectedUsers databaseConnectedUsers;
    private static MainWindow mainWindow;

    public static String username, nextUsername;
    private static DatabaseMsg db;


    public NetworkManager(String s){
        this.databaseConnectedUsers = new ConnectedUsers();
        this.mainWindow = new MainWindow(s);
        username = s;
        db =  new DatabaseMsg();
        db.init();
        existingConversation = db.getTables();
        initConvGUI(existingConversation);
    }

    // for local test, ajout d'un deuxième paramètre
    public NetworkManager(String s, int destPortUDP){
        this.databaseConnectedUsers = new ConnectedUsers();
        this.mainWindow = new MainWindow(s);
        username = s;
        db =  new DatabaseMsg();
        db.init();
        existingConversation = db.getTables();
        initConvGUI(existingConversation);

        // Differ here
        ClientUDP.setDestPort(destPortUDP);
    }

    /*
    Handler pour les différents messages UDP qu'il est possible de recevoir.
    Codes (du point de vue de la source):
    001: broadcast pour savoir qui est connecté
    002: réponse à 001
    003: broadcast pour diffuser un nouveau username
    004: réponse à 003 (refus ou ok)
    005: broadcast pour prévenir de la déconnection d'un utilisateur
     */
    public void msgUDPhandler(DatagramPacket packet) {
        String name;
        String msg;
        String received = new String(packet.getData(), 0, packet.getLength());
        String code = received.substring(0,3);
        InetAddress srcAddress = packet.getAddress();


        //TODO à mettre dans la class launchApp

        switch (code){
            // remote demande qui est co
            case "001":
                name = received.substring(3);

                if(this.databaseConnectedUsers.contains(name)){
                    // username already in use
                    msg = FormatServiceMessage.msgUsernameKO();
                } else {
                    // Ajoute émetteur dans la BDD des utilisateurs connectés
                    msg = FormatServiceMessage.msgUsernameOK();
                    this.databaseConnectedUsers.addUser(name, String.valueOf(srcAddress));
                    this.mainWindow.addConnectedUser(name);
                }
                ClientUDP.sendMessage(msg, srcAddress);
                break;

            // remote est connecté mais username déjà utilisé
            case "002":
                name = received.substring(3);
                // Ajoute émetteur dans la BDD des utilisateurs connectés
                if(!this.databaseConnectedUsers.contains(name)){
                    this.databaseConnectedUsers.addUser(name, String.valueOf(srcAddress));
                    this.mainWindow.addConnectedUser(name);
                }
                this.askForNewUsername();
                break;

            // remote est connecté et username ok
            case "003":
                name = received.substring(3);
                // Ajoute émetteur dans la BDD des utilisateurs connectés
                if(!this.databaseConnectedUsers.contains(name)){
                    this.databaseConnectedUsers.addUser(name, String.valueOf(srcAddress));
                    this.mainWindow.addConnectedUser(name);
                }
                break;

            // remote partage nouveau username
            case "004":
                int index = received.indexOf(':');
                name = received.substring(3, index);
                String newUSername = received.substring(index+1);

                try {
                    if(this.databaseConnectedUsers.contains(name)){
                        this.databaseConnectedUsers.changeUsername(String.valueOf(srcAddress), name, newUSername);
                    }
                    db.updateTable(name, newUSername);
                } catch (UsernameManagementException e) {
                    ClientUDP.sendMessage(FormatServiceMessage.msgUsernameKO(), srcAddress);
                }
                break;

            // remote se déconnecte
            case "005":
                name = received.substring(3);

                try {
                    databaseConnectedUsers.removeUser(name);
                    //TODO mise à jour GUI
                } catch (UsernameManagementException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                System.out.println("Error, unknown control code : " + code);
        }
    }

    private void askForNewUsername() {
    }


    /*
    Ajoute un bouton par conversation trouvée dans la BDD
     */
    public void initConvGUI(LinkedList<String> existingConversation){
        for(String s : existingConversation){
            mainWindow.addConversations(s);
        }
    }


    /*
    Handler pour les messages reçus depuis l'un des serveurs TCP. Insert le message reçu dans la BDD (créé une table si nécessaire), puis l'affiche dans le GUI.
     */
    public static void handleReceivedMessage(String msg, String username){
        java.util.Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        createTable(username);
        db.insertMessage(username, msg, 2);

        if(mainWindow.getDest().equals(username)){
            mainWindow.displayMessage(msg, dateFormat.format(date), 2);
        }
    }


    /*
    Envoie un message en utilisant la méthode d'envoie définie dans ClientTCP. Si une conversation avec l'utilisateur distant existe déjà, le port associé à cette
    conversation est récupéré, puis réutilisé. Dans le cas contraire, envoie d'une requête UDP afin de connaitre le prochain port disponible de l'utilisateur
    distant pour ouvrir une connection TCP. Le message est ajouté à une queue, et sera envoyé lors de la réception d'une réponse de la part de l'utilisateur distant.
     */
    public static void sendMessage(String msg, String username){
        InetAddress address;
        int port;

       // messageSent(msg, username);

        try {
            // Resolve address based on username
            address = InetAddress.getByName(databaseConnectedUsers.getAddress(username).substring(1));
            if(username.equals("BBB")){
                ClientTCP.sendMessage(msg, address, 6500, username);
            } else if(username.equals("AAA")){
                ClientTCP.sendMessage(msg, address, 5500, username);
            } else {
                ClientTCP.sendMessage(msg, address, 5555, username);
            }
        } catch (UnknownHostException e) {
            new AlertWindow("Il semble que cet utilisateur ne soit pas connecté.");
        }
    }

     /*
     Wrapper to allow insertion of message in database from external class (clientTCP) once the message has been sent
      */
    public static void messageSent(String msg, String username){
        java.util.Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        System.out.println(username + " " + msg);
        createTable(username);
        db.insertMessage(username, msg, 4);

        System.out.println("Message sent " + msg );
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

    /*
    Wrapper pour appeler la méthode d'affichage des messages de la classe MainWindow
     */
    public static void displayMessage(String msg, String date, int center){
        mainWindow.displayMessage(msg, date, center);
    }

    /*
    Sur envoie ou réception d'un nouveau message, créé une table associé à la conversation si elle n'existe pas déjà et ajoute un bouton conversation sur
    le côté gauche du panel
     */
    public static void createTable(String username) {
        if(existingConversation.isEmpty() || !existingConversation.contains(username)){
            existingConversation.add(username);
            db.createTableMessage(username);
            mainWindow.addConversations(username);
        }
    }
}
