package com.app.server;

import com.app.Logger;
import com.app.PublicKeys;

public class AuthService
{
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

    public String register(String message)
    {
        return Logger.log("register", message);
    }

    public String login(String message)
    {
        return Logger.log("login", message);
    }
}
