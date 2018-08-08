package com.fieldaware;

import com.fieldaware.processors.LogProcessor;
import com.fieldaware.processors.Menu;

/**********************************
 *  I've added a Main class so you can test from the command line
 *  If running from the command line, out/production/fieldaware is the root folder
 *  Command is java com.fieldaware.Main log.txt
 *
 *  NB - Normally I would not hard code the path as seen below with String hardPathFallback
 *  but I thought if you want to run from IntelliJ then just put the path to your log file here
 *
 *  Main runs the log processor on the log file then allows us to query the processed file through the CLI
 */

public class Main {

    /*************/
    public static final String hardPathFallback = "C:\\Users\\nbyrne\\Documents\\work\\FieldAwareTest\\fieldaware\\resources\\log.txt"; // Please change to correct file path if not using command line
    /*************/

    public static void main(String[] args) {
        LogProcessor logProcessor = new LogProcessor(args.length > 0 ? args[0] : hardPathFallback);
        Menu menu = new Menu(logProcessor);
   }


}

