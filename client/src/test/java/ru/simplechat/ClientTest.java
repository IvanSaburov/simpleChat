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

/**
 * Created by Ivan on 20.11.2017.
 */
public class ClientTest {
    @Before
    public void before() throws Exception{
        Thread thread = new Thread() {
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(8888);
                    System.out.println("server socket created.");
                    serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    @Test
    public void testCreateSocket() throws Exception {
        assertNotNull(Client.createSocket("localhost", 8888));
    }
}