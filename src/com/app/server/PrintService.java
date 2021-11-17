package com.app.server;

import com.app.Logger;

public class PrintService
{
    /**
     * prints file filename on the specified printer
     *
     * @param fileName is the name of the file that is printed
     * @param printer  is the name of the printer that should print
     */
    public String print(String fileName, String printer) {
        return Logger.log("PrintServer - print", "fileName: " + fileName + ", Printer: " + printer);
    }

    /**
     * lists the print queue for a given printer on the user's display in lines of the form <job number>   <file name>
     *
     * @param printer is the name of the printer that should print
     */
    public String queue(String printer) {
        return Logger.log("PrintServer - queue", "Printer: " + printer);
    }

    /**
     * moves job to the top of the queue
     *
     * @param printer is the name of the printer that should print
     * @param job     job
     */
    public String topQueue(String printer, int job)
    {
        return Logger.log("PrintServer - topQueue", "Printer: " + printer + ", Job: " + job );
    }

    /**
     * starts the print server
     *
     */
    public String start()
    {
        return Logger.log("PrintServer - start");
    }

    /**
     * stops the print server
     *
     */
    public String stop()
    {
        return Logger.log("PrintServer - stop");
    }

    /**
     * stops the print server, clears the print queue and starts the print server again
     */
    public String restart()
    {
        return Logger.log("PrintServer - restart");
    }

    /**
     * prints status of printer on the user's display
     *
     * @param printer is the name of the printer
     * @return status of printer on the user's display
     */
    public String status(String printer) {
        return Logger.log("PrintServer - status", "Printer: " + printer);
    }

    /**
     * prints the value of the parameter on the user's display
     *
     * @param parameter param
     * @return value of the parameter on the user's display
     */
    public String readConfig(String parameter)
    {
        return Logger.log("PrintServer - readConfig", "Parameter: " + parameter);
    }

    /**
     * sets the parameter to value
     *
     * @param parameter param
     * @param value     value
     */
    public String setConfig(String parameter, String value)
    {
        return Logger.log("PrintServer - setConfig", "Parameter: " + parameter + ", Value: " + value);
    }
}