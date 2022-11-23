package org.example.NetworkManager;

import com.sun.jdi.IntegerValue;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class ServerUDP extends Thread {

    private DatagramSocket socket;
    private byte[] buf = new byte[256];
    private String received;
    private int port;

    public ServerUDP(int port) throws SocketException {
        this.port = port;
        this.socket = new DatagramSocket(port);
    }

    public void run() {

        System.out.println("Server launched.");

        while (true) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Message recu : " + received);

        }
        //socket.close();
    }

    public String getReceived(){
        return received;
    }
}
