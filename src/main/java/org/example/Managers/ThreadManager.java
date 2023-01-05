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
            try {
                serveurSocket = new ServerSocket(port);
                port = port + 1;

                // Fonction bloquante en attente d'une requête de connexion
                socket = serveurSocket.accept();

                // Créer un nouveau thread dédié à une conversation donnée
                new ServeurTCP(socket, serveurSocket).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
