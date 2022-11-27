package org.example.NetworkManager;

import org.example.FormatServiceMessage;

import java.text.Normalizer;

public class NetworkManager {

    public void sendMessage(String msg){
        ClientTCP c = new ClientTCP();
        c.sendMessage(msg);
    }

    public static void discoverNetwork(){
        String msg = FormatServiceMessage.MsgGetConnectedUsers();
        ClientUDP.sendBroadcast(msg);
    }

    public static void respondToNetworkDiscovery() {
        String msg = FormatServiceMessage.MsgIsConnected();
        ClientUDP.sendBroadcast(msg);
    }
}
