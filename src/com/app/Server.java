package com.app;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Server implements PrintService {
    public static void main(String[] args) {
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
