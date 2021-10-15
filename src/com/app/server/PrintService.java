package com.app.server;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrintService extends Remote {

    /**
     * prints file filename on the specified printer
     *
     * @param fileName is the name of the file that is printed
     * @param printer  is the name of the printer that should print
     * @throws RemoteException if error
     */
    void print(String fileName, String printer) throws RemoteException;

    /**
     * lists the print queue for a given printer on the user's display in lines of the form <job number>   <file name>
     *
     * @param printer is the name of the printer that should print
     * @throws RemoteException if error
     */
    void queue(String printer) throws RemoteException;

    /**
     * moves job to the top of the queue
     *
     * @param printer is the name of the printer that should print
     * @param job     job
     */
    void topQueue(String printer, int job) throws RemoteException;

    /**
     * starts the print server
     *
     * @throws RemoteException       if error
     * @throws AlreadyBoundException if error
     */
    void start() throws RemoteException, AlreadyBoundException;

    /**
     * stops the print server
     *
     * @throws RemoteException   if error
     * @throws NotBoundException if error
     */
    void stop() throws RemoteException, NotBoundException;

    /**
     * stops the print server, clears the print queue and starts the print server again
     *
     * @throws RemoteException       if error
     * @throws NotBoundException     if error
     * @throws AlreadyBoundException if error
     */
    void restart() throws RemoteException, NotBoundException, AlreadyBoundException;

    /**
     * prints status of printer on the user's display
     *
     * @param printer is the name of the printer
     * @return status of printer on the user's display
     * @throws RemoteException if error
     */
    String status(String printer) throws RemoteException;

    /**
     * prints the value of the parameter on the user's display
     *
     * @param parameter param
     * @return value of the parameter on the user's display
     * @throws RemoteException if error
     */
    String readConfig(String parameter) throws RemoteException;

    /**
     * sets the parameter to value
     *
     * @param parameter param
     * @param value     value
     * @throws RemoteException if error
     */
    void setConfig(String parameter, String value) throws RemoteException;
}