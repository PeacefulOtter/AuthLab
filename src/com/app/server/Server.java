package com.app.server;


import com.app.Settings;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) throws RemoteException
    {
        RoleBasedAccessControl.retrieveRoles();
        RemoteHandler service = new RemoteHandler();

        System.setProperty("java.rmi.server.hostname", Settings.HOSTNAME);
        Registry registry = LocateRegistry.createRegistry( Settings.PORT );
        registry.rebind(Settings.SUBDOMAIN, service);

        System.out.println("Running server");
    }
}
