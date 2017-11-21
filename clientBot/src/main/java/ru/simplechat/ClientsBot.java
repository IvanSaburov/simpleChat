package ru.simplechat;

import ru.simplechat.utils.PropertyLoader;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

public class ClientsBot implements Runnable {

    private static String IP_ADDR;
    private static int PORT;
    private static int count = 50;
    private static int threadCount = 1;
    private static int quantityMessagesToSend = 44;

    static {
        IP_ADDR = PropertyLoader.getProperties().getProperty("IP_ADDR");
        PORT = Integer.parseInt(PropertyLoader.getProperties().getProperty("PORT"));
    }

    public static void main(String[] args) {
        for (int i = 0; i < count; i++) {
            Thread thread = new Thread(new ClientsBot());
            thread.start();
        }
    }


    private ClientsBot() {
    }

    public void run() {
        try {
            int number = threadCount++;
            Socket socket = new Socket(IP_ADDR, PORT);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")), true);

            MessageReader mr = new MessageReader(in);
            Thread thread = new Thread(mr); //поток чтения входящих сообщений
            thread.start();
            System.out.println("Введите логин:");
            int msgCount = 0;
            while (msgCount < quantityMessagesToSend) {
                try {
                    if (msgCount == 0) {
                        out.println("Bot-" + number + "-" + this.toString());
                    } else {
                        out.println("test message №" + msgCount);
                    }
                    msgCount++;
                    Thread.sleep(500);
                } catch (Exception e) {
                    String a = e.getMessage();
                    System.out.println(a);
                }
            }
            mr.setIsStopped(true);
            socket.close();
            in.close();
            out.close();

        } catch (IOException e) {
            System.out.println("Connection exception: " + e.getMessage());
        }
    }
}
