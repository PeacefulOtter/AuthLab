package com.app.client;

import com.app.RemoteServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class Client {
    private static final String username = "datasec";
    private static final String password = "bad_pwd";

    // Auth between client and server
    // Server must store passwords in a file (hash(pwd))
    // Session key -> Diffie Hellman?
    // send hash(username, password)

    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        String hostName = "127.0.0.1";
        Remote server = Naming.lookup("rmi://" + hostName + "/Hello");
        System.out.println("Running client");

        String response = ((RemoteServer)server).start();
        System.out.println("response: " + response);

        response = ((RemoteServer)server).setConfig("param", "lol");
        System.out.println("response: " + response);

        response = ((RemoteServer)server).stop();
        System.out.println("response: " + response);
    }
}
