package com.fieldaware.processors;

import com.fieldaware.entities.LogEntry;
import org.junit.Before;
import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class LogProcessorTest {

    private LogProcessor logProcessor;
    private List<String> logs;
    private List<LogEntry> logEntries;

    @Before
    public void setUp() throws Exception {
        logProcessor = new LogProcessor();
        logs = new ArrayList<>();
        logs.add("2012-09-13 16:04:22 DEBUG SID:34523 BID:1329 RID:65d33 'Starting new session'");
        logs.add("2012-09-13 16:04:30 DEBUG SID:34523 BID:1329 RID:54f22 'Authenticating User'");
        logs.add("2012-09-13 16:05:30 DEBUG SID:42111 BID:319 RID:65a23 'Starting new session'");
        logs.add("2012-09-13 16:04:50 ERROR SID:34523 BID:1329 RID:54ff3 'Missing Authentication token '");
        logs.add("2012-09-13 16:05:31 DEBUG SID:42111 BID:319 RID:86472 'Authenticating User'");
        logs.add("2012-09-13 16:05:31 DEBUG SID:42111 BID:319 RID:7a323 'Deleting asset with ID 543234'");
        logs.add("2012-09-13 16:05:32 WARN SID:42111 BID:319 RID:7a323 'Invalid asset ID'");

        logEntries = logProcessor.processLogEntryList(logs);
    }

    @Test
    public void processLogEntryListTest() throws Exception {

        assertEquals(7, logEntries.size());
    }

    @Test
    public void processLogEntryTest() throws Exception {
        LogEntry logEntry = logProcessor.processLogEntry(logs.get(2));

        assertEquals("DEBUG", logEntry.getLogLevel());
        assertEquals("SID:42111", logEntry.getSessionID());
        assertNotEquals("'Starting session'", logEntry.getMessage());
    }

    @Test
    public void getLogLevelTest() throws Exception {

        assertEquals(1, logProcessor.getLogLevel(logEntries, "WARN").size());
        assertEquals(5, logProcessor.getLogLevel(logEntries, "DEBUG").size());
    }

    @Test
    public void getBusinessTest() throws Exception {

        assertEquals(4, logProcessor.getBusiness(logEntries, "BID:319").size());
        assertEquals(0, logProcessor.getBusiness(logEntries, "BID:099").size());
    }

    @Test
    public void getSessionTest() throws Exception {

        assertEquals(4, logProcessor.getSession(logEntries, "SID:42111").size());
        assertEquals(3, logProcessor.getSession(logEntries, "SID:34523").size());
    }

    @Test
    public void getInDateRangeTest() throws Exception {

        assertEquals(4, logProcessor.getInDateRange(logEntries, ZonedDateTime.now(), ZonedDateTime.of(2012, 9, 13, 16,05,01,01, ZoneId.systemDefault())).size());
    }



}
