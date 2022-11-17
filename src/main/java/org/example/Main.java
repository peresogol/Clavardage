package org.example;

import org.example.NetworkManager.ClientUDP;
import org.example.NetworkManager.ServerUDP;

import java.net.SocketException;

public class Main {

    public static void main(String[] args) {
        ClientUDP client;
        ServerUDP serv;
        String echo;

        try {
            serv = new ServerUDP();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        serv.start();

        try {
            client = new ClientUDP();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        /*
        echo = client.sendEcho("hello server");
        System.out.println(echo);
        echo = client.sendEcho("server is working");
        System.out.println(echo);
        */

        client.close();

    }
}