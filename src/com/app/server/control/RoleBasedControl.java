package com.app.server.control;

import com.app.server.HashUtils;
import com.app.server.IO.IOHandler;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class RoleBasedControl implements Control {
    private static final String ROLES_PERMISSIONS = "RolesPermissions.txt";

    private static final String ROLES_CONTROL = "RolesControlList.txt";
    private static final String ROLES_CONTROL_CHANGED = "RolesControlList_Changed.txt";
    private static final String ROLES_CONTROL_TEMP = "RolesControlList_Temp.txt";

    private final Map<String, Set<String>> roles = new HashMap<>();

    public RoleBasedControl()
    {
        retrieveRoles();
    }

    private void retrieveRoles()
    {
        IOHandler.intRead( ROLES_PERMISSIONS, (reader) -> {
            reader.lines().forEach( l -> {
                List<String> split = new ArrayList<>(Arrays.asList(l.split(" ")));
                String roleName = split.get(0);
                Set<String> permissions = new HashSet<>( split.subList(1, split.size()) );
                roles.put( roleName, permissions );
            });
            return 0;
        });
    }

    @Override
    public boolean changePermissions(String username, Set<String> rolesOrPerms)
    {
        return IOHandler.readWriteReplace( ROLES_CONTROL_CHANGED, ROLES_CONTROL_TEMP, username, username + " " + rolesOrPerms.stream().reduce("", (a, b) -> b + " " + a) );
    }


    @Override
    public Set<String> getPermissions(String username )
    {
        return IOHandler.setRead( ROLES_CONTROL, (reader) -> {
            Set<String> userRoles = reader.lines()
                    // filter to only get the line where the username is
                    .filter(line -> {
                        String[] split = line.split( " " );
                        String lineUsername = split[0];
                        return lineUsername.contentEquals(username);
                    })
                    // we split that line by spaces, the first element is the username and the remaining are the roles
                    .map( line -> {
                        List<String> split = Arrays.asList( line.split( " ") );
                        List<String> splitList = new ArrayList<>(split);
                        return splitList.subList( 1, split.size() );
                    } )
                    .flatMap(List::stream)
                    .collect(Collectors.toSet());

            return userRoles.stream()
                    .map(roles::get)
                    .flatMap(Set::stream)
                    .collect(Collectors.toSet());
        });
    }

    @Override
    public boolean register(String username, Set<String> roles)
    {
        return addUser( username, roles, ROLES_CONTROL_CHANGED );
    }

    @Override
    public boolean unregister(String username)
    {
        return removeUser( username, ROLES_CONTROL_CHANGED, ROLES_CONTROL_TEMP );
    }
}
