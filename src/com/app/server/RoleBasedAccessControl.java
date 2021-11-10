package com.app.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public final class RoleBasedAccessControl
{
    private static final String ROLES_PERMISSIONS = "RolesPermissions.txt";
    private static final Map<String, Set<String>> roles = new HashMap<>();

    public static void retrieveRoles()
    {
        try ( BufferedReader fis = new BufferedReader( new FileReader( "./res/" + ROLES_PERMISSIONS ) ) )
        {
            fis.lines().forEach( l -> {
                List<String> split = new ArrayList<>(Arrays.asList(l.split(" ")));
                String roleName = split.get(0);
                Set<String> permissions = new HashSet<>( split.subList(1, split.size()) );
                roles.put( roleName, permissions );
            });
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        System.out.println(roles);
    }

    public static Map<String, Set<String>> getRoles() { return roles; }
}
