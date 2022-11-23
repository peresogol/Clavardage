package org.example.NetworkManager;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class ServerUDP extends Thread {

    private DatagramSocket socket;
    private byte[] buffer = new byte[256];
    private String received;
    public InetAddress address;
    public int port;
    public ServerUDP() throws SocketException {
        socket = new DatagramSocket(4445);
    }

    public void send(){
        Scanner sc= new Scanner(System.in); //System.in is a standard input stream
        System.out.print("Enter a message: ");
        String str= sc.nextLine();
        DatagramPacket packet = new DatagramPacket(str.getBytes(), str.length(), address, port);

        try {
            socket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        socket.close();
    }

    public void run() {

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);

        //while (true) {
            try {
                socket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            setReceived(new String(buffer, 0, packet.getLength()));
            packet.setLength(buffer.length);
            System.out.println(received);
        //}




        /*

        while (true) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            address = packet.getAddress();
            port = packet.getPort();
            packet = new DatagramPacket(buf, buf.length, address, port);
            received = new String(packet.getData(), 0, packet.getLength());
            System.out.println(received);

            try {
                socket.send(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }*/
        //socket.close();
    }

    public String getReceived() {
        return this.received;
    }

    public String setReceived(String r) {
        return this.received = r;
    }
}
