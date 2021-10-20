package com.app;

import java.io.FileNotFoundException;
import java.rmi.*;

public interface RemoteServer extends Remote
{
    String print(String fileName, String printer) throws RemoteException;
    String queue(String printer) throws RemoteException;
    String topQueue(String printer, int job) throws RemoteException;
    String start() throws RemoteException; // was void
    String stop() throws RemoteException; // was void
    String restart() throws RemoteException; // was void
    String status(String printer) throws RemoteException;
    String readConfig(String parameter) throws RemoteException;
    String setConfig(String parameter, String value) throws RemoteException;

    /* AUTH */
    String exchangePublicNumbers(String message) throws RemoteException;
    boolean register(String message) throws RemoteException;
    String login(String message) throws RemoteException;
}