package com.app.client;

import com.app.server.Information;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class Client {
    // Auth between client and server
    // Server must store passwords in a file (hash(pwd))
    // Session key -> Diffie Hellman?
    // send hash(username, password)

    public Client() throws RemoteException {
    }

    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        String hostName = "127.0.0.1";
        Remote server =  Naming.lookup("rmi://" + hostName + "/Hello");
        String response = ((Information)server).getInformation();
        System.out.println("response: " + response);

        System.out.println("Running client");
    }
}
