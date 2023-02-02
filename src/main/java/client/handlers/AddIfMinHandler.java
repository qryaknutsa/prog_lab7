package client.handlers;


import client.MainClient;
import client.client.Client;
import dataBase.User;
import person.Person;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;

public class AddIfMinHandler implements Handler, Serializable {

    Client client;

    public AddIfMinHandler(Client client) {
        this.client = client;
    }

    @Override
    public String handle(String[] args, User user) throws IOException, ClassNotFoundException {
        if(args != null){
            return "У этой команды не должно быть аргументов.";
        }
        Person person = new Person("any");
        return client.send("add_if_min", person, user);
    }
}
