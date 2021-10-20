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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Client
{
    private static final String USERNAME = "datasec";
    private static final String PASSWORD = "bad_pwd";

    private final RemoteServer server;

    private Client( RemoteServer server )
    {
        this.server = server;
        System.out.println("Running client");
    }

    // Auth between client and server
    // Server must store passwords in a file (hash(pwd))
    // send hash(username, password)
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException
    {
        Remote remote = Naming.lookup("rmi://" + Settings.HOSTNAME + "/" + Settings.SUBDOMAIN );
        RemoteServer server = (RemoteServer) remote;

        Client client = new Client(server);
        int symKey = client.getSymKey();

        boolean registered = client.register();
    }


    private int getSymKey() throws RemoteException
    {
        int privateKey = 4; // (int) Math.round(Math.random() * Settings.PRIVATE_KEY_RANGE);
        Logger.log("Client", "Private key " + privateKey );
        int publicNumber = (int) Math.pow(PublicKeys.GENERATOR.value, privateKey) % PublicKeys.PRIME.value;
        Logger.log("Client", "Public number " + publicNumber );
        String serverPublicNumber = server.exchangePublicNumbers(String.valueOf(publicNumber));
        Logger.log("Server", "Public number " + serverPublicNumber );

        int symKey = (int) Math.pow(Integer.parseInt( serverPublicNumber ), privateKey) % PublicKeys.PRIME.value;
        Logger.log("Client", "Symmetric key " + symKey );
        return symKey;
    }

    private boolean register() throws RemoteException {
        String message = USERNAME + ":::" + PASSWORD;
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        messageDigest.update(message.getBytes());
        String stringHash = new String(messageDigest.digest());

        boolean response = server.register(stringHash);
        Logger.log("Server", "Register: " + response );
        return response;
    }
}
