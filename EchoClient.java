import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) throws IOException {
        String serverHostname = new String("Localhost");

        System.out.println("Attempting to connect to " + serverHostname + "on port 1234.");

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket(serverHostname, 1234);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (Exception e) {
            System.err.println("Error connecting to the server: " + e.getMessage());
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        System.out.println("input: ");
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            if (userInput.equals("Done"))
                break;
        }
        System.out.println("echo: " + in.readLine());
        System.out.println("input: ");

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }
}