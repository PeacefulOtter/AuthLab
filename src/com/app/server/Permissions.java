package com.app.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Permissions
{
    private static final String ACCESS_CONTROL = "AccessControlList.txt";
    private static final String ROLES_CONTROL = "RolesControlList.txt";

    private static final boolean USE_ACCESS_CONTROL_POLICY = true;

    public static Set<String> getPermissions( String username )
    {
        if ( USE_ACCESS_CONTROL_POLICY )
            return retrieveAccessControlPermissions( username );
        return retrieveRolesPermissions( username );
    }

    private static Set<String> retrieveAccessControlPermissions( String username )
    {
        Set<String> permissions;

        try ( BufferedReader fis = new BufferedReader( new FileReader( "./res/" + ACCESS_CONTROL ) ) )
        {
            // singleton
            List<String> filteredLines = fis.lines()
                    // filter to only get the line where the username is
                    .filter(line -> {
                        String[] split = line.split( " " );
                        String lineUsername = split[0];
                        return lineUsername.contentEquals(username);
                    }).collect(Collectors.toList());

            // we split that line by spaces, the first element is the username and the remaining are the permissions
            String[] split = filteredLines.get(0).split( " ");
            List<String> lineList = new ArrayList<>( Arrays.asList( split ) );
            permissions = new HashSet(lineList.subList( 1, lineList.size() )); // only keep the permissions
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }

        return permissions;
    }

    private static Set<String> retrieveRolesPermissions( String username )
    {
        Set<String> permissions;

        try ( BufferedReader fis = new BufferedReader( new FileReader( "./res/" + ROLES_CONTROL ) ) )
        {
            Set<String> roles = fis.lines()
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

            Map<String, Set<String>> rolesPermissions = RoleBasedAccessControl.getRoles();
            permissions = roles.stream()
                    .map(rolesPermissions::get)
                    .flatMap(Set::stream)
                    .collect(Collectors.toSet());
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }

        return permissions;
    }
}
