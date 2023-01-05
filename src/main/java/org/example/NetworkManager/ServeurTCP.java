package org.example.NetworkManager;

import org.example.Managers.Manager;

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
            in = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
            msg = in.readLine();

            while(msg!=null){
                System.out.println("AppClient sends : "+msg);
                Manager.dispatch(msg, this.address);
                msg = in.readLine();
            }
            System.out.println("Message Received: " + msg);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //sock.close();
    }
}