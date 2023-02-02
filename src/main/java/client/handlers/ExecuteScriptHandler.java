package client.handlers;


import client.MainClient;
import client.client.Client;
import dataBase.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ExecuteScriptHandler implements Handler, Serializable {

    Client client;

    public ExecuteScriptHandler(Client client) {
        this.client = client;
    }

    @Override
    public String handle(String[] args, User user) throws IOException, ClassNotFoundException {
        if (args == null) {
            return "Это команде нужен аргумент (имя файла).";
        }
        if(args.length>1){
            return "У этой команды не может быть так много аргументов.";
        }
        client.send("execute_script", args[0], user);



        try {
            String filename = args[0];
            StringBuilder result = new StringBuilder();
            File file = new File(filename);
            Scanner in = new Scanner(file);


            Map<String, Handler> handlers = new HashMap<>();

            handlers.put("add", new AddHandler(client));
            handlers.put("add_if_min", new AddIfMinHandler(client));
            handlers.put("clear", new ClearHandler(client));
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
            handlers.put("execute_script", new ExecuteScriptHandler(client));

            while (in.hasNextLine()) {
                String[] commandAndArgs = in.nextLine().trim().split(" ");
                if(commandAndArgs[0].equals("execute_script")){
                    result.append("Нельзя рекурсивно вызывать команду execute_script.");
                }
                if (!handlers.containsKey(commandAndArgs[0])) {
                    result.append("Команда не найдена. Чтобы ознакомиться со всеми командами, введите \"help\".\n");
                } else {
                    if (commandAndArgs.length > 1) {
                        String[] arg = Arrays.copyOfRange(commandAndArgs, 1, commandAndArgs.length);
                        result.append("Команда \"").append(commandAndArgs[0]).append("\":\n");
                        result.append(handlers.get(commandAndArgs[0]).handle(arg, user));
                    } else {
                        result.append("Команда \"").append(commandAndArgs[0]).append("\":\n");
                        result.append(handlers.get(commandAndArgs[0]).handle(null, user));
                    }
                    result.append("\n");
                }
                result.append("__________________________\n");
            }
            return result.toString();
        } catch (NullPointerException | FileNotFoundException e) {
            return "Указанный файл не найден.";
        }
    }
}