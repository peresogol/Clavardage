package org.example;

import org.example.NetworkManager.ClientUDP;
import org.example.NetworkManager.ServerUDP;

import java.net.SocketException;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                ClientUDP c;
                try {
                    c = new ClientUDP();
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
            serv = new ServerUDP();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        serv.start();

        t.start();
    /*
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
    */
    }
}