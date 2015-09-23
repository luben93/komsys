package com.company;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        // write your code here
        int port = 1234;
        int bufferSize = 1024;

        ArrayList<Socket> clients = new ArrayList<>();
        DatagramSocket serverSocket = null;

        try {
            serverSocket = new DatagramSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port);
            System.exit(1);
        }

        //Socket clientSocket = null;
        System.out.println("Waiting for connection.....");
        try {

            byte[] buffer = new byte[bufferSize];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            serverSocket.receive(packet);
            String message = new String(packet.getData(), 0, packet.getLength());


            // Print information
            InetAddress clientAddr = packet.getAddress();
            System.out.println("Packet received from " + clientAddr.getHostName());
            System.out.println("Packet contained: " + message);
            buffer = new byte[bufferSize];
            packet = new DatagramPacket(buffer, buffer.length);
            int randig= ThreadLocalRandom.current().nextInt(0,100);

            while (true) {//TODO inte while true, det är så internet dör


                serverSocket.receive(packet);
                message = new String(packet.getData(), 0, packet.getLength());

                System.out.println("Packet received from " + packet.getAddress().getHostName());
                System.out.println("Packet contained:" + message+":");//TODO remove last char in message, its a fucking \n
                if (packet.getAddress().equals( clientAddr)) {
                    // Print information
                    System.out.println("is equalt");
                    if (message.equals("HELLO\n")) {
                        byte[] bufOut = "OK".getBytes();
                        DatagramPacket out = new DatagramPacket(bufOut, bufOut.length, clientAddr, packet.getPort());
                        serverSocket.send(out);
                        System.out.println("sent ok");
                    }else if(message.equals("START\n")){
                        byte[] bufOut = "READY".getBytes();
                        DatagramPacket out = new DatagramPacket(bufOut, bufOut.length, clientAddr, packet.getPort());
                        serverSocket.send(out);
                        System.out.println("sent ready");
                    }else{
                        byte[] bufOut = "CONSTRUCT ADDITIONAL PYLONS".getBytes();
                        DatagramPacket out = new DatagramPacket(bufOut, bufOut.length, clientAddr, packet.getPort());
                        serverSocket.send(out);
                        System.out.println("sent CONSTRUCT ADDITIONAL PYLONS");
                    }

                } else {
                    System.out.println("is not eqaul");
                    byte[] bufOut = "BUSY".getBytes();
                    DatagramPacket out = new DatagramPacket(bufOut, bufOut.length, clientAddr, packet.getPort());
                    serverSocket.send(out);
                    System.out.println("sent busy");
                }
            }
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        } finally {
            serverSocket.close();
            System.out.println("UDPServer done.");
        }
        System.exit(0);


    }


}
