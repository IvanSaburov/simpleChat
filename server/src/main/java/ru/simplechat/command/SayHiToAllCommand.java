package ru.simplechat.command;

import ru.simplechat.MyServer;

/**
 * Created by Ivan on 19.11.2017.
 */
public class SayHiToAllCommand extends Command {

    public SayHiToAllCommand() {
        setCommandName("sayHello");
        setCommandDescription("Команда передает \"Привет\" всем участникам чата");
    }

    @Override
    public String runCommand(String author) {
        String hello = author + " передает всем привет";
        MyServer.sendToAllConnections(false, author, hello);
        return "Вы передали всем \"Привет\"";
    }
}
