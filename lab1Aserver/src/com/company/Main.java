package com.company;

import java.io.IOException;
import java.util.NoSuchElementException;

public class Main {

    public static void main(String[] args) {
        Server server = new Server(1234);
        while (true) {
            try {
                server.serve();
            } catch (IOException e) {
                System.err.println("IO execption");
                //server.exit();
                System.exit(-1);
            } catch (NoSuchElementException n) {
                System.out.println("out of clients restarting server");
            }
        }
    }


}
