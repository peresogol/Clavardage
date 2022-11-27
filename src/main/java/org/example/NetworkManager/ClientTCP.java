package org.example.NetworkManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ClientTCP {

    public static void sendMessage(String msg) {

        final Socket clientSocket;
        final PrintWriter out;
        final Scanner sc = new Scanner(System.in);

        try {
            clientSocket = new Socket("127.0.0.1",4444);

            out = new PrintWriter(clientSocket.getOutputStream());

            msg = sc.nextLine();
            out.println(msg);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();}
    }
}

