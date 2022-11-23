package org.example.NetworkManager;

import com.sun.jdi.IntegerValue;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class ServerUDP extends Thread {

    private DatagramSocket socket;
    private byte[] buf = new byte[256];



    public ServerUDP() throws SocketException {

        Scanner sc= new Scanner(System.in); //System.in is a standard input stream
        System.out.print("Enter an reception port: ");
        String port = sc.nextLine();

        socket = new DatagramSocket(Integer.parseInt(port));
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
