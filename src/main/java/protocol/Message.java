package protocol;

import dataBase.User;

import java.io.Serializable;

public class Message implements Serializable {
    public String command;
    public Object args;
    public User user;

    public Message(String command, Object args, User user) {
        this.command = command;
        this.args = args;
        this.user = user;
    }
}
