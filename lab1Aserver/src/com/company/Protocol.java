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
        System.out.println("hej protokoll");
        try {
            out = new PrintWriter(client.getOutputStream(), true);

            in = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));

            String input;
            if ((input = in.readLine()) == null) {
                return -1;
            }
            System.out.println(input);

            while (!input.equals("HELLO")) {
                System.out.println("not hello");
                out.println("You have to say HELLO to continue");
                if ((input = in.readLine()) == null) {
                    return -2;
                }
            }
            out.println("OK");
            if ((input = in.readLine()) == null) {
                return -2;
            }
            System.out.println("okej");
            System.out.println(out);

            while (!input.equals("START")) {
                out.println("You have to say START to continue");
                if ((input = in.readLine()) == null) {
                    return -3;
                }
            }
            int randig=ThreadLocalRandom.current().nextInt(0,100);
            System.out.println(randig);
            out.println("READY");


            while ((input = in.readLine()) != null) {
               int guess = recSanityCheck(in,out,input);

                if(guess == randig){
                    out.println("You have guessed correct");
                    return 0;
                }else if(guess <randig){
                    out.println("LOW");
                }else if(guess >randig){
                    out.println("HIGH");

                }

                /*System.out.println("Server: " + input);
                out.println(input);

                if (input.equals("Bye."))
                    break;*/
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return -4;
    }

    private int recSanityCheck(BufferedReader in,PrintWriter out,String input ) throws IOException ,NullPointerException{
        int guess = -1;
        System.out.println("new recursion");
        try{
            System.out.println("Part 1: " + input);
            String check_guess = input.substring(0, 5);
            System.out.println("Part 2: " + check_guess);
            String check_space = input.substring(6);
            System.out.println("Part 3: " + check_space);
            if(!check_space.equals(" ")){
                System.out.println("Part 4: " + "else not space");
              throw new StringIndexOutOfBoundsException();
            }else{
                System.out.println("Part 5: " + "else");
                guess=Integer.parseInt(input.substring(7));
                System.out.println("Part 6: " + guess);
                return guess;
            }


        }catch (StringIndexOutOfBoundsException | NumberFormatException e1){
            out.println("You have to write GUESS x");
            System.out.println("Part 7: " + "recursive before line");

         /*   if ((input = in.readLine()) == null) {
                throw new NullPointerException("end of stream");
            }*/
            System.out.println("Part 8: " + "recursive catch");
            return  recSanityCheck(in,out,in.readLine());

        }

    }

}
