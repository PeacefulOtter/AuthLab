package com.app.server;

import com.app.Logger;
import com.app.PublicKeys;
import com.app.server.IO.IOHandler;
import com.app.server.control.Control;

import java.io.*;
import java.util.*;

public class AuthService
{
    private static final String DATABASE_FILE = "credentials.txt";
    private static final String DATABASE_FILE_CHANGED = "credentials_Changed.txt";
    private static final String DATABASE_FILE_TEMP = "credentials_Temp.txt";

    private final Control controlPolicy;
    private int symKey; /* UNUSED: for DIFFIE-HELLMAN */

    protected AuthService(Control control)
    {
        this.controlPolicy = control;
    }

    /* UNUSED */
    public String establishSymKey(String message)
    {
        int clientPublicNumber = Integer.parseInt(message);
        Logger.log("Client", "Client public number " + clientPublicNumber);

        int privateKey = 3; // (int) Math.round(Math.random() * Settings.PRIVATE_KEY_RANGE);
        Logger.log("Server", "Private key " + privateKey );
        int publicNumber = (int) Math.pow(PublicKeys.GENERATOR.value, privateKey) % PublicKeys.PRIME.value;
        Logger.log("Server", "Public number " + publicNumber );

        symKey = (int) Math.pow(clientPublicNumber, privateKey) % PublicKeys.PRIME.value;
        Logger.log("Server", "Symmetric key " + symKey );

        return String.valueOf(publicNumber);
    }

    private boolean findUser( String username )
    {
        int count = IOHandler.intRead( DATABASE_FILE, (reader) -> (int) reader.lines().filter(line -> {
            String[] split = line.split(" ");
            String lineUserHash = split[0];
            String salt = split[2];
            String userHash = HashUtils.getHash( username, salt );
            return userHash.contentEquals(lineUserHash);
        }).count());

        return count > 0;
    }

    private boolean verifyUser( String username, String password )
    {
        int count = IOHandler.intRead( DATABASE_FILE, (reader) -> (int) reader.lines().filter(line -> {
            String[] split = line.split(" ");
            String lineUserHash = split[0];
            String linePwdHash = split[1];
            String salt = split[2];
            String userHash = HashUtils.getHash( username, salt );
            String pwdHash = HashUtils.getHash( password, salt );
            return userHash.contentEquals(lineUserHash) && pwdHash.contentEquals(linePwdHash);
        }).count());

        return count > 0;
    }

    public boolean changePermissions(String username, Set<String> rolesOrPerms) {
        // if ( !findUser( username ) )
        //     return false;

        Logger.log("AuthService - changePermissions", "Changing perms of " + username + " to roles / permissions " + rolesOrPerms.toString() );
        return controlPolicy.changePermissions(username, rolesOrPerms);
    }

    public boolean register(String username, String password, Set<String> rolesOrPerms)
    {
        if ( findUser( username ) )
            return false;

        // Adding the user to the credentials
        Logger.log("AuthService - Register", "Registering " + username + " with roles / permissions " + rolesOrPerms.toString() );
        IOHandler.append( DATABASE_FILE_CHANGED, (line) -> {
            String hash = HashUtils.getFileHash( username, password );
            Logger.log("AuthService - Register", "username + password salted hash: " + hash );
            return hash;
        });
        // now adding it to the access control - we let the policy handle it
        return controlPolicy.register( username, rolesOrPerms );
    }

    public boolean unregister( String username )
    {
        if ( findUser(username) )
            return false;

        Logger.log("AuthService - Unregister", "Unregistering " + username );
        // remove from credentials
        boolean removedCredentials = IOHandler.readWrite( DATABASE_FILE_CHANGED, DATABASE_FILE_TEMP, username );
        // remove from access control - we let the policy handle it
        return removedCredentials && controlPolicy.unregister( username );
    }


    public Session login(String username, String password)
    {
        boolean found = verifyUser( username, password );
        Logger.log("AuthService - Login", "Credentials OK? " + found);
        if ( found )
        {
            Logger.log( "AuthService - Login", "Creating session key");
            Set<String> permissions = controlPolicy.getPermissions(username);
            Logger.log( "AuthService - Login", "User " + username + " has permissions " + permissions);
            return new Session(UUID.randomUUID(), permissions);
        }
        return null;
    }
}
