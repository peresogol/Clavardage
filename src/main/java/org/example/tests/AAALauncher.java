package org.example.tests;

import org.example.utils.FormatServiceMessage;
import org.example.Managers.ThreadManager;
import org.example.NetworkManager.ClientUDP;
import org.example.NetworkManager.NetworkManager;
import org.example.NetworkManager.ServerUDP;

public class AAALauncher {

    private static ServerUDP serv;
    public static NetworkManager networkManager;
    private static ThreadManager threadManager;

    public static void main(String[] args) {

        // Start Database
        // Start Connected Users
        threadManager = new ThreadManager(5500);
        threadManager.start();
        networkManager = new NetworkManager("AAA", 6000);
        startUDPServer();
    }


    // Start Server UDP
    public static void startUDPServer(){
        serv = new ServerUDP(5000, packet -> networkManager.msgUDPhandler(packet));
        serv.start();
        discoverNetwork();
    }

    // Send broadcast to discover network
    public static void discoverNetwork(){
        String msg = FormatServiceMessage.msgGetConnectedUsers();
        ClientUDP.sendBroadcast(msg);
    }
}
