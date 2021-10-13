package com.app;

<<<<<<< HEAD
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class Server {
    public static void main(String[] args) throws UnknownHostException, MalformedURLException, RemoteException {
        System.setProperty("java.rmi.server.hostname","127.0.0.1");
        InformationImpl informationImpl = new InformationImpl();
        String url = "rmi://" + InetAddress.getLocalHost().getHostAddress() + "/TestRMI";
        System.out.println("Enregistrement de l'objet avec l'url : " + url);
        Naming.rebind(url, informationImpl);
=======
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Server implements PrintService {
    public static void main(String[] args) {
>>>>>>> 93d7b1e23789545677c43e1f0cfb318fdc4eae02
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
