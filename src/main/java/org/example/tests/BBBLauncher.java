package org.example.tests;

import org.example.FormatServiceMessage;
import org.example.Managers.ThreadManager;
import org.example.NetworkManager.ClientUDP;
import org.example.NetworkManager.NetworkManager;
import org.example.NetworkManager.ServerUDP;

public class BBBLauncher {

    private static ServerUDP serv;
    private static NetworkManager networkManager;
    private static ThreadManager threadManager;

    public static void main(String[] args) {

        // Start Database
        // Start Connected Users
        threadManager = new ThreadManager(6500);
        threadManager.start();
        networkManager = new NetworkManager("BBB", 5000);
        startUDPServer();
    }


    // Start Server UDP
    public static void startUDPServer(){
        serv = new ServerUDP(6000, packet -> networkManager.msgUDPhandler(packet));
        serv.start();
        discoverNetwork();
    }

    // Send broadcast to discover network
    public static void discoverNetwork(){
        String msg = FormatServiceMessage.msgGetConnectedUsers();
        ClientUDP.sendBroadcast(msg);
    }
}
