package client.handlers;

import client.client.Client;
import dataBase.User;

import java.io.IOException;
import java.io.Serializable;

public class InfoHandler implements Handler, Serializable {

    Client client;

    public InfoHandler(Client client){
        this.client = client;
    }

    @Override
    public String handle(String[] args, User user) throws IOException, ClassNotFoundException {
        if(args != null){
            return "У этой команды не должно быть аргументов.";
        }
        return client.send("info", null, user);
    }
}
