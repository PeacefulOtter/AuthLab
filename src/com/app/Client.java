package com.app;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class Client {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        Remote r = Naming.lookup("rmi://10.0.0.13/TestRMI");
        System.out.println(r);
        if (r instanceof Information) {
            String s = ((Information) r).getInformation();
            System.out.println("chaine renvoyee = " + s);
        }
        System.out.println("Running client");
    }
}
