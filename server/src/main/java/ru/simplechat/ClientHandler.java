package ru.simplechat;

import ru.simplechat.command.CommandPool;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ClientHandler extends Thread {

    private Socket socket;

    private BufferedReader in;
    private PrintWriter out;
    private String username;

    public ClientHandler(String ipAddr, int port) throws IOException {
        this(new Socket(ipAddr, port));
    }

    public Socket getSocket() {
        return socket;
    }

    public ClientHandler(Socket socket1) throws IOException {
        this.socket = socket1;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8)), true);
        start();
    }

    @Override
    public void run() {
        boolean hasLogin = false;
        boolean loop = true;
        String inputString;
        while (loop) {
            try {
                inputString = in.readLine();
                if (inputString == null) {
                    loop = false;
                    continue;
                }
                if (!hasLogin) {
                    hasLogin = checkLogin(inputString);
                } else {
                    if (!inputString.trim().equals(""))
                        if (inputString.trim().startsWith("-")) {
                            if (inputString.trim().substring(1).equals("exit")) {
                                out.println("Вы покинули чат");
                                throw new Exception("exit");
                            }
                            out.println(CommandPool.runCommand(inputString.trim().substring(1), username));
                        } else {
                            MyServer.sendToAllConnections(false, username, "[" + username + "]" + inputString);
                        }
                }
            } catch (Exception e) {
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                loop = false;
                if (username != null)
                    MyServer.sendToAllConnections(false, username, username + " покинул(а) чат");
                MyServer.deleteHandler(this);
                disconnect();
            }
        }
    }

    public synchronized void sendString(String value) {
        try {
            out.println(value);
        } catch (Exception e) {
            disconnect();
        }
    }

    public synchronized void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean checkLogin(String login){
        boolean newHasLogin = false;
        if (login.trim().length() > 0 && !MyServer.loginExist(login)) {
            newHasLogin = true;
            username = login;
            MyServer.getLastMessage().send(out);
            MyServer.sendToAllConnections(true, username, username + " присоединился к чату");
        } else {
            try {
                out.println("Этот логин уже занят");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return newHasLogin;
    }

    @Override
    public String toString() {
        return "Connection: " + socket.getInetAddress() + ": " + socket.getPort();
    }
}
