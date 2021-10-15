package com.app.server;


import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) throws RemoteException
    {
        System.setProperty("java.rmi.server.hostname", "127.0.0.1");
        PrintService service = new PrintService();
        LocateRegistry.createRegistry(1099);

        System.out.println("Running server");

        Registry registry = LocateRegistry.getRegistry();
        registry.rebind("client", service);
    }
}
