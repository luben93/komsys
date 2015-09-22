package com.company;

/**
 * Created by Julia on 2015-09-21.
 */


        import java.io.PrintWriter;
        import java.net.Socket;

/**
 * Created by Julia on 2015-09-21.
 */
public class Client {
    private PrintWriter out;
    private Socket sock;
    private String nickname;

    public Client(PrintWriter out, Socket sock, String nickname){
        this.out = out;
        this.sock = sock;
        this.nickname = nickname;
    }

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    public Socket getSock() {
        return sock;
    }

    public void setSock(Socket sock) {
        this.sock = sock;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
