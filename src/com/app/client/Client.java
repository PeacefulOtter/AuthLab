package com.app.client;

import com.app.PublicKeys;
import com.app.RemoteServer;
import com.app.Logger;
import com.app.Settings;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public class Client
{
    //: args
    // TODO: show role when login
    // TODO: register / unregister

    private final RemoteServer server;

    private Client( RemoteServer server )
    {
        this.server = server;
        System.out.println("Running client");
    }

    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException
    {
        Remote remote = Naming.lookup("rmi://" + Settings.HOSTNAME + "/" + Settings.SUBDOMAIN );
        RemoteServer server = (RemoteServer) remote;

        Client client = new Client(server);
        // int symKey = client.getSymKey(); // create diffie-hellman symmetric key

        String username = args[0];
        String password = args[1];
        System.out.println(username);
        System.out.println(password);
        UUID sessionID = client.login(username, password);

        server.queue(sessionID, username,"myPrinter");
    }

    // DIFFIE HELLMAN KEY GENERATION
    private int getSymKey() throws RemoteException
    {
        int privateKey = 4; // (int) Math.round(Math.random() * Settings.PRIVATE_KEY_RANGE);
        Logger.log("Client", "Private key " + privateKey );
        int publicNumber = (int) Math.pow(PublicKeys.GENERATOR.value, privateKey) % PublicKeys.PRIME.value;
        Logger.log("Client", "Public number " + publicNumber );
        String serverPublicNumber = server.establishSymKey(String.valueOf(publicNumber));
        Logger.log("Server", "Public number " + serverPublicNumber );

        int symKey = (int) Math.pow(Integer.parseInt( serverPublicNumber ), privateKey) % PublicKeys.PRIME.value;
        Logger.log("Client", "Symmetric key " + symKey );
        return symKey;
    }

    private UUID login(String username, String password) throws RemoteException
    {
        UUID res = server.login(username, password);
        Logger.log("Login", "UUID: " + res );
        return res;
    }
}
