package com.company;

import java.io.DataOutputStream;
import java.net.*;
import java.util.concurrent.TimeUnit;

public class Main {
    static boolean isConnected = false;

    public static void main(String[] args) throws Exception {

        int mcPort = 7;
        String mcIPStr = "230.1.1.1";
        MulticastSocket mcSocket = null;
        InetAddress mcIPAddress = null;
        mcIPAddress = InetAddress.getByName(mcIPStr);
        mcSocket = new MulticastSocket(mcPort);
        System.out.println("Multicast Receiver running at:"
                + mcSocket.getLocalSocketAddress());
        mcSocket.joinGroup(mcIPAddress);

        DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);

        System.out.println("PORT NASLUCHIWNANY " + mcPort);

        System.out.println("Adres HOST" + mcIPStr);

        System.out.println("Waiting for a  multicast message...");
        mcSocket.receive(packet);
        String msg = new String(packet.getData(), packet.getOffset(),
                packet.getLength());
        System.out.println("[Multicast  Receiver] Received:" + msg);

        if (msg.equals("DISCOVERY")) {
            Socket clientSocket = new Socket("127.0.0.1", 7);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());


            outToServer.writeBytes("OFFER ADDRESS PORT " + "\r\n");
            outToServer.writeBytes("PORT NASLUCHIWNANY PORT " + mcPort + "\r\n");
            outToServer.writeBytes("Adres HOST " + mcIPStr + "\r\n");
            outToServer.writeBytes("" + "\r\n");
            if (!isConnected) {

                System.out.println("Timestamp Recive");
                mcSocket.receive(packet);
                String przerwa = new String(packet.getData(), packet.getOffset(),
                        packet.getLength());
                System.out.println("[Multicast  Receiver] Received:" + przerwa);
                outToServer.writeBytes("„Server ready” " + "\r\n");
                while (true) {
                    TimeUnit.MILLISECONDS.sleep(Integer.valueOf(przerwa));
                    try {
                        outToServer.writeBytes("Opoowiedz z opoznieniem " + przerwa + "ms " + "\r\n");
                        isConnected = false;
                    }catch (SocketException exception)
                    {
                        System.out.println("Klient został rozlaczony");
                        return;
                    }
                }

            } else {
                outToServer.writeBytes("„Server busy” " + "\r\n");
            }

        }

    }

}

