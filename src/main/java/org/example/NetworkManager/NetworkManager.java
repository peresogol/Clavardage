package org.example.NetworkManager;

import org.example.CustomExceptions.UsernameManagementException;
import org.example.Database.ConnectedUsers;
import org.example.FormatServiceMessage;
import org.example.GUI.MainWindow;
import org.example.Launcher;
import org.example.Managers.ThreadManager;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.LinkedList;

public class NetworkManager {

    private LinkedList<InetAddress> activConversation;
    private ConnectedUsers databaseConnectedUsers;
    private MainWindow mainWindow;

    public static String username;


    public NetworkManager(String s){
        this.activConversation = new LinkedList<>();
        this.databaseConnectedUsers = new ConnectedUsers();
        this.mainWindow = new MainWindow(s);
        username = s;
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
                this.databaseConnectedUsers.addUser(String.valueOf(srcAddress), name);
                this.mainWindow.addConnectedUser(name);
                ClientUDP.sendMessage(msg, srcAddress);
                break;

            // remote est connecté
            case "002":
                name = received.substring(3);

                System.out.println("Ajoute 002  : " + name);

                // Ajoute émetteur dans la BDD des utilisateurs connectés
                this.databaseConnectedUsers.addUser(String.valueOf(srcAddress), name);
                this.mainWindow.addConnectedUser(name);
                break;

            // Remote change username
            case "003":
                try {
                    this.databaseConnectedUsers.changeUsername(String.valueOf(srcAddress), received.substring(3));
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
                try {
                    databaseConnectedUsers.removeUser(String.valueOf(srcAddress));
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
                int port = Integer.parseInt(received.substring(4));
                ClientTCP.sendMessage("azeca", srcAddress, port); // TODO msg must be real
                break;
            default:
                System.out.println("Error, unknown control code : " + code);
        }
    }



    public void sendMessage(String msg, InetAddress address, int port){
        if(activConversation.contains(address)){
            ClientUDP.sendMessage(FormatServiceMessage.msgAskPort(), address);
        } else {
            // TODO : stocker et récupérer port et adresse
            ClientTCP.sendMessage(msg, address, port);
        }
    }

}
