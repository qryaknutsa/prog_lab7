package client.client;

import dataBase.User;
import protocol.Message;
import protocol.Response;
import protocol.Serializator;

import java.io.IOException;
import java.io.Serializable;
import java.net.*;

public class Client implements Serializable{

    private final InetSocketAddress inetSocketAddress;
    private final DatagramSocket datagramSocket;

    public Client(String host, int port) throws UnknownHostException, SocketException {
        this.inetSocketAddress = new InetSocketAddress(InetAddress.getByName(host), port);
        this.datagramSocket = new DatagramSocket();
    }

    public String send(String command, Object args, User user) throws IOException, ClassNotFoundException {
        Message message = new Message(command, args, user);
        byte[] buffer = Serializator.serialize(message).toByteArray();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, inetSocketAddress);
        datagramSocket.send(packet);
        if(command.equals("execute_script")){
            return "";
        }
        return read();

    }

    public String read() throws IOException, ClassNotFoundException {
        byte[] buffer = new byte[999999];
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
        datagramSocket.receive(datagramPacket);
        datagramSocket.setSoTimeout(12000);
        Response response = (Response) Serializator.deserialize(datagramPacket.getData());
        if (response.getResponse().contains("Происходит выход из консоли...")) {
            System.out.println(response.getResponse());
            System.exit(0);
        }
        return response.getResponse();
    }
}
