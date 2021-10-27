package com.app.server;


import com.app.Settings;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) throws RemoteException
    {
        System.setProperty("java.rmi.server.hostname", Settings.HOSTNAME);
        LocateRegistry.createRegistry( Settings.PORT );
        RemoteHandler service = new RemoteHandler();

        Registry registry = LocateRegistry.getRegistry();
        registry.rebind(Settings.SUBDOMAIN, service);

        System.out.println("Running server");
    }
}
