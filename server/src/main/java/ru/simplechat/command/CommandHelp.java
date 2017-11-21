package ru.simplechat.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Ivan on 19.11.2017.
 */
public class CommandHelp extends Command {

    public CommandHelp() {
        setCommandName("help");
        setCommandDescription("Вывод информации о командах");
    }

    @Override
    public String runCommand(String author) {
        List<String> listDescriptions = new ArrayList<>();
        Collection<Command> commands = CommandPool.getCommandMap().values();
        for (Command command : commands) {
            listDescriptions.add(command.getCommandName() + " - " + command.getCommandDescription());
        }
        listDescriptions.add("exit - Покинуть чат");
        return String.join("\n", listDescriptions);
    }
}
