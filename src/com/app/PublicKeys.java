package com.app;

public enum PublicKeys {
    PRIME(23),
    GENERATOR(9);

    public final int value;

    PublicKeys(int value )
    {
        this.value = value;
    }
}
