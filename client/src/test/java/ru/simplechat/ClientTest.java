package ru.simplechat;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Ivan on 20.11.2017.
 */
public class ClientTest {
    Client client;

    @Before
    public void before() throws Exception {
        client = new Client();
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
        assertNotNull(client.createSocket("localhost", 8888));
    }
}