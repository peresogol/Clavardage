package org.example.Managers;

import org.example.NetworkManager.ServeurTCP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// NetworkManager plutôt ???

public class ThreadManager extends Thread {

    public void run() {

        ServerSocket serveurSocket;
        Socket socket;

        int port = 4444;
        while (true) {
            System.out.println("a");
            try {
                serveurSocket = new ServerSocket(port++);
                socket = serveurSocket.accept();
                new ServeurTCP(socket, serveurSocket).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
