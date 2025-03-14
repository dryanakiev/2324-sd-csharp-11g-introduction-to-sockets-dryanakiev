package ChatRoom.Server;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

public class ServerSocket {
    private String name;
    private String ip;
    private int port;
    private java.net.ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter outputStream;
    private BufferedReader inputStream;

    public ServerSocket() {
    }

    public void startServer() {
        try {
            serverSocket = new java.net.ServerSocket(getPort());

            clientSocket = serverSocket.accept();

            inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            outputStream = new PrintWriter(clientSocket.getOutputStream(),true);


        } catch (IOException e) {
            // TODO: Add exception handle for server connectivity issues
        }
    }

    public void stopServer() {
        try {
            inputStream.close();
            outputStream.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            // TODO: Add exception handle for close server socket
        }
    }

    public String receiveMessage() {
        String clientMessage = null;
        try {
            clientMessage = inputStream.readLine();

            System.out.println("Client :" + clientMessage);

        } catch (IOException e) {
            // TODO: Add exception handle for client side message
        }
        return clientMessage;
    }

    public void handleClientInput(String clientMessage) {
        int parsedMessage = Integer.parseInt(clientMessage);
        // TODO: Add parse exception
        // TODO: Fix new line
        switch (parsedMessage) {
            case 1: {
                broadcastMessage("The time is " + LocalDateTime.now());

                break;
            }

            case 2: {
                broadcastMessage("id,first_name,last_name,email,gender,ip_address" +
                        "1,Bertie,Boxell,bboxell0@people.com.cn,Male,53.161.222.126" +
                        "2,Rice,Ashmore,rashmore1@about.com,Male,160.254.167.201" +
                        "3,Karrie,Stirzaker,kstirzaker2@noaa.gov,Female,36.190.240.204");

                break;
            }

            case 3: {
                broadcastMessage("Server is stopping");

                stopServer();

                break;
            }

            default: {
                broadcastMessage("Server received: " + "\"" + clientMessage + "\"");
                break;
            }
        }
    }

    public void onClientConnect() {
        broadcastMessage("Hello, welcome to " + getName() + " Server!" +
                "You can do the following things:" +
                "1. Get server current time" +
                "2. Get example account data" +
                "3. Stop server");
    }

    public boolean clientIsConnected() {
        if (clientSocket.isConnected())
        {
            return true;
        }
        clientIsConnected();
        return false;
    }

    public void broadcastMessage(String message) {
        outputStream.println(message);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public java.net.ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(java.net.ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public PrintWriter getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(PrintWriter outputStream) {
        this.outputStream = outputStream;
    }

    public BufferedReader getInputStream() {
        return inputStream;
    }

    public void setInputStream(BufferedReader inputStream) {
        this.inputStream = inputStream;
    }

    public static void main(String[] args) {
        ServerSocket server = new ServerSocket();

        server.setPort(6666);
        server.startServer();

        if (server.clientIsConnected()) {
            server.onClientConnect();
        }

        while (true) {
            server.handleClientInput(server.receiveMessage());
        }
    }
}

