package ru.simplechat;

import ru.simplechat.utils.LastMessage;
import ru.simplechat.utils.PropertyLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MyServer {

    private static ArrayList<ClientHandler> connections = new ArrayList<>();
    private static LastMessage lastMessage;
    private static int PORT;
    private static boolean repeat = true;

    static {
        PORT = Integer.parseInt(PropertyLoader.getProperties().getProperty("PORT"));
    }

    public static void main(String[] args) {
        Thread thread = new Thread() {
            public void run() {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(System.in, Charset.forName("UTF-8")));
                    String input = in.readLine();
                    if (("-exit").equals(input)) {
                        System.out.println("Остановка сервера...");
                        repeat = false;
                        Socket stopSocket = new Socket("localhost", PORT);
                        stopSocket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        new MyServer();
    }

    private MyServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен ...");
            lastMessage = new LastMessage();
            while (repeat) {
                try {
                    ClientHandler conn = new ClientHandler(serverSocket.accept());
                    connections.add(conn);
                } catch (IOException e) {
                    System.out.println("ClientHanler exception: " + e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendToAllConnections(boolean sendToAuthor, String author, String value) {
        System.out.println(value);
        lastMessage.add(value);

        final int cnt = connections.size();
        for (int i = 0; i < cnt; i++) {
            if (connections.get(i).getUsername() == null ||
                    (author.equals(connections.get(i).getUsername()) && !sendToAuthor)) continue;
            connections.get(i).sendString(value);
        }
    }

    public static boolean loginExist(String login) {
        return connections.stream().filter((p) -> p.getUsername() != null && p.getUsername().equals(login)).count() != 0l;
    }

    public static void deleteHandler(ClientHandler clientHandler) {
        if (connections.contains(clientHandler)) connections.remove(clientHandler);
    }

    public static LastMessage getLastMessage() {
        return lastMessage;
    }

    public static Integer getClientsCount() {
        return connections.size();
    }
}
