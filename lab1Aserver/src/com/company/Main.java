package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	// write your code here

        ArrayList<Socket> clients = new ArrayList<>();
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(1234);
        }
        catch (IOException e)
        {
            System.err.println("Could not listen on port: " + 1234);
            System.exit(1);
        }

        Socket clientSocket = null;
        System.out.println("Waiting for connection.....");

        try {
            while(true) {//TODO inte while true, det är så internet dör
                clientSocket = serverSocket.accept();
                clients.add(clientSocket);
                clientSocket.setSoTimeout(20000);

                //init new thread
                Protocol protocol=new Protocol(clientSocket);
                System.out.println("Connection successful");
                System.out.println("Waiting for input.....");
                protocol.game();

            }
        }
        catch (IOException e)
        {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        try {
            clientSocket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}
