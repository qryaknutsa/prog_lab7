package client.client;

import client.handlers.*;
import client.reader.Reader;
import person.Collection;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RequestChecker {

    Client client;
    Collection collection;

    public RequestChecker(Client client, Collection collection){
        this.client = client;
        this.collection = collection;
    }

    public void run() throws IOException, ClassNotFoundException {
        Map<String, Handler> handlers = new HashMap<>();

        handlers.put("add", new AddHandler(client));
        handlers.put("add_if_min", new AddIfMinHandler(client));
        handlers.put("clear", new ClearHandler(client));
        handlers.put("execute_script", new ExecuteScriptHandler(client));
        handlers.put("exit", new ExitHandler(client));
        handlers.put("filter_starts_with_name", new FilterStartsWithNameHandler(client));
        handlers.put("help", new HelpHandler(client));
        handlers.put("history", new HistoryHandler(client));
        handlers.put("info", new InfoHandler(client));
        handlers.put("insert_at_index", new InsertAtIndexHandler(client));
        handlers.put("print_ascending", new PrintAscendingHandler(client));
        handlers.put("print_field_ascending_birthday", new PrintFieldAscendingBirthdayHandler(client));
        handlers.put("remove_by_id", new RemoveByIdHandler(client));
        handlers.put("show", new ShowHandler(client));
        handlers.put("update_id", new UpdateIdHandler(client));
        handlers.put("sign_in", new SignInHandler(client));
        handlers.put("sign_up", new SignUpHandler(client));

        Reader reader = new Reader();
        System.out.println("Чтобы ознакомиться со всеми командами, введите \"help\".");
        while (true) {
            String[] commandAndArgs = reader.read();
            String[] defaultCommands = {"help", "sign_in", "sign_up", "exit"};
            if (collection.getUser() == null && !Arrays.asList(defaultCommands).contains(commandAndArgs[0])) {
                System.out.println("Вы не авторизованы\nВойдите или создайте аккаунт, с помощью команды sign_in, sign_up");
            } else {
                if (!handlers.containsKey(commandAndArgs[0])) {
                    System.out.println("Команда не найдена. Чтобы ознакомиться со всеми командами, введите \"help\".");
                } else {
                    String response;
                    if (commandAndArgs.length > 1) {
                        String[] arg = Arrays.copyOfRange(commandAndArgs, 1, commandAndArgs.length);
                        response = handlers.get(commandAndArgs[0]).handle(arg, collection.getUser());
                    } else {
                        response = handlers.get(commandAndArgs[0]).handle(null, collection.getUser());
                    }
                    HistoryHandler.history.add(commandAndArgs[0]);
                    System.out.println(response);

                }
            }
        }
    }
}
