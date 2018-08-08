package com.fieldaware.processors;

import com.fieldaware.entities.ProfileRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/***********************************
 * Profiler is a Singleton that contains a dictionary - profileRecords - that records results of all timed functions
 * Any function can be timed using lambda expression as follows
 * Profiler.getInstance().time(() -> logProcessor.getLogLevel(logProcessor.getLogEntries(), in.next().toUpperCase()), "getLogLevel");
            This runs the function as normal, but records time taken and function name in profileRecords List

    printTestReport(String functionName) - This will print out function stats as requested for a given function
    printFunctionMin(List<ProfileRecord> filteredRecordsByFunction) - Will return min time taken to complete given function
    printFunctionMax(List<ProfileRecord> filteredRecordsByFunction) - Will return max time taken to complete given function
    printFunctionAverage(List<ProfileRecord> filteredRecordsByFunction) - Will return average time taken to complete given function

 NB!! -> I noticed that your examples were rounded to 2 decimal places. I've not rounded to 2 dp as most function times were so low
 the rounding would be inaccurate, and often 0.00
 */

public class Profiler {

    private static final Profiler instance = new Profiler();

    private List<ProfileRecord> profileRecords = new ArrayList<>();

    private Profiler() {
    }

    // Runtime initialization
    // By defualt ThreadSafe
    public static Profiler getInstance() {
        return instance;
    }

    public void time(Runnable block, String functionSurce) {
        long start = System.nanoTime();
        try {
            block.run();
        } finally {
            long end = System.nanoTime();
            profileRecords.add(new ProfileRecord((end-start)/1.0e9, functionSurce));
        }
    }

    public String printTestReport(String functionName){
        List<ProfileRecord> filteredRecordsByFunction = profileRecords.stream().filter(record -> {
            return record.getFunctionName().equals(functionName);
        }).collect(Collectors.toList());

       String report = "\nFunction: " + functionName +"\n";
       report += "NumSamples: " + filteredRecordsByFunction.size() + "\n";
       report += "Min: " + printFunctionMin(filteredRecordsByFunction) + " secs" + "\n";
       report += "Max: " + printFunctionMax(filteredRecordsByFunction) + " secs" + "\n";
       report += "Average: " + printFunctionAverage(filteredRecordsByFunction) + " secs" + "\n";

       return report;
    }

    public Double printFunctionMin(List<ProfileRecord> filteredRecordsByFunction){
        return filteredRecordsByFunction.stream().mapToDouble(record -> record.getFunctionTime()).min().getAsDouble();
    }

    public Double printFunctionMax(List<ProfileRecord> filteredRecordsByFunction){
        return filteredRecordsByFunction.stream().mapToDouble(record -> record.getFunctionTime()).max().getAsDouble();
    }

    public Double printFunctionAverage(List<ProfileRecord> filteredRecordsByFunction){
        return filteredRecordsByFunction.stream().mapToDouble(record -> record.getFunctionTime()).average().getAsDouble();
    }
    public List<ProfileRecord> getProfileRecords() {
        return profileRecords;
    }
}
