package com.app.server;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server implements PrintService {
    public static void main(String[] args) throws UnknownHostException, MalformedURLException, RemoteException, AlreadyBoundException {
        System.setProperty("java.rmi.server.hostname","127.0.0.1");
        InformationImpl obj = new InformationImpl();
        LocateRegistry.createRegistry(1099);
        Registry registry = LocateRegistry.getRegistry();
        registry.rebind("Hello", obj);

        System.out.println("Running server");
    }

    @Override
    public void print(String fileName, String printer) throws RemoteException {
        Logger.log("print", "fileName: " + fileName + ", Printer: " + printer);
    }

    @Override
    public void queue(String printer) throws RemoteException {
        Logger.log("queue", "Printer: " + printer);
    }

    @Override
    public void topQueue(String printer, int job) throws RemoteException {
        Logger.log("topQueue", "Printer: " + printer + ", Job: " + job );
    }

    @Override
    public void start() throws RemoteException {
        Logger.log("start");
    }

    @Override
    public void stop() throws RemoteException {
        Logger.log("stop");
    }

    @Override
    public void restart() throws RemoteException {
        Logger.log("restart");
    }

    @Override
    public String status(String printer) throws RemoteException {
        Logger.log("status", "Printer: " + printer);
        return null;
    }

    @Override
    public String readConfig(String parameter) throws RemoteException {
        Logger.log("readConfig", "Parameter: " + parameter);
        return null;
    }

    @Override
    public void setConfig(String parameter, String value) throws RemoteException {
        Logger.log("setConfig", "Parameter: " + parameter + ", Value: " + value);
    }
}
