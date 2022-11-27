package org.example;

public class Main {

    public static void main(String[] args) {

        ThreadManager t = new ThreadManager();
        t.start();

        /*
        ClientUDP c = new ClientUDP();
        c.sendBroadcast("TEST msg accentué");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        c.sendBroadcast("TEST msg accentué 2");
         */
    }
}