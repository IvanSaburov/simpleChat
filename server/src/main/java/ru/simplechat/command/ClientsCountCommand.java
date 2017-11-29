package ru.simplechat.command;

import ru.simplechat.MyServer;

/**
 * Created by Ivan on 19.11.2017.
 */
public class ClientsCountCommand extends Command {
    private MyServer myServer = MyServer.getInstance();
    public ClientsCountCommand() {
        setCommandName("count");
        setCommandDescription("Получение количества клиентов");
    }

    @Override
    public String runCommand(String author) {
        int count = myServer.getClientsCount();
        StringBuilder sb = new StringBuilder("В данный момент ");
        switch (count) {
            case 1:
                sb.append("подключен 1 клиент");
                break;
            default:
                sb.append("подключено " + count + " клиента(ов)");
        }
        return sb.toString();
    }
}
