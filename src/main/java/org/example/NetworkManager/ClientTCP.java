package org.example.NetworkManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


public class ClientTCP {

    public static void sendMessage(String msg, InetAddress address, int port) {

        final Socket clientSocket;
        final PrintWriter out;

        try {
            // ERROR POSSIBLE

            clientSocket = new Socket(address, port);

            out = new PrintWriter(clientSocket.getOutputStream());

            out.println(msg);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();}
    }
}