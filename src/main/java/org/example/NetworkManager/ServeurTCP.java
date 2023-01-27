package org.example.NetworkManager;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class ServeurTCP extends Thread {

    protected Socket socket;

    public ServeurTCP(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        String line;
        InputStream in = null;
        BufferedReader buff = null;

        try {
            in = socket.getInputStream();
            buff = new BufferedReader(new InputStreamReader(in));
        } catch (Exception e) {
            return;
        }

        while (true) {
            try {
                line = buff.readLine();
                if(line != null){
                    System.out.println("Message Received: " + line);
                    String username = NetworkManager.getUsernameFromAddress(String.valueOf(this.socket.getInetAddress()));
                    NetworkManager.handleReceivedMessage(line, username);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}