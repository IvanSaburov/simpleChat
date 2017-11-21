package ru.simplechat.command;


public abstract class Command {
    private String commandName;
    private String commandDescription;

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandDescription() {
        return commandDescription;
    }

    public void setCommandDescription(String commandDescription) {
        this.commandDescription = commandDescription;
    }

    abstract String runCommand(String author);
}
