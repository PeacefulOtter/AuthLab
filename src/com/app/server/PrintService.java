package com.app.server;

import com.app.RemoteServer;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrintService extends UnicastRemoteObject implements RemoteServer
{
    private static final long serialVersionUID = 2674880711467464646L;

    private boolean CLIENT_AUTHENTICATED = false;

    protected PrintService() throws RemoteException
    {
        super();
        System.out.println("print service creation");
    }

    /**
     * prints file filename on the specified printer
     *
     * @param fileName is the name of the file that is printed
     * @param printer  is the name of the printer that should print
     * @throws RemoteException if error
     */
    @Override
    public String print(String fileName, String printer) throws RemoteException
    {
        return Logger.log("print", "fileName: " + fileName + ", Printer: " + printer);
    }

    /**
     * lists the print queue for a given printer on the user's display in lines of the form <job number>   <file name>
     *
     * @param printer is the name of the printer that should print
     * @throws RemoteException if error
     */
    @Override
    public String queue(String printer) throws RemoteException
    {
        return Logger.log("queue", "Printer: " + printer);
    }

    /**
     * moves job to the top of the queue
     *
     * @param printer is the name of the printer that should print
     * @param job     job
     */
    @Override
    public String topQueue(String printer, int job) throws RemoteException
    {
        return Logger.log("topQueue", "Printer: " + printer + ", Job: " + job );
    }

    /**
     * starts the print server
     *
     * @throws RemoteException       if error
     * @throws AlreadyBoundException if error
     */
    @Override
    public String start() throws RemoteException
    {
        return Logger.log("start");
    }

    /**
     * stops the print server
     *
     * @throws RemoteException   if error
     * @throws NotBoundException if error
     */
    @Override
    public String stop() throws RemoteException
    {
        return Logger.log("stop");
    }

    /**
     * stops the print server, clears the print queue and starts the print server again
     *
     * @throws RemoteException       if error
     * @throws NotBoundException     if error
     * @throws AlreadyBoundException if error
     */
    @Override
    public String restart() throws RemoteException
    {
        return Logger.log("restart");
    }

    /**
     * prints status of printer on the user's display
     *
     * @param printer is the name of the printer
     * @return status of printer on the user's display
     * @throws RemoteException if error
     */
    @Override
    public String status(String printer) throws RemoteException
    {
        return Logger.log("status", "Printer: " + printer);
    }

    /**
     * prints the value of the parameter on the user's display
     *
     * @param parameter param
     * @return value of the parameter on the user's display
     * @throws RemoteException if error
     */
    @Override
    public String readConfig(String parameter) throws RemoteException
    {
        return Logger.log("readConfig", "Parameter: " + parameter);
    }

    /**
     * sets the parameter to value
     *
     * @param parameter param
     * @param value     value
     * @throws RemoteException if error
     */
    @Override
    public String setConfig(String parameter, String value) throws RemoteException
    {
        return Logger.log("setConfig", "Parameter: " + parameter + ", Value: " + value);
    }
}