package com.app.server;

import java.rmi.*;

public interface Information extends Remote {
    String getInformation() throws RemoteException;
}