package com.fieldaware.processors;

import com.fieldaware.entities.LogEntry;
import org.junit.Before;
import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class ProfilerTest {

    private LogProcessor logProcessor;
    private List<String> logs;
    private List<LogEntry> logEntries;

    @Before
    public void setUp() throws Exception {
        logProcessor = new LogProcessor();
        logs = new ArrayList<>();
        logEntries = new ArrayList<>();
        logs.add("2012-09-13 16:04:22 DEBUG SID:34523 BID:1329 RID:65d33 'Starting new session'");
        logs.add("2012-09-13 16:04:30 DEBUG SID:34523 BID:1329 RID:54f22 'Authenticating User'");
        logs.add("2012-09-13 16:05:30 DEBUG SID:42111 BID:319 RID:65a23 'Starting new session'");
        logs.add("2012-09-13 16:04:50 ERROR SID:34523 BID:1329 RID:54ff3 'Missing Authentication token '");
        logs.add("2012-09-13 16:05:31 DEBUG SID:42111 BID:319 RID:86472 'Authenticating User'");
        logs.add("2012-09-13 16:05:31 DEBUG SID:42111 BID:319 RID:7a323 'Deleting asset with ID 543234'");
        logs.add("2012-09-13 16:05:32 WARN SID:42111 BID:319 RID:7a323 'Invalid asset ID'");
        logEntries = logProcessor.processLogEntryList(logs);

        Profiler.getInstance().time(() -> logProcessor.getLogLevel(logEntries, "DEBUG"), "getLogLevel");
        Profiler.getInstance().time(() -> logProcessor.getSession(logEntries, "SID:34523"), "getSession");
        Profiler.getInstance().time(() -> logProcessor.getLogLevel(logEntries, "WARN"), "getLogLevel");
        Profiler.getInstance().time(() -> logProcessor.getInDateRange(logEntries, ZonedDateTime.now(), ZonedDateTime.of(2012, 9, 13, 16,05,01,01, ZoneId.systemDefault())), "getInDateRange");
    }

    @Test
    public void printReportTest() throws Exception {
        String report = Profiler.getInstance().printTestReport("getLogLevel");

        System.out.print(report);

        assertTrue(report.contains("Function: getLogLeve"));
    }

    @Test
    public void timeEntriesTest() throws Exception {

        assertEquals(4, Profiler.getInstance().getProfileRecords().size());
    }

}
