package org.example.NetworkManager;

import java.io.IOException;
import java.net.*;

public class ServerUDP extends Thread {

    private DatagramSocket socket;
    private byte[] buf = new byte[256];

    public ServerUDP() throws SocketException {
        socket = new DatagramSocket(4445);
    }

    public void run() {

        while (true) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buf, buf.length, address, port);
            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println(received);

            try {
                socket.send(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //socket.close();
    }
}
