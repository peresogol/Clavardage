package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static int portNumber = 0;
    public static ServerSocket  serverSocket = null;

    public static void main(String[] args) {
        Socket socket = null;
        socket = new Socket();

        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
        acceptClients();
    }

    public static void acceptClients() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
            } catch (IOException e){
                System.out.println("Erreur acceptClients sur " +portNumber);
            }
        }
    }
}
