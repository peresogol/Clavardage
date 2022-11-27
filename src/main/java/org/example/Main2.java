package org.example;

import org.example.NetworkManager.ClientTCP;
import org.example.NetworkManager.NetworkManager;
import org.example.NetworkManager.ServerUDP;
import org.example.NetworkManager.ServeurTCP;

public class Main2 {

    public static void main(String[] args) {

        NetworkManager n = new NetworkManager();
        n.sendMessage("Hello je suis le client");
        /*
        ServerUDP s = new ServerUDP(5555);
        s.start();
        */
    }
}