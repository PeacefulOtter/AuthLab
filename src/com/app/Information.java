package com.app;

import java.rmi.*;

public interface Information extends Remote {
    String getInformation() throws RemoteException;
}