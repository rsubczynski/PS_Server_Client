package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class  StartNewTread extends Thread  {
    private Socket clientSentence;
    private String msg;
    private String zczytanyPort;
    private DatagramPacket packet;

    public StartNewTread(Socket connectionSocket, DatagramPacket packet) {
        clientSentence = connectionSocket;
        this.packet = packet;
        start();
    }
    @Override
    public void run() {
        super.run();
        BufferedReader infoFromServer = null;

        try {
            infoFromServer = new BufferedReader(new InputStreamReader(clientSentence.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                msg = infoFromServer.readLine();
                System.out.println(msg);
                if (msg.equals("")){
                    connect();
                }
            } catch (IOException e) {
                try {
                    clientSentence.close();
                    return;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                return;
            }
        }
    }

    private void connect() throws IOException {

        switch (getStringInKeybords()){
            case 1:
                zczytanyPort = "230.1.1.1";
                break;
            default:
                zczytanyPort="";

        }
        int mcPort = 7;
        String mcIPStr = zczytanyPort;
        DatagramSocket udpSocket = null;
        try {
            System.out.println("System bedzie dawal odpowiedzi co 1000 ms");
            udpSocket = new DatagramSocket();
            InetAddress mcIPAddress = InetAddress.getByName(mcIPStr);
            byte[] msg = "1000".getBytes();
            DatagramPacket packet = new DatagramPacket(msg, msg.length);
            packet.setAddress(mcIPAddress);
            packet.setPort(mcPort);
            udpSocket.send(packet);
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }


    private static int getStringInKeybords() {
        int wybor = 0;
        System.out.println( "1 :  230.1.1.1");

        try {
            System.out.println("Wybierz host");
            Scanner odczyt = new Scanner(System.in);
            wybor = odczyt.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Mozna wybierac tylko Liczby calkowite");
        }
        return wybor;
    }
}
