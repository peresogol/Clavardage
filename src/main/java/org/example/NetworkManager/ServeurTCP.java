package org.example.NetworkManager;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class ServeurTCP extends Thread {

    private ServerSocket serveurSock;
    private Socket sock;
    private String address; // identifier
    public ServeurTCP(Socket socket, ServerSocket serveurSocket) {
        this.sock = socket;
        this.serveurSock = serveurSocket;
        this.address = String.valueOf(this.sock.getInetAddress());
    }

    public void run() {
        BufferedReader in;
        String msg;
        try {

            while(true){
                in = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
                msg = in.readLine();
                System.out.println("zacazc");
                if(msg != null){
                    System.out.println("Message Received: " + msg);
                    String username = NetworkManager.getUsernameFromAddress(this.address);
                    NetworkManager.handleReceivedMessage(msg, username);
                    System.out.println("Released");
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //sock.close();
    }
}