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

    // for local test, ajout d'un deuxième paramètre
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

    /*
    Handler pour les différents messages UDP qu'il est possible de recevoir.
    Codes (du point de vue de la source):
    001: broadcast pour savoir qui est connecté
    002: réponse à 001
    003: broadcast pour diffuser un nouveau username
    004: réponse à 003 (refus ou ok)
    005: broadcast pour prévenir de la déconnection d'un utilisateur
    006: requête pour connaître le numéro du prochain port disponible pour initier une connection TCP
    007: réponse à 006
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
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }


        if(activConversation.containsKey(address)){
            port = activConversation.get(address);
            ClientTCP.sendMessage(msg, address, port, username);
        } else {
            pendingMessages.add(new PendingMessage(address, msg));
            ClientUDP.sendMessage(FormatServiceMessage.msgAskPort(), address);
        }
    }

     /*
     Wrapper to allow insertion of message in database from external class (clientTCP) once the message has been sent
      */
    public static void messageSent(String msg, String username){
        java.util.Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

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
