package com.appthwack.appthwack;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;
import java.util.HashMap;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.appthwack.appthwack.AppThwackProject;
import com.appthwack.appthwack.AppThwackApi;
import com.appthwack.appthwack.AppThwackFile;
import com.appthwack.appthwack.AppThwackRun;
import com.appthwack.appthwack.AppThwackException;

public class AppThwackTest extends TestCase {

    private static final String ApiKey = "DTOZZNWeCNWFWtuqqJEm14nnonVJMDXA9flmdvzg";
    private static final String Domain = "http://thwackbin.herokuapp.com";

    private AppThwackApi api;
    private List<AppThwackProject> projects;
    private AppThwackProject project;

    public AppThwackTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(AppThwackTest.class);
    }

    protected void setUp() {
        api = new AppThwackApi(ApiKey, Domain);
        projects = api.getProjects();
        project = projects.get(0);
    }

    /**
     * AppThwackApi.getProjects()
     */
    public void testGetProjects() {
        assertNotNull("getProjects returned null", projects);
        assertFalse("getProjects returned empty list", projects.isEmpty());
    }

    /**
     * AppThwackApi.getProject(String)
     */
    public void testGetProjectByName() {
        // Found
        String name = "My Project";
        AppThwackProject p1 = api.getProject(name);
        assertNotNull(String.format("getProject(%s) returned null", name), p1);
        assertEquals(String.format("getProject(%s) returned incorrect project", name), p1.name, name);

        // UpperCase Insensitive
        AppThwackProject p2 = api.getProject(name.toUpperCase());
        assertNotNull(String.format("getProject(%s) returned null", name.toUpperCase()), p2);
        assertEquals(String.format("getProject(%s) returned incorrect project", name.toUpperCase()), p2.name, name);

        // LowerCase Insensitive
        AppThwackProject p3 = api.getProject(name.toLowerCase());
        assertNotNull(String.format("getProject(%s) returned null", name.toLowerCase()), p3);
        assertEquals(String.format("getProject(%s) returned incorrect project", name.toLowerCase()), p3.name, name);

        // Not Found
        String bad = "This Doesn't Exist!";
        AppThwackProject p4 = api.getProject(bad);
        assertNull(String.format("getProject(%s) didn't return null for nonexistent project", bad), p4);
    }

    /**
     * AppThwackApi.getProject(Integer)
     */
    public void testGetProjectById() {
        // Found
        Integer id = 1;
        AppThwackProject p1 = api.getProject(id);
        assertNotNull(String.format("getProject(%d) returned null", id), p1);
        assertEquals(String.format("getProject(%d) returned incorrect project", id), p1.id, id);

        // Not Found
        Integer bad = -1;
        AppThwackProject p2 = api.getProject(bad);
        assertNull(String.format("getProject(%d) didn't return null for nonexistent project", bad), p2);
    }

    /**
     * AppThwackApi.uploadFile(String)
     */
    public void testUploadFromPath() {

    }
    
    /**
     * AppThwackApi.uploadFile(File)
     */
    public void testUploadFromFile() {

    }
    
    /**
     * AppThwackProject.getDevicePools()
     */
    public void testDevicePools() {
        List<AppThwackDevicePool> pools = project.getDevicePools();
        assertNotNull("getDevicePools returned null", pools);
        assertFalse("getDevicePools returned empty list", pools.isEmpty());
    }
    
    /**
     * AppThwackProject.getDevicePool(String)
     */
    public void testDevicePoolByName() {
        // Found
        String name = "The Usual Suspects";
        AppThwackDevicePool p1 = project.getDevicePool(name);
        assertNotNull(String.format("getDevicePool(%s) returned null", name), p1);
        assertEquals(String.format("getDevicePool(%s) returned incorrect pool", name), p1.name, name);

        // UpperCase Insensitive
        AppThwackDevicePool p2 = project.getDevicePool(name.toUpperCase());
        assertNotNull(String.format("getDevicePool(%s) returned null", name.toUpperCase()), p2);
        assertEquals(String.format("getDevicePool(%s) returned incorrect pool", name.toUpperCase()), p2.name, name);

        // LowerCase Insensitive
        AppThwackDevicePool p3 = project.getDevicePool(name.toLowerCase());
        assertNotNull(String.format("getDevicePool(%s) returned null", name.toLowerCase()), p3);
        assertEquals(String.format("getDevicePool(%s) returned incorrect pool", name.toLowerCase()), p3.name, name);

        // Not Found
        String bad = "This Doesn't Exist!";
        AppThwackDevicePool p4 = project.getDevicePool(bad);
        assertNull(String.format("getDevicePool(%s) didn't return null for nonexistent pool", bad), p4);
    }

    /**
     * AppThwackProject.getDevicePool(Integer)
     */
    public void testDevicePoolById() {
        AppThwackProject project = api.getProjects().get(0);

        // Found
        Integer id = 15;
        AppThwackDevicePool p1 = project.getDevicePool(id);
        assertNotNull(String.format("getDevicePool(%d) returned null", id), p1);
        assertEquals(String.format("getDevicePool(%d) returned incorrect pool", id), p1.id, id);

        // Not Found
        Integer bad = -1;
        AppThwackDevicePool p2 = project.getDevicePool(bad);
        assertNull(String.format("getDevicePool(%d) didn't return null for nonexistent pool", bad), p2);
    }

    /**
     * AppThwackProject.getRun(Integer)
     */
    public void testGetRun() {
        Integer id = 128;
        AppThwackRun run = project.getRun(id);
        assertNotNull(String.format("getRun(%d) returned null", id), run);
        assertEquals(String.format("getRun(%d) returned incorrect run", id), run.id, id);
    }
    
    /**
     * AppThwackRun.getResults()
     */
    public void testGetResults() {
        AppThwackRun run = project.getRun(128);
        AppThwackResult result = run.getResults();

        // Passes
        assertNotNull("passesByJob is null", result.passesByJob);
        assertNotNull("passesByType is null", result.passesByType);
        assertNotNull("passesByDevice is null", result.passesByDevice);

        // Warnings
        assertNotNull("warningsByJob is null", result.warningsByJob);
        assertNotNull("warningsByType is null", result.warningsByType);
        assertNotNull("warningsByDevice is null", result.warningsByDevice);

        // Failures
        assertNotNull("failuresByJob is null", result.failuresByJob);
        assertFalse("failuresByJob contains no data", result.failuresByJob.isEmpty());
        assertNotNull("failuresByType is null", result.failuresByType);
        assertFalse("failuresByType contains no data", result.failuresByType.isEmpty());
        assertNotNull("failuresByDevice is null", result.failuresByDevice);
        assertFalse("failuresByDevice contains no data", result.failuresByDevice.isEmpty());

        //  Peformance
        assertNotNull("performance is null", result.performance);
        assertFalse("performance contains no data", result.performance.isEmpty());
        assertNotNull("performanceSummary is null", result.performanceSummary);

        //  Summary
        assertNotNull("summary is null", result.summary);
        assertEquals(17, result.summary.failures.intValue());
        assertEquals(0, result.summary.errors.intValue());
        assertEquals(23, result.summary.passes.intValue());
        assertEquals(0, result.summary.warnings.intValue());
        assertEquals(40, result.summary.count.intValue());
        assertEquals(36, result.summary.completed.intValue());
        assertEquals("fail", result.summary.result);
        assertNotNull("reportFile is null", result.summary.reportFile);
        assertNotNull("minutesUsed is null", result.summary.minutesUsed);
    }

    /**
     * AppThwackRun.getStatus()
     */
    public void testGetResultsStatus() {
        AppThwackRun run = project.getRun(128);
        String expected = "completed";

        String status = run.getStatus();
        assertNotNull("getStatus returned null", status);
        assertEquals(String.format("getStatus returned incorrect status string: %s", status), expected, status);
    }

    /**
     * AppThwackRun.downloadResults()
     */
    public void testDownloadResults() throws IOException {

    }
}
