package org.example.NetworkManager;

import java.net.*;
import java.util.*;

public class ClientUDP {
    private static DatagramSocket socket;
    private static int destPort;


    public static void setDestPort(int destPort) {
        ClientUDP.destPort = destPort;
    }


    /*  Envoie la chaine de caractères broadcastMessage en broadcast sur toutes les adresses réseaux
     *  présentes dans la liste addressList, renvoyée par la fonction getBroadcastAddress()
     */
    public static void sendBroadcast(String broadcastMessage) {
        List<InetAddress> addressList = new ArrayList<>();
        try {
            addressList = getBroadcastAddress();
            socket = new DatagramSocket();
            socket.setBroadcast(true);

            byte[] buffer = broadcastMessage.getBytes();

            for(InetAddress addr : addressList) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, addr, destPort);
                socket.send(packet);
               // packet = new DatagramPacket(buffer, buffer.length, addr, 6666);
               // socket.send(packet);
            }
            socket.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    /*
    Envoie d'un message à un utilisateur en particulier, permet de réduire l'usage des broadcasts et de diminuer la charge du réseau
     */
    public static void sendMessage(String message, InetAddress address) {

        try {
            socket = new DatagramSocket();

            byte[] buffer = message.getBytes();

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, destPort);
            socket.send(packet);

           // packet = new DatagramPacket(buffer, buffer.length, address, 6666);
            //socket.send(packet);

            socket.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * Récupère les adresses des réseaux auquel est connecté l'hôte
     * Retourne une liste contenant ces adresses
     */
    private static List<InetAddress> getBroadcastAddress() throws SocketException {
        List<InetAddress> broadcastList = new ArrayList<>();
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                continue;
            }
            networkInterface.getInterfaceAddresses().stream().map(a -> a.getBroadcast()).filter(Objects::nonNull).forEach(broadcastList::add);
        }
        return broadcastList;
    }


    public void close() {
        socket.close();
    }


}