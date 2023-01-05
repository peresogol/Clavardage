package org.example.NetworkManager;

import java.io.IOException;
import java.net.*;
import java.util.function.Consumer;

public class ServerUDP extends Thread {
    private byte[] buf = new byte[1024];
    private int port;
    private Consumer<DatagramPacket> handler;

    public ServerUDP(int port, Consumer<DatagramPacket> handler)  {
        this.handler = handler;
        this.port = port;
    }

    public void run() {

        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        DatagramPacket packet;

        System.out.println("UDP server launched.");

        while (true) {
            packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.handler.accept(packet);
        }
    }


}
