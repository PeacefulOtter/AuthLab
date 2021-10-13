package com.app;

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
        System.out.println("Running server");
    }
}
