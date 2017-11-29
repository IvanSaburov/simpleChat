package ru.simplechat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ivan on 21.11.2017.
 */
public class MyServerTest {
    ClientHandler clientHandler;
    MyServer myServer = MyServer.getInstance();
    Thread thread;

    @Before
    public void before() throws Exception {
        thread = new Thread() {
            public void run() {
                MyServer.main(new String[0]);
            }
        };
        thread.start();
    }

    @After
    public void after() throws Exception {
        thread.interrupt();
        clientHandler.disconnect();
    }

    @Test
    public void testDeleteHandler() throws Exception {
    }

    @Test
    public void testGetClientsCount() throws Exception {
        clientHandler = new ClientHandler("localhost", 8989);
        assertEquals(new Integer(1), myServer.getClientsCount());
    }
}