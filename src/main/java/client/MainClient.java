package client;

import client.client.Client;
import client.client.RequestChecker;
import client.handlers.*;
import client.reader.Reader;
import dataBase.DataBaseConnection;
import dataBase.User;
import person.Collection;

import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainClient {

    public static DataBaseConnection dataBaseConnection = new DataBaseConnection();
    public static Collection collection = new Collection(dataBaseConnection);

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client client = new Client("localhost", 43728);

        RequestChecker requestChecker = new RequestChecker(client, collection);

        requestChecker.run();
    }
}