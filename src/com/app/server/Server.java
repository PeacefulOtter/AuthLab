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
    public static void main(String[] args) throws RemoteException
    {
        Control controlPolicy = new AccessBasedControl(); // new RoleBasedControl();
        RemoteHandler service = new RemoteHandler(controlPolicy);

        System.setProperty("java.rmi.server.hostname", Settings.HOSTNAME);
        Registry registry = LocateRegistry.createRegistry( Settings.PORT );
        registry.rebind(Settings.SUBDOMAIN, service);

        System.out.println("Running server");

        /* FOR ROLE BASED ACCESS CONTROL*/
        // Set<String> georgeRoles = new HashSet<>(){{add("service-technician");}};
        // Set<String> henryRoles = new HashSet<>(){{add("user");}};
        // Set<String> idaRoles = new HashSet<>(){{add("power-user");}};

        /* FOR ACCESS BASED CONTROL*/
        Set<String> georgeRoles = new HashSet<>(){{ add("status"); add("readConfig"); add("setConfig"); }};
        Set<String> henryRoles = new HashSet<>(){{ add("print"); add("queue"); }};
        Set<String> idaRoles = new HashSet<>(){{ add("print"); add("queue"); add("topQueue"); add("restart"); }};

        // bob leaves
        boolean a = service.unregister("bob");
        System.out.println("bob unregistered " + a);

        // george now service-technician
        boolean b = service.changePermissions("george", georgeRoles);
        System.out.println("george changed perms " + b);

        // henry new user
        boolean c = service.register("henry", "henry_password", henryRoles);
        System.out.println("henry register " + c);

        // ida power-user
        boolean d = service.register("ida", "ida_password", idaRoles);
        System.out.println("ida register " + d);
    }
}
