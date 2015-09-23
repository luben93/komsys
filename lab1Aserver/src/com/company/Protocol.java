package com.company;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by luben on 15-09-16.
 */
public  class Protocol {
    private boolean sentOk=false;
    private boolean sentReady=false;
    private int randig=-1;

    public  Protocol() {


    }

    public String game(String message) {
//        byte[] bufOut="CONSTRUCT ADDITIONAL PYLONS".getBytes();
//        DatagramPacket out = null;
//
//        try {
//            while (!message.equals("HELLO")) {
//                 bufOut = "CONSTRUCT ADDITIONAL PYLONS".getBytes();
//                 out = new DatagramPacket(bufOut, bufOut.length, clientAddr, packet.getPort());
//                serverSocket.send(out);
//                System.out.println("sent CONSTRUCT ADDITIONAL PYLONS");
//            }
//                bufOut = "OK".getBytes();
//                 out = new DatagramPacket(bufOut, bufOut.length, clientAddr, packet.getPort());
//                serverSocket.send(out);
//                System.out.println("sent ok");
//
//
//            while (!message.equals("START")) {
//                 bufOut = "CONSTRUCT ADDITIONAL PYLONS".getBytes();
//                 out = new DatagramPacket(bufOut, bufOut.length, clientAddr, packet.getPort());
//                serverSocket.send(out);
//                System.out.println("sent CONSTRUCT ADDITIONAL PYLONS");
//            }
//                 bufOut = "READY".getBytes();
//                 out = new DatagramPacket(bufOut, bufOut.length, clientAddr, packet.getPort());
//                serverSocket.send(out);
//                System.out.println("sent ready");
//                int randig= ThreadLocalRandom.current().nextInt(0,100);
//
//
//        }catch (IOException e) {
//            e.printStackTrace();
//            return -1;
//        }
//        return 0;
        String out="YOU MUST CONSTRUCT ADDITIONAL PYLONS";
        if(message.equals("HELLO")&&!sentOk&&!sentReady){//HELLO
            sentOk=true;
            out= "OK";
        }else if(message.equals("HELLO")&&sentOk&&!sentReady){//START
            sentReady=true;
            randig= ThreadLocalRandom.current().nextInt(0,100);
            out= "START";
        }else if(sentOk&&sentReady){//GUESS TODO contains?????
            int guess=-1;
            try{
                if(message.startsWith("GUESS ")){
                        guess=Integer.parseInt(message.substring(6));
                        System.out.println("Part 6: " + guess);
                    if(guess == randig){
                        out=("You have guessed correct");
                    }else if(guess <randig){
                        out=("LOW");
                    }else if(guess >randig){
                        out=("HIGH");

                    }
                }else{
                    throw new NumberFormatException("no guess");
                }
            }catch ( NumberFormatException e1){
                out= "NOPE write \nGUESS X \nwhere x is your guess";
            }
        }else{//else
            out= "NOPE";
        }
        return out;
    }




   /* public int game() {
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
            System.out.println(input);

            while (!input.equals("HELLO")) {
                System.out.println("not hello");
                out.println("You have to say HELLO to continue");
                if (input.equals("he")){
                    System.out.println("UOeuiopu");
                    return -9;
                }
                if ((input = in.readLine()) == null) {
                    return -2;
                }
            }
            out.println("OK");
            if ((input = in.readLine()) == null) {
                return -2;
            }


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
            //}


     /*   } catch (IOException e) {
            e.printStackTrace();
        }
        return -4;
    }//*

    private int recSanityCheck(String input ) throws IOException ,NullPointerException{
        int guess = -1;
        try{
            if(!input.substring(0, 6).equals("GUESS ")){
                throw new StringIndexOutOfBoundsException();
            }
                guess=Integer.parseInt(input.substring(6));
                System.out.println("Part 6: " + guess);
                return guess;

        }catch (StringIndexOutOfBoundsException | NumberFormatException e1){
            out.println("You have to write GUESS x");
            return  recSanityCheck(in,out,in.readLine());
        }
    }
//TODO: avsluta socketen//*/
}
