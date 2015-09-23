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

        String out="YOU MUST CONSTRUCT ADDITIONAL PYLONS";
        if(message.equals("HELLO")&&!sentOk&&!sentReady){//HELLO
            sentOk=true;
            out= "OK";
        }else if(message.equals("START")&&sentOk&&!sentReady){//START
            sentReady=true;
            randig= ThreadLocalRandom.current().nextInt(0,100);
            System.out.println("randig="+randig);
            out= "READY";
        }else if(sentOk&&sentReady){//GUESS TODO contains?????
            int guess=-1;
            try{
                if(message.startsWith("GUESS ")){
                        guess=Integer.parseInt(message.substring(6));
                    if(guess == randig){
                        out=("DONE");
                        System.out.println("ALL done");
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
        System.out.println("out=["+out+"]");
        return out;
    }



}
