package com.app.server.IO;

import java.io.*;
import java.util.Set;

public class IOHandler
{
    public static Set<String> setRead(String origin, SetReadCallback callback)
    {
        Set<String> res = null;
        try ( BufferedReader fis = new BufferedReader( new FileReader( "./res/" + origin ) ) )
        {
            res = callback.call(fis);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return res;
    }

    public static int intRead(String origin, IntReadCallback callback)
    {
        int res = 0;

        try ( BufferedReader fis = new BufferedReader( new FileReader( "./res/" + origin ) ) )
        {
            res = callback.call(fis);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return res;
    }

    public static boolean append(String origin, WriteCallback callback )
    {
        try ( FileOutputStream fos = new FileOutputStream("./res/" + origin, true))
        {
            String res = callback.call("" );
            fos.write((res + "\n").getBytes());
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean readWrite(String origin, String temp, String removePart)
    {
        File file = new File( "./res/" + origin );
        File tempFile = new File( "./res/" + temp );

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile)) )
        {
            String currentLine;
            while ((currentLine = reader.readLine()) != null)
            {
                String split[] = currentLine.split(" ");
                if (split[0].contentEquals(removePart)) continue;
                writer.write(currentLine + System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tempFile.renameTo(file);
    }
}
