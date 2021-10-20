package com.app.server;

import com.app.RemoteServer;

import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteHandler extends UnicastRemoteObject implements RemoteServer
{
    private static final long serialVersionUID = 2674880711467464646L;

    private final PrintService printService;
    private final AuthService authService;

    public RemoteHandler() throws RemoteException {
        super();
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
    public String exchangePublicNumbers(String message) throws RemoteException {
        return authService.exchangePublicNumbers(message);
    }

    @Override
    public boolean register(String message) throws RemoteException {
        return authService.register(message);
    }

    @Override
    public String login(String message) throws RemoteException {
        return authService.login(message);
    }
}
