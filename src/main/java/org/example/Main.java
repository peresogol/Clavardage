package org.example;

import org.example.NetworkManager.ClientUDP;
import org.example.NetworkManager.ServerUDP;

public class Main {

    public static void main(String[] args) {
        ClientUDP client;
        ServerUDP serv;
        String echo;

        try {
            serv = new ServerUDP();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        serv.start();

        try {
            client = new ClientUDP();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        echo = client.send("hello server");
        System.out.println(echo);
        echo = client.send("server is working");
        System.out.println(echo);

        client.close();

    }
}