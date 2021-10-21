package com.app.client;

import com.app.PublicKeys;
import com.app.RemoteServer;
import com.app.Logger;
import com.app.Settings;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
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
    private static final String CREDENTIALS = USERNAME + ":::" + PASSWORD;

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
        boolean login = client.login();
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

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private String hashString( String msg )
    {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

        final byte[] hashBytes = messageDigest.digest(msg.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hashBytes);
    }

    private boolean register() throws RemoteException {

        boolean response = server.register(hashString(CREDENTIALS));
        Logger.log("Server", "Register: " + response );
        return response;
    }

    private boolean login() throws RemoteException {
        boolean res = server.login(hashString(CREDENTIALS));
        Logger.log("Server", "Login: " + res );
        return res;
    }
}
