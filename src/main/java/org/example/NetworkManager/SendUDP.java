package org.example.NetworkManager;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class SendUDP {
    private DatagramSocket socket;
    private InetAddress address;
    private int port;
    private byte[] buf;

    public SendUDP(int port) throws SocketException, UnknownHostException {
        this.socket = new DatagramSocket();
        this.port = port;
        this.address = InetAddress.getByName("localhost");
    }

    public void start() {
        String echo;
        SendUDP client;

        Scanner sc= new Scanner(System.in);

        while(true) {
            System.out.print("Enter a message: ");
            String str= sc.nextLine();
            this.send(str);
        }
    }

        public void send(String msg) {
            buf = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);

            try {
                socket.send(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    public void close() {
        socket.close();
    }
}