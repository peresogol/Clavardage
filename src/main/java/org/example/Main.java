package org.example;

import org.example.NetworkManager.ClientUDP;

public class Main {

    public static void main(String[] args) {

        ClientUDP c = new ClientUDP();
        c.sendBroadcast("TEST msg accentué");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        c.sendBroadcast("TEST msg accentué 2");
    }
}