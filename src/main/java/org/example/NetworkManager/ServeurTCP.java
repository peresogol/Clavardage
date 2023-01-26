package org.example.NetworkManager;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class ServeurTCP extends Thread {

    private ServerSocket serveurSock;
    private Socket sock;
    private String address; // identifier


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



    /*
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
    }*/
}