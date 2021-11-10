package com.app.server;

import java.util.Set;
import java.util.UUID;

public class Session
{
    private final UUID id;
    private final Set<String> permissions;

    public Session(UUID id, Set<String> permissions)
    {
        this.id = id;
        this.permissions = permissions;
    }

    public UUID getId() {
        return id;
    }

    public Set<String> getSessionPermissions() {
        return permissions;
    }
}
