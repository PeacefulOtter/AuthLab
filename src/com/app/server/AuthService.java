package com.app.server;

import com.app.HashUtils;
import com.app.Logger;
import com.app.PublicKeys;

import java.io.*;
import java.util.UUID;

public class AuthService
{
    private static final String DATABASE_FILE = "public_credentials.txt";
    private boolean clientAuthenticated = false;
    private int symKey;

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
        long count = 0;

        try ( BufferedReader fis = new BufferedReader( new FileReader( "./res/" + DATABASE_FILE ) ) )
        {
            count = fis.lines().filter(line -> {
                String[] split = line.split(" ");
                String lineUserHash = split[0];
                String salt = split[2];
                String userHash = HashUtils.getHash( username + salt );
                return userHash.contentEquals(lineUserHash);
            }).count();
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }

        return count > 0;
    }

    private boolean verifyUser( String username, String password )
    {
        long count = 0;

        try ( BufferedReader fis = new BufferedReader( new FileReader( "./res/" + DATABASE_FILE ) ) )
        {
            count = fis.lines().filter(line -> {
                String[] split = line.split(" ");
                String hash = split[1];
                String salt = split[2];
                String userHash = HashUtils.getHash( username + password + salt );
                return userHash.contentEquals(hash);
            }).count();
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }

        return count > 0;
    }

    public UUID register(String username, String password)
    {
        if ( findUser( username ) )
            return null;

        try ( FileOutputStream fos = new FileOutputStream("./res/" + DATABASE_FILE, true))
        {
            String hash = HashUtils.getFileHash( username, password );
            Logger.log("Register", "username + password salted hash: " + hash );
            fos.write((hash + "\n").getBytes());
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }

        Logger.log("Server", "Register completed" );
        return UUID.randomUUID();
    }

    public UUID login(String username, String password)
    {
        boolean found = verifyUser( username, password );
        Logger.log("Server", "User exists? " + found);
        if ( found )
            return UUID.randomUUID();
        return null;
    }
}
