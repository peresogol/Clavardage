package org.example.NetworkManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


public class ClientTCP {

    public static void sendMessage(String msg, InetAddress address, int port, String username) {

        final Socket clientSocket;
        final PrintWriter out;

        try {
            System.out.println(address + ":" + port);
            clientSocket = new Socket(address, port);
            out = new PrintWriter(clientSocket.getOutputStream());

            out.println(msg);
            out.flush();
            clientSocket.close();

            NetworkManager.messageSent(msg, username);;
        } catch (IOException e) {
            e.printStackTrace();}
    }
}