package ru.simplechat;

import org.junit.Before;
import org.junit.Test;
import ru.simplechat.utils.LastMessage;

import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.*;

/**
 * Created by Ivan on 30.11.2017.
 */
public class MyServerTest {
    MyServer server;
    private String host = "localhost";
    private int port = 8989;

    @Before
    public void start() {
        server = MyServer.getInstance();
        Thread thread = new Thread() {
            public void run() {
                server.runServer();
            }
        };
        thread.start();
    }

    @Test
    public void testRunServer() throws Exception {
        Socket socket = new Socket(host, port);
        assertNotNull(socket);
        Thread.sleep(100);
        server.deleteHandler(getLastClient());
        socket.close();
    }


    @Test
    public void testLoginExist() throws Exception {
        Socket socket = new Socket(host, port);
        Thread.sleep(100);
        getLastClient().setUsername("Tester");
        assertTrue(server.loginExist("Tester"));
        server.deleteHandler(getLastClient());
        socket.close();
    }

    @Test
    public void testCreateLogin() throws Exception {
        Socket socket = new Socket(host, port);
        Thread.sleep(100);
        ClientHandler handler = getLastClient();
        assertTrue(server.createLogin("User", handler));
        server.deleteHandler(handler);
        socket.close();
    }

    @Test
    public void testGetLastMessage() throws Exception {
        Socket socket = new Socket(host, port);
        Thread.sleep(100);
        ClientHandler handler = getLastClient();
        server.createLogin("TestUser", handler);
        LastMessage message = server.getLastMessage();
        assertFalse(message.isEmpty());
        server.deleteHandler(handler);
        socket.close();
    }

    private ClientHandler getLastClient() {
        CopyOnWriteArrayList<ClientHandler> list = server.getConnections();
        return list.get(list.size() - 1);
    }
}