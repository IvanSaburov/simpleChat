package ru.simplechat;

import ru.simplechat.utils.LastMessage;
import ru.simplechat.utils.PropertyLoader;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyServer {

    private CopyOnWriteArrayList<ClientHandler> connections = new CopyOnWriteArrayList<>();
    private LastMessage lastMessage;
    private static int PORT;
    private boolean repeat = true;
    private static MyServer instance = new MyServer();
    private boolean isRunning = false;

    static {
        PORT = Integer.parseInt(PropertyLoader.getProperties().getProperty("PORT"));
    }

    public static MyServer getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        Thread thread = new Thread() {
            public void run() {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(System.in, Charset.forName("UTF-8")));
                    String input = in.readLine();
                    instance.tryRunCommand(input);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        instance.runServer();
    }

    private MyServer() {
    }

    public void runServer() {
        if(isRunning) return;
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен ...");
            isRunning = true;
            lastMessage = new LastMessage();
            while (repeat) {
                try {
                    ClientHandler conn = new ClientHandler(serverSocket.accept());
                    conn.start();
                    connections.add(conn);
                } catch (IOException e) {
                    System.out.println("ClientHanler exception: " + e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void sendToAllConnections(boolean sendToAuthor, String author, String value) {
        System.out.println(value);
        lastMessage.add(value);

        final int cnt = connections.size();
        for (int i = 0; i < cnt; i++) {
            if (connections.get(i).getUsername() == null ||
                    (author.equals(connections.get(i).getUsername()) && !sendToAuthor)) continue;
            connections.get(i).sendString(value);
        }
    }

    public synchronized boolean loginExist(String login) {
        return connections.stream().filter((p) -> p.getUsername() != null && p.getUsername().equals(login)).count() != 0l;
    }

    public void deleteHandler(ClientHandler clientHandler) {
        if (connections.contains(clientHandler)) connections.remove(clientHandler);
    }

    public void tryRunCommand(String command) throws IOException {
        if (("-exit").equals(command)) {
            System.out.println("Остановка сервера...");
            repeat = false;
            Socket stopSocket = new Socket("localhost", PORT);
            stopSocket.close();
        }
    }

    public synchronized boolean createLogin(String login, ClientHandler handler) throws Exception {
        boolean hasLogin = false;
        PrintWriter out = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(handler.getSocket().getOutputStream(), StandardCharsets.UTF_8)), true);
        if (login.trim().length() > 0 && !loginExist(login)) {
            hasLogin = true;
            handler.setUsername(login);
            getLastMessage().send(out);
            sendToAllConnections(true, handler.getUsername(), handler.getUsername() + " присоединился к чату");
        } else {
            try {
                out.println("Этот логин уже занят");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return hasLogin;
    }

    public LastMessage getLastMessage() {
        return lastMessage;
    }

    public Integer getClientsCount() {
        return connections.size();
    }
}
