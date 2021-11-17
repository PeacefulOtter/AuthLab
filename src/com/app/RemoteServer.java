package com.app;

import java.rmi.*;
import java.util.UUID;

public interface RemoteServer extends Remote
{
    String print(UUID id, String username, String fileName, String printer) throws RemoteException;
    String queue(UUID id, String username, String printer) throws RemoteException;
    String topQueue(UUID id, String username, String printer, int job) throws RemoteException;
    String start(UUID id, String username) throws RemoteException; // was void
    String stop(UUID id, String username) throws RemoteException; // was void
    String restart(UUID id, String username) throws RemoteException; // was void
    String status(UUID id, String username, String printer) throws RemoteException;
    String readConfig(UUID id, String username, String parameter) throws RemoteException;
    String setConfig(UUID id, String username, String parameter, String value) throws RemoteException;

    /* AUTH */
    String establishSymKey(String message) throws RemoteException;
    UUID login(String username, String password) throws RemoteException;
}