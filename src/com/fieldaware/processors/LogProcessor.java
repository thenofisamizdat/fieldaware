package com.fieldaware.processors;

import com.fieldaware.entities.LogEntry;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**************************
 * The Log Processor attempts to read the contents of the log file at the specified path
 * If it can't find a file at said path it creates an empty log data structure
 *
 * processLogEntryList(List<String> logs) - uses stream().map() to perform processLogEntry() on each log entry and store in List
 * processLogEntry(String logEntry) - First separates the message string as this is has a unique split char seq
 *                                    Then splits string excluding msg string by space ' '
 *                                    Then creates a ZonedDateTime using first 2 tokens (date and time)
 *                                    and cretes a new LogEntry Entity with corresponding data
 * List<LogEntry> getLogLevel - Filters List of processed log entries by supplied Log Level String
 * List<LogEntry> getLBusiness - Filters List of processed log entries by supplied Business ID String
 * List<LogEntry> getSession - Filters List of processed log entries by supplied Session ID String
 * List<LogEntry> getInDateRange - Filters List of processed log entries by date range (date before and after)
 */

public class LogProcessor {

    private List<String> logs;
    private List<LogEntry> logEntries;

    public LogProcessor(){}
    public LogProcessor(String path){
        try{
             logs = Files.lines(Paths.get(path)).collect(Collectors.toList());
        }
        catch(Exception e){
            System.out.println("File not found. Check path and run again.");
            logs = new ArrayList<>();
        }
        finally{
            logEntries = processLogEntryList(logs);
        }
    }

    public List<LogEntry> processLogEntryList(List<String> logs){
        return logs.stream().map(log -> processLogEntry(log)).collect(Collectors.toList());
    }

    public LogEntry processLogEntry(String logEntry){
        String logMessage = logEntry.substring(logEntry.indexOf(" '")+1);
        String[] logStamps = logEntry.substring(0, logEntry.indexOf(" '")).split("\\s+");

        LocalDate date = LocalDate.parse(logStamps[0]);
        LocalTime time = LocalTime.parse(logStamps[1]);


        ZonedDateTime dateTime = ZonedDateTime.of(date ,time, ZoneId.systemDefault());

        String logLevel = logStamps[2];
        String sessionID = logStamps[3];
        String businessID = logStamps[4];
        String requestID = logStamps[5];

        return new LogEntry(dateTime, logLevel, sessionID, businessID, requestID, logMessage);
    }

    public List<LogEntry> getLogLevel(List<LogEntry> logEntries, String logLevel){
        List<LogEntry> filteredLog = logEntries.stream().filter(logEntry -> {return logEntry.getLogLevel().equals(logLevel);}).collect(Collectors.toList());
        filteredLog.forEach(System.out::println);
        return filteredLog;
    }
    public List<LogEntry> getBusiness(List<LogEntry> logEntries, String businessID){
        List<LogEntry> filteredLog = logEntries.stream().filter(logEntry -> {return logEntry.getBusinessID().equals(businessID);}).collect(Collectors.toList());
        filteredLog.forEach(System.out::println);
        return filteredLog;
    }
    public List<LogEntry> getSession(List<LogEntry> logEntries, String sessionID){
        List<LogEntry> filteredLog = logEntries.stream().filter(logEntry -> {return logEntry.getSessionID().equals(sessionID);}).collect(Collectors.toList());
        filteredLog.forEach(System.out::println);
        return filteredLog;
    }
    public List<LogEntry> getInDateRange(List<LogEntry> logEntries, ZonedDateTime beforeThisDate, ZonedDateTime afterThisDate){
        List<LogEntry> filteredLog = logEntries.stream().filter(logEntry -> {return logEntry.getDate().isAfter(afterThisDate);}).filter(logEntry -> {return logEntry.getDate().isBefore(beforeThisDate);}).collect(Collectors.toList());
        filteredLog.forEach(System.out::println);
        return filteredLog;
    }

    public List<String> getLogs() {
        return logs;
    }

    public void setLogs(List<String> logs) {
        this.logs = logs;
    }

    public List<LogEntry> getLogEntries() {
        return logEntries;
    }

    public void setLogEntries(List<LogEntry> logEntries) {
        this.logEntries = logEntries;
    }

}
