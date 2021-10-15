package com.app.server;

import com.app.RemoteServer;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrintService extends UnicastRemoteObject implements RemoteServer {
    private static final long serialVersionUID = 2674880711467464646L;

    protected PrintService() throws RemoteException {
        super();
    }

    /**
     * prints file filename on the specified printer
     *
     * @param fileName is the name of the file that is printed
     * @param printer  is the name of the printer that should print
     * @throws RemoteException if error
     */
    public void print(String fileName, String printer) throws RemoteException {
        Logger.log("print", "fileName: " + fileName + ", Printer: " + printer);
    }
    /**
     * lists the print queue for a given printer on the user's display in lines of the form <job number>   <file name>
     *
     * @param printer is the name of the printer that should print
     * @throws RemoteException if error
     */
    public void queue(String printer) throws RemoteException {
        Logger.log("queue", "Printer: " + printer);
    }
    /**
     * moves job to the top of the queue
     *
     * @param printer is the name of the printer that should print
     * @param job     job
     */
    public void topQueue(String printer, int job) throws RemoteException {
        Logger.log("topQueue", "Printer: " + printer + ", Job: " + job );
    }
    /**
     * starts the print server
     *
     * @throws RemoteException       if error
     * @throws AlreadyBoundException if error
     */
    @Override
    public String start() throws RemoteException {
        Logger.log("start");
        return "start OK";
    }
    /**
     * stops the print server
     *
     * @throws RemoteException   if error
     * @throws NotBoundException if error
     */
    @Override
    public String stop() throws RemoteException {
        Logger.log("stop");
        return "stop OK";
    }
    /**
     * stops the print server, clears the print queue and starts the print server again
     *
     * @throws RemoteException       if error
     * @throws NotBoundException     if error
     * @throws AlreadyBoundException if error
     */
    @Override
    public String restart() throws RemoteException {
        Logger.log("restart");
        return "restart OK";
    }
    /**
     * prints status of printer on the user's display
     *
     * @param printer is the name of the printer
     * @return status of printer on the user's display
     * @throws RemoteException if error
     */
    @Override
    public String status(String printer) throws RemoteException {
        Logger.log("status", "Printer: " + printer);
        return "status {printer: " + printer + "} OK";
    }
    /**
     * prints the value of the parameter on the user's display
     *
     * @param parameter param
     * @return value of the parameter on the user's display
     * @throws RemoteException if error
     */
    @Override
    public String readConfig(String parameter) throws RemoteException {
        Logger.log("readConfig", "Parameter: " + parameter);
        return "readConfig {parameter: " + parameter + "} OK";
    }
    /**
     * sets the parameter to value
     *
     * @param parameter param
     * @param value     value
     * @throws RemoteException if error
     */
    @Override
    public String setConfig(String parameter, String value) throws RemoteException {
        Logger.log("setConfig", "Parameter: " + parameter + ", Value: " + value);
        return "setConfig {parameter: " + parameter + ", value: " + value + "} OK";
    }
}