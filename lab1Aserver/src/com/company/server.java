package com.company;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Created by luben on 15-09-23.
 */
public class Server {
    private int port;
    private int bufferSize = 1024;

    private DatagramSocket serverSocket = null;

    public void serve() throws IOException, NoSuchElementException {
        System.out.println("Waiting for connection.....");

        byte[] buffer = new byte[bufferSize];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        LinkedList<InetAddress> clients = new LinkedList<>();
        Protocol p = new Protocol();

            serverSocket.receive(packet);
            String message = new String(packet.getData(), 0, packet.getLength());
            clients.add(packet.getAddress());
            send(p.game(message), clients.peek(), packet.getPort());
            while (true) {
                try {
                serverSocket.receive(packet);
                message = new String(packet.getData(), 0, packet.getLength());

                System.out.println("Packet from: " + packet.getAddress().getHostName() + ":" + packet.getPort() + " contained:" + message + ":");//TODO remove last char in message, its a fucking \n
                if (packet.getAddress().equals(clients.peek())) { // Print information
                    String out = p.game(message);
                    InetAddress addr = clients.peek();
                    if (out.equals("DONE")) {
                        p = new Protocol();
                        out = "You have guessed correct\n" +
                                " the game has now ended\n" +
                                " if want to play again you have to stand last in line";
                        addr = clients.pop();
                    }
                    send(out, addr, packet.getPort()); //start protocol
                } else {

                    send("BUSY", packet.getAddress(), packet.getPort());
                    if (!clients.contains(packet.getAddress())) {
                        clients.add(packet.getAddress());
                    }
                }
                }catch (SocketTimeoutException timeout){
                    timeout.printStackTrace();
                    clients.pop();
                    p=new Protocol();
                }
            }

    }

    public Server(int port) {
        this.port = port;
        try {
            serverSocket = new DatagramSocket(port);
            serverSocket.setSoTimeout(20000);
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
