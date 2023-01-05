package org.example;

import org.example.Managers.ThreadManager;
import org.example.NetworkManager.ServerUDP;

public class AppServeur {

    public static void main(String[] args) {

        ThreadManager t = new ThreadManager();
        t.start();

        //ServerUDP s = new ServerUDP(5555, packet -> System.out.println(packet));
       //s.start();

    }
}