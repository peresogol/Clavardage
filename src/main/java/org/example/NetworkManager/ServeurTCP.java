package org.example.NetworkManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class ServeurTCP extends Thread {

    private ServerSocket serveurSock;
    private Socket sock;

    public ServeurTCP(Socket socket, ServerSocket serveurSocket) {
        this.sock = socket;
        this.serveurSock = serveurSocket;
    }

    public void run() {
        PrintWriter out;
        BufferedReader in;
        String msg;
        try {
            in = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
            msg = in.readLine();
            while(msg!=null){
                System.out.println("Client sends : "+msg);
                msg = in.readLine();
            }
            System.out.println("Message Received: " + msg);

        /*
        Reponse :

        out = new PrintWriter(this.sock.getOutputStream());
        out.println(msg);
        out.flush();
        */
            //ois.close();
            //oos.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //sock.close();
    }
}