package com.app;

import java.rmi.*;

public interface RemoteServer extends Remote {
    String start() throws RemoteException; // was void
    String stop() throws RemoteException; // was void
    String restart() throws RemoteException; // was void
    String status(String printer) throws RemoteException;
    String readConfig(String parameter) throws RemoteException;
    String setConfig(String parameter, String value) throws RemoteException;
}