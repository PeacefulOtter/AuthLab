package com.app;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class Server implements PrintService {
    public static void main(String[] args) throws UnknownHostException, MalformedURLException, RemoteException {
        System.setProperty("java.rmi.server.hostname", "127.0.0.1");
        InformationImpl informationImpl = new InformationImpl();
        String url = "rmi://" + InetAddress.getLocalHost().getHostAddress() + "/TestRMI";
        System.out.println("Enregistrement de l'objet avec l'url : " + url);
        Naming.rebind(url, informationImpl);
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
    public void start() throws RemoteException {

    }

    @Override
    public void stop() throws RemoteException {

    }

    @Override
    public void restart() throws RemoteException {

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
