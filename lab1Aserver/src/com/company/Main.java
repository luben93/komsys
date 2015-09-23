package com.company;

import java.io.IOException;
import java.util.NoSuchElementException;

public class Main {

    public static void main(String[] args) {
        Server server = new Server(1234);
        try {
            server.serve();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
