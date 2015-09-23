/**
 * Created by Julia on 2015-09-21.
 */
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Client {

    public static void main(String[] args) throws IOException {
        String serverHostname = "130.229.184.65";
        int port = 1234;

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket(serverHostname, port);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    echoSocket.getInputStream()));

            System.out.println("You are now connected!");
            BufferedReader stdIn = new BufferedReader(
                    new InputStreamReader(System.in));

            System.out.println("echo: " + in.readLine());
            System.out.print("> ");
            Server server = new Server(in);
            Thread trad = new Thread(server);
            trad.start();
            String userInput;

            while (out!=null) {
                if ((userInput = stdIn.readLine()) != null) {
                    out.println(userInput);
                    System.out.print("> ");
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to: " + serverHostname);
            System.exit(1);
        }finally {
            try {
                if(in != null) in.close();
                if(out != null) out.close();
                if (echoSocket != null) echoSocket.close();
            } catch(IOException ie) {}
        }

    }

    public static class Server implements Runnable {
        BufferedReader in;
        public Server(BufferedReader in) throws SocketException {
            this.in = in;
        }

        @Override
        public void run() {
            String server_msg;
            while (true) {
                try {
                    if ((server_msg = in.readLine()) != null && !server_msg.equals("")) {
                        System.out.println("echo: " + server_msg);
                        if(server_msg.equals("exit")){
                            System.out.println("Closing client...");
                            System.exit(0);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
    }
}