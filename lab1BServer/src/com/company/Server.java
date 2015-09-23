package com.company;

/**
 * Created by Julia on 2015-09-21.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Julia on 2015-09-21.
 */
public class Server {
    static ArrayList<Client> clients = new ArrayList<Client>();

    public Server(int port) {
        try {
            server(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void server(int port) throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port);
            System.exit(1);
        }

        Socket clientSocket = null;
        System.out.println("Waiting for connection.....");

        try {
            while (true) {//TODO inte while true, det är så internet dör
                clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(
                        clientSocket.getOutputStream());
                synchronized (clients) {
                    clients.add(new Client(out, clientSocket, ""));
                }

                ClientHandler ch = new ClientHandler(clients.get(clients.size() - 1), this);
                Thread trad = new Thread(ch);
                trad.start();
            }
        } catch (IOException e) {
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

    private synchronized void sendToAll(String msg, Client client) {
        /*
        A thread iterating through the list (sending)
must not be interrupted by other threads
sending, adding or removing streams/handlers
to the list. We need o synchronize code –
monitor?

         */
        if (msg.equals("/who")) {
            StringBuilder sb = new StringBuilder(100);
            for (int i = 0; i < clients.size(); i++) {
                if (!clients.get(i).getSock().equals(client)) {
                    sb.append(clients.get(i).getSock().getInetAddress().getHostName() + " , ");
                }
            }
            msg = sb.toString();
            try {
                client.getOut().print(" The clients connected : " + msg + "\n\r");
                client.getOut().flush(); // !
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            synchronized (clients) {
                try {
                    for (int i = 0; i < clients.size(); i++) {
                        if (!clients.get(i).getOut().equals(client.getOut())) {
                            String hostname = null;
                            if (client.getNickname() != null && !client.getNickname().equals("")) {
                                hostname = client.getNickname();
                            } else {
                                hostname = client.getSock().getInetAddress().getHostName();
                            }
                            clients.get(i).getOut().print(hostname + " wrote : " + msg + "\n\r");
                            clients.get(i).getOut().flush(); // !
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public class ClientHandler implements Runnable {
        Client client;
        Server s;

        public ClientHandler(Client client, Server s) {
            this.client = client;
            this.s = s;
        }

        @Override
        public void run() {
            PrintWriter out = null;
            BufferedReader in = null;
            try {
                out = new PrintWriter(client.getSock().getOutputStream(), true);
                in = new BufferedReader(
                        new InputStreamReader(client.getSock().getInputStream()));
                System.out.println("Connection successful");
                System.out.println("Waiting for input.....");

                String input;
                out.println("Welcome to the chat!");
                while (true) {
                    if ((input = in.readLine()) != null) {
                        if (input.startsWith("/")) {
                            if (input.equals("/quit")) {
                                client.getOut().print("exit"+"\n\r");
                                client.getOut().flush();
                                input = "has leaved the chat room";
                                s.sendToAll(input, client);
                                break;
                            } else if (input.equals("/help")) {
                                String msg = "1. /quit 2. /who 3. /nick <nickname> 4. /help";
                                try {
                                    client.getOut().print(" Available commands : " + msg + "\n\r");
                                    client.getOut().flush(); // !
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                if (input.length() >= 6) {
                                    if (input.substring(0, 6).equals("/nick ")) {
                                        client.setNickname(input.substring(6));
                                    }
                                }else{
                                    try {
                                        client.getOut().print(" unknown command "+"\n\r");
                                        client.getOut().flush(); // !
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        } else {
                            s.sendToAll(input, client);
                        }


                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (in != null) in.close();
                    if (out != null) out.close();
                    if (client.getSock() != null) client.getSock().close();
                    for(int i = 0; i < clients.size();i++){
                        if(clients.get(i).getSock().equals(client.getSock())){
                            clients.remove(i);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}