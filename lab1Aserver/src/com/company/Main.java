package com.company;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.NoSuchElementException;

public class Main {

    public static void main(String[] args) {
        Server server = new Server(1234);
       while (true) {
           try {
               server.serve();
           } catch (SocketTimeoutException e) {
                e.printStackTrace();
           } catch (NoSuchElementException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
               System.exit(-1);
           }
       }
    }


}
