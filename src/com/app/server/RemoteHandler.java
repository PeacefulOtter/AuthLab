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
        return id != null && sessions.containsKey(id);
    }

    private boolean hasAccess( UUID id, String method )
    {
        Set<String> permissions = sessions.get(id);
        return permissions.contains(method);
    }

    @Override
    public String print(UUID id, String username, String fileName, String printer) throws RemoteException {
        if ( !isAuthenticated(id) ) return "You are not authenticated - please login first";
        else if ( !hasAccess(id, "print") ) return "You do not have access to this method";

        Logger.log("print", username);
        return printService.print(fileName, printer);
    }

    @Override
    public String queue(UUID id, String username, String printer) throws RemoteException {
        if ( !isAuthenticated(id) ) return "You are not authenticated - please login first";
        else if ( !hasAccess(id, "queue") ) return "You do not have access to this method";

        Logger.log("Username", username);
        return printService.queue(printer);
    }

    @Override
    public String topQueue(UUID id, String username, String printer, int job) throws RemoteException {
        if ( !isAuthenticated(id) ) return "You are not authenticated - please login first";
        else if ( !hasAccess(id, "topQueue") ) return "You do not have access to this method";

        Logger.log("Username", username);
        return printService.topQueue(printer, job);
    }

    @Override
    public String start(UUID id, String username) throws RemoteException {
        if ( !isAuthenticated(id) ) return "You are not authenticated - please login first";
        else if ( !hasAccess(id, "start") ) return "You do not have access to this method";

        Logger.log("Username", username);
        return printService.start();
    }

    @Override
    public String stop(UUID id, String username) throws RemoteException {
        if ( !isAuthenticated(id) ) return "You are not authenticated - please login first";
        else if ( !hasAccess(id, "stop") ) return "You do not have access to this method";

        Logger.log("Username", username);
        return printService.stop();
    }

    @Override
    public String restart(UUID id, String username) throws RemoteException {
        if ( !isAuthenticated(id) ) return "You are not authenticated - please login first";
        else if ( !hasAccess(id, "restart") ) return "You do not have access to this method";

        Logger.log("Username", username);
        return printService.restart();
    }

    @Override
    public String status(UUID id, String username, String printer) throws RemoteException {
        if ( !isAuthenticated(id) ) return "You are not authenticated - please login first";
        else if ( !hasAccess(id, "status") ) return "You do not have access to this method";

        Logger.log("Username", username);
        return printService.status(printer);
    }

    @Override
    public String readConfig(UUID id, String username, String parameter) throws RemoteException {
        if ( !isAuthenticated(id) ) return "You are not authenticated - please login first";
        else if ( !hasAccess(id, "readConfig") ) return "You do not have access to this method";

        Logger.log("Username", username);
        return printService.readConfig(parameter);
    }

    @Override
    public String setConfig(UUID id, String username, String parameter, String value) throws RemoteException {
        if ( !isAuthenticated(id) ) return "You are not authenticated - please login first";
        else if ( !hasAccess(id, "setConfig") ) return "You do not have access to this method";

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
        if ( session == null ) return null;
        sessions.put(session.getId(), session.getSessionPermissions());
        return session.getId();
    }
}
