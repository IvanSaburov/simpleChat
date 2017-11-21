package ru.simplechat;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Ivan on 19.11.2017.
 */
public class MessageReader implements Runnable {
    boolean isStopped = false;
    BufferedReader in;

    public MessageReader(BufferedReader in) {
        this.in = in;
    }

    public void run() {
        try {
            while (!isStopped) {
                if (in.ready()) {
                    String s = in.readLine();
                    System.out.println(s);
                }
            }
        } catch (IOException e) {
            System.err.println("Error I/O: " + e.getMessage());
        }
    }

    public void setIsStopped(boolean isStopped) {
        this.isStopped = isStopped;
    }
}
