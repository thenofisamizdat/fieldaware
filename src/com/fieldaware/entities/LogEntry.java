package com.fieldaware.entities;

import java.time.ZonedDateTime;

public class LogEntry{

    private ZonedDateTime date;
    private String logLevel;
    private String sessionID;
    private String businessID;
    private String requestID;
    private String message;

    public LogEntry(ZonedDateTime date, String logLevel, String sessionID, String businessID, String requestID, String message){
        this.date = date;
        this.logLevel = logLevel;
        this.sessionID = sessionID;
        this.businessID = businessID;
        this.requestID = requestID;
        this.message = message;
    }

    public String toString(){
        return getDate().toString() + " " + getLogLevel() + " " + getSessionID() + " " + getBusinessID() + " " + getRequestID() + " " + getMessage();
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getBusinessID() {
        return businessID;
    }

    public void setBusinessID(String businessID) {
        this.businessID = businessID;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
