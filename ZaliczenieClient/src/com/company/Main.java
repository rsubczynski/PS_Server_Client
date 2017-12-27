package com.company;

import java.net.*;

public class Main {

    public static void main(String[] args) throws Exception {
        int mcPort = 7;
        String mcIPStr = "230.1.1.1";
        DatagramSocket udpSocket = new DatagramSocket();

        InetAddress mcIPAddress = InetAddress.getByName(mcIPStr);
        byte[] msg = "DISCOVERY".getBytes();
        DatagramPacket packet = new DatagramPacket(msg, msg.length);
        packet.setAddress(mcIPAddress);
        packet.setPort(mcPort);
        udpSocket.send(packet);

        System.out.println("Sent a  multicast message.");
        System.out.println("Exiting application");
        udpSocket.close();

        ServerSocket welcomeSocket = new ServerSocket(7);
        try {
            while (true) {
                Socket connectionSocket = welcomeSocket.accept();
                new StartNewTread(connectionSocket, packet);
            }
        } catch (Exception e) {
            System.out.println("Socket juz istenije");
        }
    }
}



