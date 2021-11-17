package com.app.server.control;

import com.app.server.IO.IOHandler;

import java.util.*;
import java.util.stream.Collectors;

public class AccessBasedControl implements Control {
    private static final String ACCESS_CONTROL = "AccessControlList.txt";
    private static final String ACCESS_CONTROL_CHANGED = "AccessControlList_Changed.txt";
    private static final String ACCESS_CONTROL_TEMP = "AccessControlList_Temp.txt";

    @Override
    public Set<String> getPermissions(String username)
    {
        return IOHandler.setRead( ACCESS_CONTROL, (reader) -> {
            List<String> filteredLines = reader.lines()
                    // filter to only get the line where the username is
                    .filter(line -> {
                        String[] split = line.split( " " );
                        String lineUsername = split[0];
                        return lineUsername.contentEquals(username);
                    }).collect(Collectors.toList());

            // we split that line by spaces, the first element is the username and the remaining are the permissions
            String[] split = filteredLines.get(0).split( " ");
            List<String> lineList = new ArrayList<>( Arrays.asList( split ) );
            return new HashSet<>(lineList.subList( 1, lineList.size() )); // only keep the permissions
        });
    }

    @Override
    public boolean register(String username, Set<String> roles) {
        return false;
    }

    @Override
    public boolean unregister(String username) {
        return removeUser( username, ACCESS_CONTROL_CHANGED, ACCESS_CONTROL_TEMP );
    }
}
