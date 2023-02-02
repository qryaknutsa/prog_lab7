package client.handlers;

import client.client.Client;
import dataBase.User;

import java.io.IOException;
import java.io.Serializable;

public class ExitHandler implements Handler, Serializable {

    Client client;

    public ExitHandler(Client client){
        this.client = client;
    }
    @Override
    public String handle(String[] args, User user) throws IOException, ClassNotFoundException {
        if(args != null){
            return "У этой команды не должно быть аргументов.";
        }
        String message = client.send("exit", null, user);
        System.out.println(message);
        System.exit(0);
        return "";
    }
}
