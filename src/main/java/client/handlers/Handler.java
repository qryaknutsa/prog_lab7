package client.handlers;


import dataBase.User;

import java.io.IOException;

public interface Handler {
    String handle(String[] args, User user) throws IOException, ClassNotFoundException;
}
