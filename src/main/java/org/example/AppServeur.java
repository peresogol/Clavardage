package org.example;

import org.example.NetworkManager.ServerUDP;

public class AppServeur {

    public static void main(String[] args) {
/*
        NetworkManager n = new NetworkManager();
        n.sendMessage("Hello je suis le client");


        */
        ServerUDP s = new ServerUDP(5555, packet -> System.out.println(packet));
        s.start();

    }
}