package com.company;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;

/**
 * Created by luben on 15-09-23.
 */
public class Server {
    private int port;
    private int bufferSize = 1024;

    // ArrayList<Socket> clients = new ArrayList<>();
    private DatagramSocket serverSocket = null;

    public void serve() {
        // write your code here

        //Socket clientSocket = null;
        System.out.println("Waiting for connection.....");
        try {

            byte[] buffer = new byte[bufferSize];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            serverSocket.receive(packet);
            String message = new String(packet.getData(), 0, packet.getLength());

            InetAddress clientAddr = packet.getAddress();// Print information
            System.out.println("Packet received from " + clientAddr.getHostName());
            System.out.println("Packet contained: " + message);
            byte[] FbufOut = "oh hai\n i will be your server today\n if i am am busy, i will tell you\n to play send\n HELLO and then\n START and then\n GUESS X where X is your guess number.".getBytes();
            DatagramPacket Fout = new DatagramPacket(FbufOut, FbufOut.length, clientAddr, packet.getPort());
            serverSocket.send(Fout);
            buffer = new byte[bufferSize];
            packet = new DatagramPacket(buffer, buffer.length);
            LinkedList<InetAddress> clients = new LinkedList<>();
            Protocol p = new Protocol();

            while (true) {//TODO inte while true, det är så internet dör


                serverSocket.receive(packet);
                message = new String(packet.getData(), 0, packet.getLength());

                System.out.println("Packet received from " + packet.getAddress().getHostName() + ":" + packet.getPort());
                System.out.println("Packet contained:" + message + ":");//TODO remove last char in message, its a fucking \n
                if (packet.getAddress().equals(clientAddr)) { // Print information
                    send(p.game(message), clientAddr, packet.getPort()); //start protocol
                    System.out.println("is equalt");
                } else {
                    System.out.println("is not eqaul");
                    send("BUSY", packet.getAddress(), packet.getPort());
                    //  clients.push(packet.getAddress());
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

    public Server(int port) {
        this.port = port;
        try {
            serverSocket = new DatagramSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port);
            System.exit(1);
        }

    }

    private void send(String msg, InetAddress recv, int recvPort) throws IOException {
        byte[] bufOut = msg.getBytes();
        DatagramPacket out = new DatagramPacket(bufOut, bufOut.length, recv, recvPort);
        serverSocket.send(out);
    }


}
