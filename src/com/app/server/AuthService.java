package com.app.server;

import com.app.Logger;
import com.app.PublicKeys;

import java.io.*;

public class AuthService
{
    private static final String DATABASE_FILE = "database.txt";
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

    private boolean findUser( String hash )
    {
        long count = 0;

        try ( BufferedReader fis = new BufferedReader( new FileReader( "./res/" + DATABASE_FILE ) ) )
        {
            count = fis.lines().filter(hash::equals).count();
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }

        return count > 0;
    }

    public boolean register(String message)
    {
        // if user already is in the db, don't register it again
        boolean found = findUser( message );
        if ( found )
            return false;

        try ( FileOutputStream fos = new FileOutputStream("./res/" + DATABASE_FILE, true))
        {
            fos.write((message + "\n").getBytes());
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }

        Logger.log("Server", "Register completed" );
        return true;
    }

    public boolean login(String message)
    {
        boolean found = findUser( message );
        Logger.log("Server", "User exists? " + found);
        return found;
    }
}
