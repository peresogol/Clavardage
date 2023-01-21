package org.example.NetworkManager;

import org.example.CustomExceptions.UsernameManagementException;
import org.example.Database.ConnectedUsers;
import org.example.FormatServiceMessage;
import org.example.GUI.MainWindow;
import org.example.Managers.ThreadManager;
import org.example.PendingMessage;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class NetworkManager {

    private static HashMap<InetAddress, Integer> activConversation;

    private static ConnectedUsers databaseConnectedUsers;
    private MainWindow mainWindow;

    private static Queue<PendingMessage> pendingMessages;
    public static String username;


    public NetworkManager(String s){
        this.activConversation = new HashMap<>();
        this.databaseConnectedUsers = new ConnectedUsers();
        this.mainWindow = new MainWindow(s);
        this.pendingMessages = new LinkedList<>();
        username = s;
    }

    // for local test
    public NetworkManager(String s, int destPortUDP){
        this.activConversation = new HashMap<>();
        this.databaseConnectedUsers = new ConnectedUsers();
        this.mainWindow = new MainWindow(s);
        this.pendingMessages = new LinkedList<>();
        username = s;
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

                System.out.println("Ajoute 001  : " + name);

                msg = FormatServiceMessage.msgIsConnected();
                // Ajoute émetteur dans la BDD des utilisateurs connectés
                this.databaseConnectedUsers.addUser(name, String.valueOf(srcAddress));
                this.mainWindow.addConnectedUser(name);
                ClientUDP.sendMessage(msg, srcAddress);
                break;

            // remote est connecté
            case "002":
                name = received.substring(3);

                System.out.println("Ajoute 002  : " + name);

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
                System.out.println("remote asking for port");
                ClientUDP.sendMessage(msg, srcAddress);
                break;

            // remote indique port TCP
            case "007": // TODO ajout dans bdd locale (à créer)
                int port = Integer.parseInt(received.substring(3));
                PendingMessage toSend = this.pendingMessages.remove();
                System.out.println("007 port : " + port + " addr : " + srcAddress + " " + toSend.getAddr());
                if(toSend.getAddr().equals(srcAddress)) {
                    ClientTCP.sendMessage(toSend.getMsg(), srcAddress, port);
                }
                activConversation.put(srcAddress, port);
                //TODO else catch exception
                break;
            default:
                System.out.println("Error, unknown control code : " + code);
        }
    }


    public static void sendMessage(String msg, String username){

        InetAddress address;

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
            ClientTCP.sendMessage(msg, address, port);
        } else {
            pendingMessages.add(new PendingMessage(address, msg));
            ClientUDP.sendMessage(FormatServiceMessage.msgAskPort(), address);
            System.out.println("send udp");
        }
    }

}
