package com.app.server;

import com.app.Logger;
import com.app.RemoteServer;
import com.app.server.control.Control;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class RemoteHandler extends UnicastRemoteObject implements RemoteServer
{
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

    private boolean isAuthenticated(UUID id)
    {
        return sessions.containsKey(id);
    }

    @Override
    public String print(UUID id, String username, String fileName, String printer) throws RemoteException {
        if ( !isAuthenticated(id) ) return null;

        Set<String> permissions = sessions.get(id);
        boolean hasAccess = permissions.contains("print");
        if ( !hasAccess )
            return "You do not have access to this method";

        // if ( policy.allow print )
        Logger.log("print", username);
        return printService.print(fileName, printer);
    }

    @Override
    public String queue(UUID id, String username, String printer) throws RemoteException {
        if ( !isAuthenticated(id) ) return null;
        Logger.log("Username", username);
        return printService.queue(printer);
    }

    @Override
    public String topQueue(UUID id, String username, String printer, int job) throws RemoteException {
        if ( !isAuthenticated(id) ) return null;
        Logger.log("Username", username);
        return printService.topQueue(printer, job);
    }

    @Override
    public String start(UUID id, String username) throws RemoteException {
        if ( !isAuthenticated(id) ) return null;
        Logger.log("Username", username);
        return printService.start();
    }

    @Override
    public String stop(UUID id, String username) throws RemoteException {
        if ( !isAuthenticated(id) ) return null;
        Logger.log("Username", username);
        return printService.stop();
    }

    @Override
    public String restart(UUID id, String username) throws RemoteException {
        if ( !isAuthenticated(id) ) return null;
        Logger.log("Username", username);
        return printService.restart();
    }

    @Override
    public String status(UUID id, String username, String printer) throws RemoteException {
        if ( !isAuthenticated(id) ) return null;
        Logger.log("Username", username);
        return printService.status(printer);
    }

    @Override
    public String readConfig(UUID id, String username, String parameter) throws RemoteException {
        if ( !isAuthenticated(id) ) return null;
        Logger.log("Username", username);
        return printService.readConfig(parameter);
    }

    @Override
    public String setConfig(UUID id, String username, String parameter, String value) throws RemoteException {
        if ( !isAuthenticated(id) ) return null;
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

    public boolean register(String username, String password, Set<String> roles) throws RemoteException {
        return authService.register(username, password, roles);
    }

    public boolean unregister(String username) throws RemoteException {
        return authService.unregister(username);
    }
}
