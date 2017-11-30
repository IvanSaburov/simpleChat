package ru.simplechat;

import org.junit.Before;
import org.junit.Test;

import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Ivan on 20.11.2017.
 */
public class ClientTest {
    Client client;

    @Before
    public void before() throws Exception {
        client = new Client();
    }

    @Test
    public void testCreateSocket() throws Exception {
        ServerSocket serverSocket = new ServerSocket(8888);
        Socket socket = client.createSocket("localhost", 8888);
        serverSocket.accept();
        assertNotNull(socket);
    }
}