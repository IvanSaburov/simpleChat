package ru.simplechat;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

/**
 * Created by Ivan on 21.11.2017.
 */
public class MessageReaderTest {
    BufferedReader in;
    String inputString = "test string";

    @Before
    public void before() throws Exception{
        in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8.name()))));
    }

    @Test
    public void testRun() throws Exception {
        MessageReader mr = new MessageReader(in);
        Thread readerThread = new Thread(mr);
        readerThread.start();
        assertEquals(inputString, mr.getLastInput());
    }
}