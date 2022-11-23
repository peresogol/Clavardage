package org.example;

import org.example.NetworkManager.ClientUDP;
import org.example.NetworkManager.ServerUDP;

import java.net.SocketException;
import java.net.UnknownHostException;

public class Main2 {

    public static void main(String[] args) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                ClientUDP c;
                try {
                    c = new ClientUDP(4443);
                    c.start();
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        });


        ServerUDP serv;
        String echo;

        try {
            serv = new ServerUDP(4444);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        serv.start();

        t.start();

    }
}