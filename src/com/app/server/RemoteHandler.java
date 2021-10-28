package com.app.server;

import com.app.Logger;
import com.app.RemoteServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class RemoteHandler extends UnicastRemoteObject implements RemoteServer
{
    private static final long serialVersionUID = 2674880711467464646L;

    private final Set<UUID> sessions; // keep track of the sessions
    private final PrintService printService;
    private final AuthService authService;

    public RemoteHandler() throws RemoteException {
        super();
        sessions = new HashSet<>();
        printService = new PrintService();
        authService = new AuthService();
    }

    private boolean isAuthenticated(UUID id)
    {
        return sessions.contains(id);
    }

    @Override
    public String print(UUID id, String username, String fileName, String printer) throws RemoteException {
        if ( !isAuthenticated(id) ) return null;
        Logger.log("Username", username);
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
    public UUID register(String username, String password) throws RemoteException {
        UUID id = authService.register(username, password);
        if ( id != null )
            sessions.add( id );
        return id;
    }

    @Override
    public UUID login(String username, String password) throws RemoteException {
        return authService.login(username, password);
    }
}
