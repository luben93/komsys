package com.company;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Created by luben on 15-09-23.
 */
public class Server {
    private int port;
    private int bufferSize = 1024;

    // ArrayList<Socket> clients = new ArrayList<>();
    private DatagramSocket serverSocket = null;

    public void serve() throws IOException, NoSuchElementException {
        // write your code here

        //Socket clientSocket = null;
        System.out.println("Waiting for connection.....");
        //try {

        byte[] buffer = new byte[bufferSize];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        ArrayList<InetAddress> clients = new ArrayList<>();
        //LinkedList<InetAddress> clients = new LinkedList<>();
        Protocol p = new Protocol();
        serverSocket.receive(packet);
        String message = new String(packet.getData(), 0, packet.getLength());
        InetAddress clientAddr = packet.getAddress();

        send(p.game(message), clientAddr, packet.getPort());
        clients.add(clientAddr);
        while (true) {//TODO inte while true, det är så internet dör

            //    try{
            serverSocket.receive(packet);
            message = new String(packet.getData(), 0, packet.getLength());
//                if (clientAddr== null){
//                    clients.add(clientAddr);
//                    clientAddr = packet.getAddress();
//
//                }


            System.out.println("Packet from: " + packet.getAddress().getHostName() + ":" + packet.getPort() + " contained:" + message + ":");//TODO remove last char in message, its a fucking \n
            if (packet.getAddress().equals(clientAddr)) { // Print information
                String out = p.game(message);
                boolean testing = false;
                InetAddress old = clientAddr;
                if (out.equals("DONE")) {
                    if (clients.size() == 1) {
                        clients.remove(0);
                        out = "You have guessed correct\n" +
                                " the game has now ended\n" +
                                " if want to play again you have to stand last in line";
                        send(out, old, packet.getPort()); //start protocol
                        testing = true;
                        buffer = new byte[bufferSize];
                        packet = new DatagramPacket(buffer, buffer.length);
                        p = new Protocol();
                        serverSocket.receive(packet);
                        message = new String(packet.getData(), 0, packet.getLength());
                        clientAddr = packet.getAddress();
                        send(p.game(message), clientAddr, packet.getPort());
                        clients.add(clientAddr);
                    } else {
                        clients.remove(0);//TODO nullpointer after last ???
                        clientAddr = clients.get(0);
                        p = new Protocol();
                        out = "You have guessed correct\n" +
                                " the game has now ended\n" +
                                " if want to play again you have to stand last in line";
                    }
                }
                if (!testing) {
                    send(out, old, packet.getPort()); //start protocol
                } else {
                    testing = false;
                }

            } else {

                send("BUSY", packet.getAddress(), packet.getPort());
                if (!clients.contains(packet.getAddress())) {
                    System.out.println("added");
                    clients.add(packet.getAddress());
                }
            }
            //}catch(NoSuchElementException e) {
            // JUST RESTART LOOP OF DOOM
            // }

        }
//        } catch (IOException e) {
//            System.err.println("Accept failed.");
//            System.exit(1);
//        } finally {
//            serverSocket.close();
//            System.out.println("UDPServer done.");
//        }
        // System.exit(0);
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
