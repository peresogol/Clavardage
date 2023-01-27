package org.example.tests;

import org.example.GUI.EnterUsername;
import org.example.Managers.ThreadManager;
import org.example.NetworkManager.ClientUDP;
import org.example.NetworkManager.NetworkManager;
import org.example.NetworkManager.ServerUDP;
import org.example.utils.FormatServiceMessage;

public class Launcher {

    private static ServerUDP serv;
    public static NetworkManager networkManager;
    private static ThreadManager threadManager;

    public static void main(String[] args) {
        new EnterUsername("Veuillez saisir un nom d'utilisateur :");
    }

    public void startApp(String text) {
        threadManager = new ThreadManager(5500);
        threadManager.start();
        networkManager = new NetworkManager(text, 6000);
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
