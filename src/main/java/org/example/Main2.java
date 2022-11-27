package org.example;

import org.example.NetworkManager.ServerUDP;

public class Main2 {

    public static void main(String[] args) {

        ServerUDP s = new ServerUDP(5555);
        s.start();

    }
}