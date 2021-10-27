package com.app.server;

import com.app.HashUtils;
import com.app.RemoteServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;

public class RemoteHandler extends UnicastRemoteObject implements RemoteServer
{
    private static final long serialVersionUID = 2674880711467464646L;

    private final Set<String> clients; // stores the hash of the clients => hash(username+password)
    private final PrintService printService;
    private final AuthService authService;

    public RemoteHandler() throws RemoteException {
        super();
        clients = new HashSet<>();
        printService = new PrintService();
        authService = new AuthService();
    }

    @Override
    public String print(String fileName, String printer) throws RemoteException {
        return printService.print(fileName, printer);
    }

    @Override
    public String queue(String printer) throws RemoteException {
        return printService.queue(printer);
    }

    @Override
    public String topQueue(String printer, int job) throws RemoteException {
        return printService.topQueue(printer, job);
    }

    @Override
    public String start() throws RemoteException {
        return printService.start();
    }

    @Override
    public String stop() throws RemoteException {
        return printService.stop();
    }

    @Override
    public String restart() throws RemoteException {
        return printService.restart();
    }

    @Override
    public String status(String printer) throws RemoteException {
        return printService.status(printer);
    }

    @Override
    public String readConfig(String parameter) throws RemoteException {
        return printService.readConfig(parameter);
    }

    @Override
    public String setConfig(String parameter, String value) throws RemoteException {
        return printService.setConfig(parameter, value);
    }

    @Override
    public String establishSymKey(String message) throws RemoteException {
        return authService.establishSymKey(message);
    }

    @Override
    public boolean register(String username, String password) throws RemoteException {
        String hash = HashUtils.getHash( username, password );
        boolean registered = authService.register(username, password);
        if ( registered )
            clients.add( hash );
        return registered;
    }

    @Override
    public boolean login(String username, String password) throws RemoteException {
        return authService.login(username, password);
    }
}
