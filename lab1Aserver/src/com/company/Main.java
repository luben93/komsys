package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
	// write your code here
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
                //init new thread
                Server server=new Server(clientSocket);
                Thread trad = new Thread(server);
                trad.start();
                //add shit to array
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
            System.out.println("bajs på dig");
        }



    }
}
