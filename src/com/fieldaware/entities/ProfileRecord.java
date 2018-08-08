package com.fieldaware.entities;

public class ProfileRecord {
    Double functionTime;
    String functionName;

    public ProfileRecord(Double functionTime, String functionName){
        this.functionTime = functionTime;
        this.functionName = functionName;
    }

    public Double getFunctionTime() {
        return functionTime;
    }

    public void setFunctionTime(Double functionTime) {
        this.functionTime = functionTime;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

}
