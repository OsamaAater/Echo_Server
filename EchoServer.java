import java.net.*;
import java.io.*;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        serverSocket = new ServerSocket(1234); // Port number can be changed

        System.out.println("Server started. Listening on port 1234...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected: " + clientSocket);

            Thread clientThread = new Thread(new ClientHandler(clientSocket));
            clientThread.start();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Received from client " + clientSocket + ": " + inputLine);
                    out.println(inputLine);

                    if (inputLine.equals("Done")) {
                        break;
                    }
                }

                System.out.println("Closing connection with client " + clientSocket);
            } catch (IOException e) {
                System.err.println("Error handling client: " + e.getMessage());
            } finally {
                try {
                    if (out != null) out.close();
                    if (in != null) in.close();
                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println("Error closing streams: " + e.getMessage());
                }
            }
        }
    }
}
