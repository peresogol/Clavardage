package org.example;

import org.example.NetworkManager.ClientUDP;
import org.example.NetworkManager.NetworkManager;
import org.example.NetworkManager.ServerUDP;

public class Launcher {

    private ServerUDP serv;


    public static void main(String[] args) {

        // Start Database
        // Start Connected Users
        // Start GUI
        // Start ThreadManager

    }

    // Start Server UDP
    public void startUDPServer(){
        serv = new ServerUDP(5555, packet -> NetworkManager.msgUDPhandler(packet));
        serv.start();
    }

    // Send broadcast to discover network
    public static void discoverNetwork(){
        String msg = FormatServiceMessage.msgGetConnectedUsers();
        ClientUDP.sendBroadcast(msg);
    }
}
