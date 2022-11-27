package org.example.NetworkManager;

import java.net.*;
import java.util.*;

public class ClientUDP {
    private DatagramSocket socket;

    /*  Envoie la chaine de caractères broadcastMessage en broadcast sur toutes les adresses réseaux
     *  présentes dans la liste addressList, renvoyée par la fonction getBroadcastAddress()
     */
    public void sendBroadcast(String broadcastMessage) {
        List<InetAddress> addressList = new ArrayList<>();
        try {
            addressList = this.getBroadcastAddress();
            socket = new DatagramSocket();
            socket.setBroadcast(true);

            byte[] buffer = broadcastMessage.getBytes();

            for(InetAddress addr : addressList) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, addr, 5555);
                socket.send(packet);
            }
            socket.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* Récupère les adresses des réseaux auquel est connecté l'hôte
     * Retourne une liste contenant ces adresses
     */
    public List<InetAddress> getBroadcastAddress() throws SocketException {
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