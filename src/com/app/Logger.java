package com.app;

public abstract class Logger
{
    public static String log(String tag)
    {
        return log(tag, "");
    }

    public static String log(String tag, String content)
    {
        String message = "[" + tag + "] " + content;
        System.out.println(message);
        return message;
    }
}
