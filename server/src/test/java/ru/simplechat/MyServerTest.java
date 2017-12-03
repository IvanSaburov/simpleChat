package ru.simplechat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import ru.simplechat.utils.LastMessage;

import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Ivan on 30.11.2017.
 */
public class MyServerTest {
    MyServer server;
    private String host = "localhost";
    private int port = 8989;

    @Mock
    CopyOnWriteArrayList<ClientHandler> clientsList;

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
        socket.close();
    }


    @Test
    public void testLoginExist() throws Exception {
        ClientHandler mockClientHandler = mock(ClientHandler.class);
        when(mockClientHandler.getUsername()).thenReturn("tester");
        server.getConnections().add(mockClientHandler);
        assertTrue(server.loginExist("tester"));
    }

    @Test
    public void testCreateLogin() throws Exception {
        Socket mockSocket = mock(Socket.class);
        OutputStream out = mock(OutputStream.class);
        ClientHandler mockClientHandler = mock(ClientHandler.class);

        when(mockClientHandler.getSocket()).thenReturn(mockSocket);
        when(mockClientHandler.getUsername()).thenReturn("User");
        when(mockClientHandler.getSocket().getOutputStream()).thenReturn(out);

        assertTrue(server.createLogin("User", mockClientHandler));
        server.deleteHandler(mockClientHandler);
    }

    @Test
    public void testGetLastMessage() throws Exception {
        Socket mockSocket = mock(Socket.class);
        OutputStream out = mock(OutputStream.class);
        ClientHandler mockClientHandler = mock(ClientHandler.class);

        when(mockClientHandler.getUsername()).thenReturn("TestUser");
        when(mockClientHandler.getSocket()).thenReturn(mockSocket);
        when(mockClientHandler.getSocket().getOutputStream()).thenReturn(out);

        server.createLogin("TestUser", mockClientHandler);
        LastMessage message = server.getLastMessage();
        assertFalse(message.isEmpty());
        server.deleteHandler(mockClientHandler);
    }
}