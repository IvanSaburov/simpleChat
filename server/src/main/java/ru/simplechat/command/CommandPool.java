package ru.simplechat.command;

import org.reflections.Reflections;

import java.util.*;

/**
 * Created by Ivan on 19.11.2017.
 */
public class CommandPool {
    private static Map<String, Command> commandMap = new HashMap<>();

    static {
        Command c;
        Reflections reflections = new Reflections("ru.simplechat.command");
        Set<Class<? extends Command>> commands = reflections.getSubTypesOf(Command.class);
        for (Class commandClass : commands) {
            try {
                c = (Command) commandClass.newInstance();
                commandMap.put(c.getCommandName(), c);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    public static String runCommand(String commandName, String author) {
        if (commandMap.containsKey(commandName)) {
            return commandMap.get(commandName).runCommand(author);
        } else {
            return "Данная команда не найдена";
        }
    }


    public static Map<String, Command> getCommandMap() {
        return commandMap;
    }
}
