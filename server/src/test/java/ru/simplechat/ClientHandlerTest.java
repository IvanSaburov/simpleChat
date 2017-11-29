package ru.simplechat;

import org.junit.After;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;


public class ClientHandlerTest {
    List<ClientHandler> listClientHandler;
    private MyServer myServer = MyServer.getInstance();

    @Before
    public void before() throws Exception {
        Thread myServerThread = new Thread() {
            public void run() {
                MyServer.main(new String[0]);
            }
        };
        myServerThread.start();
        ClientHandler clientHandler = new ClientHandler("localhost", 8989);
        clientHandler.setUsername("Tester");
        listClientHandler = new ArrayList<>();
        listClientHandler.add(clientHandler);
    }

    @After
    public void after() throws Exception {
        for (ClientHandler clientHandler : listClientHandler) {
            clientHandler.disconnect();
        }
        myServer.tryRunCommand("-exit");
    }
}