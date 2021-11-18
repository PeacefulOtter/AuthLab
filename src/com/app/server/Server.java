package com.app.server;


import com.app.Settings;
import com.app.server.control.AccessBasedControl;
import com.app.server.control.Control;
import com.app.server.control.RoleBasedControl;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashSet;
import java.util.Set;

public class Server {
    // service.register( "alice", "alicepwd", alicePerms);
    // service.register( "bob", "bobpwd", bobPerms);
    // service.register( "cecilia", "ceciliapwd", ceciliaPerms);
    // service.register( "david", "davidpwd", userPerms);
    // service.register( "erica", "ericapwd", userPerms);
    // service.register( "fred", "fredpwd", userPerms);
    // service.register( "george", "georgepwd", userPerms);

    public static void main(String[] args) throws RemoteException
    {
        Control controlPolicy = new RoleBasedControl(); // new RoleBasedControl();
        RemoteHandler service = new RemoteHandler(controlPolicy);

        System.setProperty("java.rmi.server.hostname", Settings.HOSTNAME);
        Registry registry = LocateRegistry.createRegistry( Settings.PORT );
        registry.rebind(Settings.SUBDOMAIN, service);

        System.out.println("Running server");

        /* FOR ROLE BASED ACCESS CONTROL*/
        // String[] alicePerms = "manager".split( " " );
        String[] bobPerms = "service-technician".split(" ");
        String[] ceciliaPerms = "power-user".split(" ");
        String[] userPerms = "user".split(" ");

        /* FOR ACCESS BASED CONTROL*/
        // String[] alicePerms = "start stop restart print queue topQueue status readConfig setConfig".split( " " );
        // String[] bobPerms = "start stop restart status readConfig setConfig".split(" ");
        // String[] ceciliaPerms = "print queue topQueue restart".split(" ");
        // String[] userPerms = "print queue".split(" ");

        // bob leaves
        boolean a = service.unregister("bob");
        System.out.println("bob unregistered " + a);

        // george now service-technician - takes the role of bob
        boolean b = service.changePermissions("george", bobPerms);
        System.out.println("george changed perms " + b);

        // henry new user
        boolean c = service.register("henry", "henry_password", userPerms);
        System.out.println("henry register " + c);

        // ida also a power-user like cecilia
        boolean d = service.register("ida", "ida_password", ceciliaPerms);
        System.out.println("ida register " + d);
    }
}
