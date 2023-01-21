package org.example.Managers;

import org.example.NetworkManager.ServeurTCP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// NetworkManager plutôt ???

public class ThreadManager extends Thread {

    public static int port;

    public ThreadManager(int p){
        port = p;
    }

    public void run() {

        ServerSocket serveurSocket;
        Socket socket;

        while (true) {
            try {
                serveurSocket = new ServerSocket(port);
                System.out.println("listening on port " + port);
                // Fonction bloquante en attente d'une requête de connexion
                socket = serveurSocket.accept();
                System.out.println("accept scopkcet");
                // Créer un nouveau thread dédié à une conversation donnée
                new ServeurTCP(socket, serveurSocket).start();
                port++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
