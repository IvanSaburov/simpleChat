package ru.simplechat;

import org.junit.Before;
import org.junit.Test;
import ru.simplechat.utils.PropertyLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

import static org.junit.Assert.*;


public class ClientHandlerTest {

    Socket socket;
    ClientHandler clientHandler;

    @Before
    public void before() throws Exception {
        Thread thread = new Thread() {
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(8989);
                    System.out.println("server socket created.");
                    serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
//                    String s = in.readLine();
//                    System.out.println(s);
                    System.out.println("accepted connection");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    @Test
    public void testRun() throws Exception {

    }

    @Test
    public void testSendString() throws Exception {
        socket = new Socket("localhost" , 8989);
        clientHandler = new ClientHandler(socket);
        clientHandler.sendString("test");
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
//        String s = in.readLine();
        String sdf = "";
    }
}