package com.app.server.control;

import com.app.server.IO.IOHandler;

import java.util.Set;

public interface Control
{
    Set<String> getPermissions(String username );
    boolean register( String username, Set<String> roles);
    boolean unregister( String username );

    default boolean addUser( String username, Set<String> roles, String file)
    {
        return IOHandler.append( file, (line) -> username + " " + roles.toString() );
    }

    default boolean removeUser( String username, String origin, String temp )
    {
        return IOHandler.readWrite( origin, temp, username );
    }
}
