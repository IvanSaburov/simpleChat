package ru.simplechat;

import ru.simplechat.utils.PropertyLoader;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

public class Client {

    private static String IP_ADDR;
    private static int PORT;
    private Socket socket;

    static {
        IP_ADDR = PropertyLoader.getProperties().getProperty("IP_ADDR");
        PORT = Integer.parseInt(PropertyLoader.getProperties().getProperty("PORT"));
    }

    public static void main(String[] args) {
        new Client();
    }


    private Client() {
        try {
            socket = createSocket(IP_ADDR, PORT);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")), true);

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in, Charset.forName("UTF-8"))); //Консоль клиента

            MessageReader mr = new MessageReader(in);
            Thread thread = new Thread(mr); //поток чтения входящих сообщений
            thread.start();
            System.out.println("Введите логин:");
            while (true) {
                try {
                    String t = br.readLine();
                    out.println(t);
                } catch (Exception e) {
                    mr.setIsStopped(true);
                    socket.close();
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Connection exception: " + e.getMessage());
        }
    }

    public static Socket createSocket(String ip, int port) throws IOException {
        return new Socket(ip, port);
    }
}