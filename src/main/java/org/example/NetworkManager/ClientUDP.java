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

    public void start() {
        String echo;
        ClientUDP client;

        Scanner sc= new Scanner(System.in); //System.in is a standard input stream

        try {
            client = new ClientUDP();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        while(true) {
            System.out.print("Enter a message: ");
            String str= sc.nextLine();

            echo = client.send(str);
        }
    }

        public String send(String msg) {
            buf = msg.getBytes();
            Scanner sc= new Scanner(System.in); //System.in is a standard input stream
            System.out.print("Enter an emission port: ");
            String port = sc.nextLine();

            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, Integer.parseInt(port));



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