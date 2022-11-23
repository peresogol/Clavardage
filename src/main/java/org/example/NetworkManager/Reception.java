package org.example.NetworkManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Reception extends Thread {

    private DatagramSocket socket;
    private byte[] buffer = new byte[256];
    private String received;
    public InetAddress address;
    public int port;


    public void close() {
        socket.close();
    }

    public void run() {

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);

        while (true) {
            try {
                socket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            setReceived(new String(buffer, 0, packet.getLength()));
            packet.setLength(buffer.length);
            System.out.println(received);
        }
    }

    public String getReceived() {
        return this.received;
    }

    public String setReceived(String r) {
        return this.received = r;
    }
}
