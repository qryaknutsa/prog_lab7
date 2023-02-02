package client.handlers;

import client.MainClient;
import client.client.Client;
import dataBase.User;

import java.io.IOException;
import java.io.Serializable;

public class SignUpHandler implements Handler, Serializable {
    Client client;
    public SignUpHandler(Client client){
        this.client = client;
    }

    @Override
    public String handle(String[] args, User oldUser) throws IOException, ClassNotFoundException {
        if(args != null){
            return "У этой команды не должно быть аргументов.";
        }
        User user = new User();
        MainClient.collection.setUser(user);
        return client.send("sign_up", user, user);
    }
}
