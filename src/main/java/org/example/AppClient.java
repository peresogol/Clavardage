package org.example;

import org.example.NetworkManager.ClientUDP;
import org.example.NetworkManager.NetworkManager;

public class AppClient {

    public static void main(String[] args) {

        NetworkManager n = new NetworkManager();
        n.sendMessage("Hello je suis le client");

        //ClientUDP.sendBroadcast("AreYouOk");

    }
}