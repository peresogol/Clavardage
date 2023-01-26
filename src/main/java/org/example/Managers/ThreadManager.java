package org.example.Managers;

import org.example.NetworkManager.ServeurTCP;
import org.example.poubelle.EchoThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


// NetworkManager plutôt ???

public class ThreadManager extends Thread {

    public static int port;

    /*
    Classe chargée de la création de thread de type ServerTCP. Chaque thread créé est alloué à la réception des messages d'une seule conversation
     */
    public ThreadManager(int p){
        port = p;
    }

    public void run() {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (Exception e) {
                System.out.println("Error Thread Manager: " + e);
            }
            // new thread for a client
            new ServeurTCP(socket).start();
        }
    }
    /*
    public void run() {

        ServerSocket serveurSocket;
        Socket socket;

        try {
            serveurSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        while (true) {
            try {
                System.out.println("listening on port " + port);

                // Fonction bloquante en attente d'une requête de connexion
                socket = serveurSocket.accept();

                // Créer un nouveau thread dédié à une conversation donnée
                new ServeurTCP(socket, serveurSocket).start();
                port++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
*/
}
