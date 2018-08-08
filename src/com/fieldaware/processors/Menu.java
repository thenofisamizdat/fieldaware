package com.fieldaware.processors;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Scanner;

/*******************************************************
 * This is a command line menu system to test the log filtering functions, and the profiler
 */

public class Menu {

    private LogProcessor logProcessor;
    private Scanner in;

    public Menu(LogProcessor logProcessor){
        this.logProcessor = logProcessor;
        in = new Scanner(System.in);
        createMenu();
    }

    private void createMenu(){
        System.out.println("\nSelect a function (1-5):");
        System.out.println("1 - getLogLevel");
        System.out.println("2 - getBusiness");
        System.out.println("3 - getSession");
        System.out.println("4 - getInDateRange");
        System.out.println("5 - View Profiler Stats");
        try{
            createSubMenu(in.nextInt());
        }
        catch (Exception e){
            System.out.println("Please enter a number 1-5");
            in = new Scanner(System.in);
            createMenu();
        }
    }
    private void createSubMenu(int submenu) {
        switch (submenu) {
            case 1:
                System.out.println("Choose Log Level with which to filter (eg - DEBUG)");
                try {
                    String logLevel = in.next().toUpperCase();
                    Profiler.getInstance().time(() -> logProcessor.getLogLevel(logProcessor.getLogEntries(), logLevel), "getLogLevel");
                }
                catch(Exception e){
                    System.out.println("No records found for this Log Level");
                    createMenu();
                }
                break;
            case 2:
                System.out.println("Choose Business ID with which to filter (eg - BID:1329)");
                try {
                    String business = in.next().toUpperCase();
                    Profiler.getInstance().time(() -> logProcessor.getBusiness(logProcessor.getLogEntries(), business), "getBusiness");
                }
                catch(Exception e){
                    System.out.println("No records found for this Business ID");
                    createMenu();
                }
                break;
            case 3:
                System.out.println("Choose Session ID with which to filter (eg - SID:34523)");
                try {
                    String session = in.next().toUpperCase();
                    Profiler.getInstance().time(() -> logProcessor.getSession(logProcessor.getLogEntries(), session), "getSession");
                }
                catch(Exception e){
                    System.out.println("No records found for this Session ID");
                    createMenu();
                }
                break;
            case 4:
                System.out.println("Find entries made before this date: (yyyy-MM-dd HH:mm:ss)");

                try {
                    LocalDate dateBefore = LocalDate.parse(in.next());
                    LocalTime timeBefore = LocalTime.parse(in.next());

                    ZonedDateTime dateTimeBefore = ZonedDateTime.of(dateBefore, timeBefore, ZoneId.systemDefault());

                    System.out.println("Find entries made after this date: (yyyy-MM-dd HH:mm:ss)");

                    LocalDate dateAfter = LocalDate.parse(in.next());
                    LocalTime timeAfter = LocalTime.parse(in.next());

                    ZonedDateTime dateTimeAfter = ZonedDateTime.of(dateAfter, timeAfter, ZoneId.systemDefault());
                    Profiler.getInstance().time(() -> logProcessor.getInDateRange(logProcessor.getLogEntries(), dateTimeBefore, dateTimeAfter), "getInDateRange");

                }
                catch(Exception e){
                    System.out.println("Invalid Date(s) entered");
                    createMenu();
                }
                break;
            case 5:
                System.out.println("Select Function to profile (getLogLevel/getBusiness/getSession/getInDateRange)");
                try {
                    String report = Profiler.getInstance().printTestReport(in.next());
                    System.out.println(report);
                }
                catch(Exception e){
                    System.out.println("No records found for this function");
                    createMenu();
                }
                break;
            default:
                createMenu();
                break;
        }
        createMenu();
    }
}
