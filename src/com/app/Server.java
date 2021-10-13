package com.app;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

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

    }

    @Override
    public void queue(String printer) throws RemoteException {

    }

    @Override
    public void topQueue(String printer, int job) throws RemoteException {

    }

    @Override
    public void start() throws RemoteException, AlreadyBoundException {

    }

    @Override
    public void stop() throws RemoteException, NotBoundException {

    }

    @Override
    public void restart() throws RemoteException, NotBoundException, AlreadyBoundException {

    }

    @Override
    public String status(String printer) throws RemoteException {
        return null;
    }

    @Override
    public String readConfig(String parameter) throws RemoteException {
        return null;
    }

    @Override
    public void setConfig(String parameter, String value) throws RemoteException {

    }
}
