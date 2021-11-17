package com.app.server;


import com.app.Settings;
import com.app.server.control.Control;
import com.app.server.control.RoleBasedControl;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashSet;
import java.util.Set;

public class Server {
    public static void main(String[] args) throws RemoteException
    {
        Control controlPolicy = new RoleBasedControl(); // you can replace with AccessBasedControl()
        RemoteHandler service = new RemoteHandler(controlPolicy);

        System.setProperty("java.rmi.server.hostname", Settings.HOSTNAME);
        Registry registry = LocateRegistry.createRegistry( Settings.PORT );
        registry.rebind(Settings.SUBDOMAIN, service);

        System.out.println("Running server");

        Set<String> roles = new HashSet<>(){{add("..."); add("...");}};
        service.register( "", "", roles );
        service.register( "", "", roles );
        service.unregister( "" );
        service.unregister( "" );
    }
}
