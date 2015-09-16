package com.company;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class Server implements Runnable {
    private Socket client = null;

    public Server(Socket client)throws SocketException {

        this.client = client;
        client.setSoTimeout(10000);

    }

    public int startar() {
        PrintWriter out=null;
        BufferedReader in=null;
        Protocol protocol=new Protocol(client);
        System.out.println("Connection successful");
        System.out.println("Waiting for input.....");

        return protocol.game();

    }


    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {


        startar();
    }
}