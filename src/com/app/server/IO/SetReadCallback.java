package com.app.server.IO;

import java.io.BufferedReader;
import java.util.Set;

public interface SetReadCallback {
    Set<String> call(BufferedReader reader);
}
