package client.handlers;


import client.MainClient;
import client.client.Client;
import dataBase.User;
import person.Person;
import server.MainServer;

import java.io.IOException;
import java.io.Serializable;

public class UpdateIdHandler implements Handler, Serializable {

    Client client;

    public UpdateIdHandler(Client client) {
        this.client = client;
    }

    @Override
    public String handle(String[] args, User user) throws IOException, ClassNotFoundException {
        if (args == null) {
            return "Этой команде нужен аргумент (id).";
        }
        if (args.length > 1) {
            return "У этой команды не может быть так много аргументов.";
        }
        String id = args[0];
        String responseForExist = client.send("is_exist", id, user);
        if (responseForExist.equals("false")) {
            return "Человека с таким id нет в коллекции.";
        }
        String responseForUser = client.send("can_change", id, user);
        if (responseForUser.equals("false")) {
            return "Вы не можете обновить этого человека, так как им владеет другой пользователь.";
        }
        else{
            Person person = new Person(id);
            person.setId(Integer.parseInt(id));
            return client.send("update_id", person, user);
        }

    }
}
