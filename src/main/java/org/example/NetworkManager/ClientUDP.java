package org.example.NetworkManager;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class ClientUDP {
    private DatagramSocket socket;
    private InetAddress address;

    private byte[] buf;

    public ClientUDP() throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("localhost");
    }

    public static void main(String[] args) {
        String echo;
        ClientUDP client;
        Scanner sc= new Scanner(System.in); //System.in is a standard input stream

        try {
            client = new ClientUDP();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.print("Enter a message: ");

        String str= sc.nextLine();

        echo = client.send(str);
        System.out.println(echo);
    }

        public String send(String msg) {
            buf = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);

            try {
                socket.send(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            packet = new DatagramPacket(buf, buf.length);

            try {
                socket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String received = new String(packet.getData(), 0, packet.getLength());
            return received;
        }

    public void close() {
        socket.close();
    }
}