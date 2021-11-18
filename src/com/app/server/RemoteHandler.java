package com.app.server;

import com.app.Logger;
import com.app.RemoteServer;
import com.app.server.control.Control;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class RemoteHandler extends UnicastRemoteObject implements RemoteServer {
    private static final long serialVersionUID = 2674880711467464646L;

    private final Map<UUID, Set<String>> sessions; // keep track of the sessions
    private final PrintService printService;
    private final AuthService authService;

    public RemoteHandler(Control control) throws RemoteException {
        super();
        sessions = new HashMap<>();
        printService = new PrintService();
        authService = new AuthService(control);
    }

    private boolean isAuthenticated(UUID id) {
        return sessions.containsKey(id);
    }

    @Override
    public String print(UUID id, String username, String fileName, String printer) throws RemoteException {
        if (!isAuthenticated(id)) return null;

        Set<String> permissions = sessions.get(id);
        boolean hasAccess = permissions.contains("print");
        if (!hasAccess)
             return Logger.log("Access denied to print", username );

        // if ( policy.allow print )
        Logger.log("print", username);
        return printService.print(fileName, printer);
    }

    @Override
    public String queue(UUID id, String username, String printer) throws RemoteException {
        if (!isAuthenticated(id)) return null;
        Set<String> permissions = sessions.get(id);
        boolean hasAccess = permissions.contains("queue");
        if (!hasAccess)
            return Logger.log("Access denied to queue", username );
        Logger.log("Username", username);
        return printService.queue(printer);
    }

    @Override
    public String topQueue(UUID id, String username, String printer, int job) throws RemoteException {
        if (!isAuthenticated(id)) return null;
        Set<String> permissions = sessions.get(id);
        boolean hasAccess = permissions.contains("topQueue");
        if (!hasAccess)
            return Logger.log("Access denied to topQueue", username );
        Logger.log("Username", username);
        return printService.topQueue(printer, job);
    }

    @Override
    public String start(UUID id, String username) throws RemoteException {
        if (!isAuthenticated(id)) return null;
        Set<String> permissions = sessions.get(id);
        boolean hasAccess = permissions.contains("start");

        if (!hasAccess)
            return Logger.log("Access denied to start", username );

        Logger.log("Permission denined ", username);
        return printService.start();
    }

    @Override
    public String stop(UUID id, String username) throws RemoteException {
        if (!isAuthenticated(id)) return null;
        Set<String> permissions = sessions.get(id);
        boolean hasAccess = permissions.contains("stop");
        if (!hasAccess)
            return Logger.log("Access denied to stop", username );
        Logger.log("Username", username);
        return printService.stop();
    }

    @Override
    public String restart(UUID id, String username) throws RemoteException {
        if (!isAuthenticated(id)) return null;
        Set<String> permissions = sessions.get(id);
        boolean hasAccess = permissions.contains("restart");
        if (!hasAccess)
            return Logger.log("Access denied to restart", username );
        Logger.log("Username", username);
        return printService.restart();
    }

    @Override
    public String status(UUID id, String username, String printer) throws RemoteException {
        if (!isAuthenticated(id)) return null;
        Set<String> permissions = sessions.get(id);
        boolean hasAccess = permissions.contains("status");
        if (!hasAccess)
            return Logger.log("Access denied to status", username );
        Logger.log("Username", username);
        return printService.status(printer);
    }

    @Override
    public String readConfig(UUID id, String username, String parameter) throws RemoteException {
        if (!isAuthenticated(id)) return null;
        Set<String> permissions = sessions.get(id);
        boolean hasAccess = permissions.contains("readConfig");

        if (!hasAccess)
            return Logger.log("Access denied to readConfig", username );
        Logger.log("Username", username);
        return printService.readConfig(parameter);
    }

    @Override
    public String setConfig(UUID id, String username, String parameter, String value) throws RemoteException {
        if (!isAuthenticated(id)) return null;
        Set<String> permissions = sessions.get(id);
        boolean hasAccess = permissions.contains("setConfig");
        if (!hasAccess)
            return Logger.log("Access denied to setConfig", username );
        Logger.log("Username", username);
        return printService.setConfig(parameter, value);
    }

    @Override
    public String establishSymKey(String message) throws RemoteException {
        return authService.establishSymKey(message);
    }

    @Override
    public UUID login(String username, String password) throws RemoteException {
        Session session = authService.login(username, password);
        sessions.put(session.getId(), session.getSessionPermissions());
        return session.getId();
    }

    public boolean changePermissions(String username, String[] rolesOrPerms) {
        return authService.changePermissions(username, rolesOrPerms);
    }

    public boolean register(String username, String password, String[] rolesOrPerms) {
        return authService.register(username, password, rolesOrPerms);
    }

    public boolean unregister(String username) throws RemoteException {
        return authService.unregister(username);
    }
}
