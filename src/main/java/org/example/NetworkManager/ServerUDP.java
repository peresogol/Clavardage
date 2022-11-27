package org.example.NetworkManager;

import java.io.IOException;
import java.net.*;

public class ServerUDP extends Thread {
    private byte[] buf = new byte[1024];
    private int port;

    public ServerUDP(int port)  {
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
            handlePacket(packet);
        }
        //socket.close();
    }

    private void handlePacket(DatagramPacket packet) {
        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Message recu : " + received);
    }

}
