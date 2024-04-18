package ChatRoom.Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientSocket {
    Client client;

    public ClientSocket(Client client) {
        this.client = client;
    }

    public void startConnection() {
        try {
            client.setClientSocket(new Socket(client.getIp(),client.getPort()));

            client.setInputStream(new BufferedReader(new InputStreamReader(client.getClientSocket().getInputStream())));

            client.setOutputStream(new PrintWriter(client.getClientSocket().getOutputStream(),true));
        } catch (IOException e) {
            // TODO: Add exception handle for network connectivity error
        }
    }

    public void stopConnection() {
        try {
            client.getClientSocket().close();
            client.getInputStream().close();
            client.getOutputStream().close();
        } catch (IOException e) {
            // TODO: Add exception handle for closing network socket
        }

    }

    public void sendMessage(String message) {
        String serverMessage;

        client.getOutputStream().println(message);

        try {
            serverMessage = client.getInputStream().readLine();

            receiveMessage(serverMessage);

        } catch (IOException e) {
            // TODO: Add exception handle for server side broadcast
        }
    }

    public void receiveMessage(String message) {
        System.out.println(message);
    }

    public void receiveMessage() {
        try {
            System.out.println(client.getInputStream().readLine());
        } catch (IOException e) {
            // TODO: Add exception handle for server side broadcast
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Client client = new Client();

        client.setName("Client 1");
        client.setIp("127.0.0.1");
        client.setPort(6666);

        ClientSocket clientSocket = new ClientSocket(client);



        clientSocket.startConnection();

        clientSocket.receiveMessage();

        while(true) {
            clientSocket.sendMessage(scanner.nextLine());
        }
    }
}

