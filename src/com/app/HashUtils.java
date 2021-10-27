package com.app;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class HashUtils {
    private static final Random RANDOM = new SecureRandom();

    private static String getNextSalt()
    {
        return String.valueOf(RANDOM.nextLong());
    }

    private static String bytesToHex(byte[] hash)
    {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for ( int i = 0; i < hash.length; i++ )
        {
            String hex = Integer.toHexString(0xff & hash[i]);
            if( hex.length() == 1 )
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static String hashString( String s )
    {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

        final byte[] hashBytes = messageDigest.digest(s.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hashBytes);
    }

    public static String getHash( String username, String password )
    {
        return hashString( username + password );
    }

    public static String getHash( String s )
    {
        return hashString( s );
    }

    public static String getFileHash(String username, String password )
    {
        String salt = getNextSalt();
        String userHash = getHash( username + salt );
        String credentialsHash = getHash( username + password + salt );
        return userHash + " " + credentialsHash + " " + salt;
    }

}
