package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by luben on 15-09-16.
 */
public class Protocol {
    private Socket client;

    public Protocol(Socket client) {
        this.client = client;

    }

    public int game() {
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            out = new PrintWriter(client.getOutputStream(), true);

            in = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));

            String input;
            if ((input = in.readLine()) == null) {
                return -1;
            }

            while (!input.equals("HELLO")) {
                System.out.println("someone said hello");
                out.print("You have to say HELLO to continue");
                if ((input = in.readLine()) == null) {
                    return -2;
                }
            }
            out.print("OK");

            while (!input.equals("START")) {
                out.print("You have to say START to continue");
                if ((input = in.readLine()) == null) {
                    return -2;
                }
            }
            int randig=ThreadLocalRandom.current().nextInt(0,100);
            out.print("READY");


            while ((input = in.readLine()) != null) {

                int guess=Integer.parseInt(input.substring(6));

                if(guess == randig){
                    out.print("You have guessed correct");
                    return 0;
                }else if(guess <randig){
                    out.print("LOW");
                }else if(guess >randig){
                    out.print("HIGH");

                }

                /*System.out.println("Server: " + input);
                out.println(input);

                if (input.equals("Bye."))
                    break;*/
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return -3;
    }

}
