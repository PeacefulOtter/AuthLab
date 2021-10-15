package com.app.server;

public abstract class Logger
{
    public static void log(String tag)
    {
        System.out.println("["+tag+"] ");
    }

    public static void log(String tag, String content)
    {
        System.out.println("["+tag+"] " + content);
    }
}
