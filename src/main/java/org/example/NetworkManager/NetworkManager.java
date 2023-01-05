package org.example.NetworkManager;

import org.example.CustomExceptions.UsernameManagementException;
import org.example.Database.ConnectedUsers;
import org.example.FormatServiceMessage;

import java.net.DatagramPacket;

public class NetworkManager {

    private ServerUDP serv;

    //TODO à mettre dans la classe LaunchApp
    public void startUDPServer(){
        serv = new ServerUDP(5555, packet -> msgUDPhandler(packet));
        serv.start();
    }

    private void msgUDPhandler(DatagramPacket packet) {
        String received = new String(packet.getData(), 0, packet.getLength());
        String code = received.substring(0,3);

        //TODO à mettre dans la class launchApp
        ConnectedUsers databaseConnectedUsers = new ConnectedUsers();

        switch (code){
            case "001":
                String msg = FormatServiceMessage.msgIsConnected();
                ClientUDP.sendBroadcast(msg);
                break;
            case "002":
                databaseConnectedUsers.addUser(String.valueOf(packet.getAddress()), received.substring(4));
                break;
            case "003":
                //TODO maj db change username
                break;
            case "004":
                //TODO remove try catch and exception throwing
                try {
                    databaseConnectedUsers.removeUser(String.valueOf(packet.getAddress()));
                } catch (UsernameManagementException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                System.out.println("Error, unknown control code : " + code);
        }
    }

    public void sendMessage(String msg){
        ClientTCP.sendMessage(msg);
    }

    //TODO call this method in launchApp class
    public static void discoverNetwork(){
        String msg = FormatServiceMessage.msgGetConnectedUsers();
        ClientUDP.sendBroadcast(msg);
    }

}
