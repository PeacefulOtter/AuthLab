package com.app.server;

import com.app.Logger;
import com.app.PublicKeys;

import java.io.*;

public class AuthService
{
    private static final String DATABASE_FILE = "database.txt";
    private boolean clientAuthenticated = false;
    private int symKey;

    public String exchangePublicNumbers(String message)
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

    public boolean register(String message)
    {
        try ( FileOutputStream fos = new FileOutputStream("./res/" + DATABASE_FILE, true))
        {
            fos.write((message + "\n").getBytes());
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }

        Logger.log("Server", message);
        return true;
    }

    public boolean login(String message)
    {
        Logger.log("login", message);
        return true;
    }
}
