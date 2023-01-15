package org.example;

import org.example.NetworkManager.ClientUDP;
import org.example.NetworkManager.NetworkManager;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class AppClient {

    public static void main(String[] args) throws UnknownHostException {

        NetworkManager n = new NetworkManager();
        n.sendMessage("Hello je suis le client", InetAddress.getByName("127.0.0.1"), 4444);

        //ClientUDP.sendBroadcast("AreYouOk");

    }
}