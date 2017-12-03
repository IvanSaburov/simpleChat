package ru.simplechat;

import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ClientHandlerTest {

    @Test
    public void testSetUsername() throws Exception {
        MyServer mockMyServer = mock(MyServer.class);
        Socket mockSocket = mock(Socket.class);
        OutputStream out = mock(OutputStream.class);
        InputStream in = mock(InputStream.class);

        when(mockSocket.getOutputStream()).thenReturn(out);
        when(mockSocket.getInputStream()).thenReturn(in);

        ClientHandler clientHandler = new ClientHandler(mockSocket);

        clientHandler.myServer = mockMyServer;
        when(mockMyServer.loginExist("testUser")).thenReturn(true);
        assertFalse(clientHandler.setUsername("testUser"));
    }

}