package org.example;

import org.example.GUI.MainWindow;
import org.example.NetworkManager.ClientUDP;
import org.example.NetworkManager.NetworkManager;
import org.example.NetworkManager.ServerUDP;

public class Launcher2 {

    private static ServerUDP serv;
    private static NetworkManager networkManager;

    public static void main(String[] args) {

        // Start Database
        // Start Connected Users
        // Start ThreadManager
        networkManager = new NetworkManager("BBB");
        startUDPServer();
    }


    // Start Server UDP
    public static void startUDPServer(){
        serv = new ServerUDP(6666, packet -> networkManager.msgUDPhandler(packet));
        serv.start();
        discoverNetwork();
    }

    // Send broadcast to discover network
    public static void discoverNetwork(){
        String msg = FormatServiceMessage.msgGetConnectedUsers();
        ClientUDP.sendBroadcast(msg);
    }
}
