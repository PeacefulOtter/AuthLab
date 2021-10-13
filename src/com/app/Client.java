package com.app;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
    public Client() throws RemoteException {
    }

    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        String hostName = "127.0.0.1";
        Information stub = (Information) Naming.lookup("rmi://" + hostName + "/Hello");
        String response = stub.getInformation();
        System.out.println("response: " + response);

        System.out.println("Running client");
    }
}
