package com.company;

import java.io.IOException;
import java.io.PrintWriter;
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
                //init new thread

                Server server=new Server(clientSocket);
                Thread trad = new Thread(server);
                trad.start();

                /*if(clients.size()>1){
                    System.out.println(" YEEE ");
                    PrintWriter out = new PrintWriter(clients.get(clients.size()-1).getOutputStream(), true);
                    out.println("BUSY");
                    trad.join();
                }
                */
                //trad.join();

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
        }



    }

    //TODO: avsluta tråden
}
