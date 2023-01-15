package org.example.NetworkManager;

import org.example.CustomExceptions.UsernameManagementException;
import org.example.Database.ConnectedUsers;
import org.example.FormatServiceMessage;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.LinkedList;

public class NetworkManager {

    private static LinkedList<InetAddress> activConversation = new LinkedList<>();

    public static void sendMessage(String msg, InetAddress address, int port){
        if(activConversation.contains(address)){
            ClientUDP.sendMessage(FormatServiceMessage.msgAskPort(), address);
        } else {
            // TODO : stocker et récupérer port et adresse
            ClientTCP.sendMessage(msg, address, port);
        }
    }

    public static void msgUDPhandler(DatagramPacket packet) {
        String received = new String(packet.getData(), 0, packet.getLength());
        String code = received.substring(0,3);
        InetAddress srcAddress = packet.getAddress();

        //TODO à mettre dans la class launchApp
        ConnectedUsers databaseConnectedUsers = new ConnectedUsers();

        switch (code){
            case "001":
                String msg = FormatServiceMessage.msgIsConnected();
                ClientUDP.sendBroadcast(msg);
                break;
            case "002":
                databaseConnectedUsers.addUser(String.valueOf(srcAddress), received.substring(4));
                break;
            case "003":
                try {
                    databaseConnectedUsers.changeUsername(String.valueOf(srcAddress), received.substring(4));
                } catch (UsernameManagementException e) {
                    ClientUDP.sendMessage(FormatServiceMessage.msgUsernameAlreadyUsed(), srcAddress);
                }
                break;
            case "004":
                //TODO peux pas changer username
                break;
            case "005":
                //TODO remove try catch and exception throwing
                try {
                    databaseConnectedUsers.removeUser(String.valueOf(srcAddress));
                } catch (UsernameManagementException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "006":
                ClientUDP.sendMessage("azec", srcAddress);
                break;
            case "007": // TODO ajout dans bdd locale (à créer)
                int port = Integer.parseInt(received.substring(4));
                ClientTCP.sendMessage("test", srcAddress, port);
                break;
            default:
                System.out.println("Error, unknown control code : " + code);
        }
    }
}
