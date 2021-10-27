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
    private static final String USERNAME = "zadzjidz";
    private static final String PASSWORD = "ijjijiji";

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
        int symKey = client.getSymKey();

        UUID session = client.register();
        UUID login = client.login();
        server.print(session, USERNAME, "file.txt", "MyPrinter");
    }


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

    private UUID register() throws RemoteException {

        UUID res = server.register(USERNAME, PASSWORD);
        Logger.log("Server", "Register: " + res );
        return res;
    }

    private UUID login() throws RemoteException {
        UUID res = server.login(USERNAME, PASSWORD);
        Logger.log("Server", "Login: " + res );
        return res;
    }
}
