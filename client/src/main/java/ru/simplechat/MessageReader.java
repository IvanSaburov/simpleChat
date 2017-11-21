package ru.simplechat;

import java.io.BufferedReader;
import java.io.IOException;

public class MessageReader implements Runnable {
    boolean isStopped = false;
    BufferedReader in;
    String lastInput;

    public MessageReader(BufferedReader in) {
        this.in = in;
    }

    public void run() {
        try {
            while (!isStopped) {
                String s = in.readLine();
                if (s == null) {
                    isStopped = true;
                    continue;
                }
                System.out.println(s);
                lastInput = s;
            }
        } catch (IOException e) {
            System.err.println("Error I/O: " + e.getMessage());
        }
    }

    public void setIsStopped(boolean isStopped) {
        this.isStopped = isStopped;
    }

    public String getLastInput(){
        return lastInput;
    }
}
