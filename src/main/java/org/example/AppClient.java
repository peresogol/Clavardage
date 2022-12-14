package org.example;

import org.example.Database.SetupDatabase;
import org.example.NetworkManager.ClientUDP;

public class AppClient {

    public static void main(String[] args) {
        /*
        ThreadManager t = new ThreadManager();
        t.start();
        */
        ClientUDP.sendBroadcast("AreYouOk");

    }
}