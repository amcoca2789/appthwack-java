package com.appthwack.appthwack;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.appthwack.appthwack.AppThwackDevicePool;
import com.appthwack.appthwack.AppThwackFile;
import com.appthwack.appthwack.AppThwackRun;

import javax.ws.rs.core.MediaType;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.multipart.FormDataMultiPart;


/**
 * Represents an AppThwack project.
 * @author ahawker
 *
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown=true)
public class AppThwackProject {
    public Integer id;
    public String name;
    public String url;

    @JsonIgnore
    private WebResource root;

    public AppThwackProject() {

    }

    /**
     * Schedule AppExplorer test run.
     * @param app AppThwackFile which represents Android apk uploaded to AppThwack
     * @param name name of this run which appears on AppThwack
     * @param pool AppThwackDevicePool which represents collection of devices to test on
     * @param explorerParams Map of optional params to tweak the AppThwack AppExplorer
     * @return AppThwackRun which represents the scheduled run
     * @throws AppThwackException
     */
    public AppThwackRun scheduleAppExplorerRun(AppThwackFile app, String name, AppThwackDevicePool pool, HashMap<String, String> explorerParams) throws AppThwackException {
        return scheduleApp(app, name, pool, explorerParams);
    }

    /**
     * Schedule built-in iOS test run.
     * @param app AppThwackFile which represents Android apk uploaded to AppThwack
     * @param name name of this run which appears on AppThwack
     * @param pool AppThwackDevicePool which represents collection of devices to test on
     * @return AppThwackRun which represents the scheduled run
     * @throws AppThwackException
     */
    public AppThwackRun scheduleBuiltinIOSRun(AppThwackFile app, String name, AppThwackDevicePool pool) throws AppThwackException {
        return scheduleApp(app, name, pool, null);
    }

    /**
     * Schedule Calabash test run.
     * @param app AppThwackFile which represents an app uploaded to AppThwack
     * @param scripts AppThwackFile which represents a .zip of calabash content uploaded to AppThwack
     * @param name name of this run which appears on AppThwack
     * @param pool AppThwackDevicePool which represents collection of devices to test on
     * @return AppThwackRun which represents the active run
     * @throws AppThwackException
     */
    public AppThwackRun scheduleCalabashRun(AppThwackFile app, AppThwackFile scripts, String name, AppThwackDevicePool pool) throws AppThwackException {
        return scheduleCalabashRun(app, scripts, name, pool, null);
    }

    /**
     * Schedule Calabash test run.
     * @param app AppThwackFile which represents an app uploaded to AppThwack
     * @param scripts AppThwackFile which represents a .zip of calabash content uploaded to AppThwack
     * @param name name of this run which appears on AppThwack
     * @param pool AppThwackDevicePool which represents collection of devices to test on
     * @param tags Tags to pass to Calabash
     * @return AppThwackRun which represents the active run
     * @throws AppThwackException
     */
    public AppThwackRun scheduleCalabashRun(AppThwackFile app, AppThwackFile scripts, String name, AppThwackDevicePool pool, String tags) throws AppThwackException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("calabash", Integer.toString(scripts.id));
        if (tags != null && !tags.isEmpty()) {
            params.put("calabashtags", tags);
        }
        return scheduleApp(app, name, pool, params);
    }

    /**
     * Schedule Android JUnit/Robotium test run.
     * @param app AppThwackFile which represents an app uploaded to AppThwack
     * @param testApp AppThwackFile which represents an Android test apk uploaded to AppThwack
     * @param name name of this run which appears on AppThwack
     * @param pool AppThwackDevicePool which represents collection of devices to test on
     * @return AppThwackRun which represents the scheduled run
     * @throws AppThwackException
     */
    public AppThwackRun scheduleJUnitRun(AppThwackFile app, AppThwackFile testApp, String name, AppThwackDevicePool pool) throws AppThwackException {
        return scheduleJUnitRun(app, testApp, name, pool, null);
    }

    /**
     * Schedule Android JUnit/Robotium test run.
     * @param app AppThwackFile which represents an app uploaded to AppThwack
     * @param testApp AppThwackFile which represents an Android test apk uploaded to AppThwack
     * @param name name of this run which appears on AppThwack
     * @param pool AppThwackDevicePool which represents collection of devices to test on
     * @param testFilter Filter specific TestCase/TestSuite to run
     * @return AppThwackRun which represents the scheduled run
     * @throws AppThwackException
     */
    public AppThwackRun scheduleJUnitRun(AppThwackFile app, AppThwackFile testApp, String name, AppThwackDevicePool pool, String testFilter) throws AppThwackException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("junit", Integer.toString(testApp.id));
        if (testFilter != null && !testFilter.isEmpty()) {
            params.put("testfilter", testFilter);
        }
        return scheduleApp(app, name, pool, params);
    }

    /**
     * Schedule Android UI Automator test run.
     * @param app AppThwackFile which represents an app uploaded to AppThwack
     * @param tests AppThwackFile which represents a JAR uploaded to AppThwack
     * @param name name of this run which appears on AppThwack
     * @param pool AppThwackDevicePool which represents collection of devices to test on
     * @param testFilter Filter specific TestCase/TestSuite to run
     * @return AppThwackRun which represents the scheduled run
     * @throws AppThwackException
     */
    public AppThwackRun scheduleUIAutomatorRun(AppThwackFile app, AppThwackFile tests, String name, AppThwackDevicePool pool) throws AppThwackException {
        return scheduleUIAutomatorRun(app, tests, name, pool, null);
    }

    /**
     * Schedule Android UI Automator test run.
     * @param app AppThwackFile which represents an app uploaded to AppThwack
     * @param tests AppThwackFile which represents a JAR uploaded to AppThwack
     * @param name name of this run which appears on AppThwack
     * @param pool AppThwackDevicePool which represents collection of devices to test on
     * @param testFilter Filter specific TestCase/TestSuite to run
     * @return AppThwackRun which represents the scheduled run
     * @throws AppThwackException
     */
    public AppThwackRun scheduleUIAutomatorRun(AppThwackFile app, AppThwackFile tests, String name, AppThwackDevicePool pool, String testFilter) throws AppThwackException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("uiautomator", Integer.toString(tests.id));
        if (testFilter != null && !testFilter.isEmpty()) {
            params.put("testfilter", testFilter);
        }
        return scheduleApp(app, name, pool, params);
    }

    /**
     * Schedule Android MonkeyTalk test run.
     * @param app AppThwackFile which represents an app uploaded to AppThwack
     * @param testApp AppThwackFile which represents an Android test apk uploaded to AppThwack
     * @param name name of this run which appears on AppThwack
     * @param pool AppThwackDevicePool which represents collection of devices to test on
     * @return AppThwackRun which represents the scheduled run
     * @throws AppThwackException
     */
    public AppThwackRun scheduleMonkeyTalkRun(AppThwackFile app, AppThwackFile testApp, String name, AppThwackDevicePool pool) throws AppThwackException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("monkeytalk", Integer.toString(testApp.id));
        return scheduleApp(app, name, pool, params);
    }

    /**
     * Schedule iOS UI Automation test run.
     * @param app AppThwackFile which represents an app uploaded to AppThwack
     * @param scripts AppThwackFile which represents UIA scripts uploaded to AppThwack
     * @param name name of this run which appears on AppThwack
     * @param pool AppThwackDevicePool which represents collection of devices to test on
     * @return AppThwackRun which represents the scheduled run
     * @throws AppThwackException
     */
    public AppThwackRun scheduleUIARun(AppThwackFile app, AppThwackFile scripts, String name, AppThwackDevicePool pool) throws AppThwackException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("uia", Integer.toString(scripts.id));
        return scheduleApp(app, name, pool, params);
    }

    /**
     * Schedule iOS KIF test run.
     * @param app AppThwackFile which represents an app uploaded to AppThwack
     * @param name name of this run which appears on AppThwack
     * @param pool AppThwackDevicePool which represents collection of devices to test on
     * @return AppThwackRun which represents the scheduled run
     * @throws AppThwackException
     */
    public AppThwackRun scheduleKIFRun(AppThwackFile app, String name, AppThwackDevicePool pool) throws AppThwackException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("kif", "");
        return scheduleApp(app, name, pool, params);
    }

    /**
     * Schedule iOS OCUnit test run.
     * @param app AppThwackFile which represents an iOS app to test
     * @param tests AppThwackFile which represents your OCUnit test bundle
     * @param name name of this run which appears on AppThwack
     * @param pool AppThwackDevicePool which represents collection of devices to test on
     * @return AppThwackRun which represents the scheduled run
     * @throws AppThwackException
     */
    public AppThwackRun scheduleOCUnitRun(AppThwackFile app, AppThwackFile tests, String name, AppThwackDevicePool pool) throws AppThwackException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("ocunit", Integer.toString(tests.id));
        return scheduleApp(app, name, pool, params);
    }

    /**
     * Schedule iOS XCTest test run.
     * @param app AppThwackFile which represents an iOS app to test
     * @param tests AppThwackFile which represents your XCTest test bundle
     * @param name name of this run which appears on AppThwack
     * @param pool AppThwackDevicePool which represents collection of devices to test on
     * @return AppThwackRun which represents the scheduled run
     * @throws AppThwackException
     */
    public AppThwackRun scheduleXCTestRun(AppThwackFile app, AppThwackFile tests, String name, AppThwackDevicePool pool) throws AppThwackException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("xctest", Integer.toString(tests.id));
        return scheduleApp(app, name, pool, params);
    }

    /**
     * Schedule an app test run.
     * @param app AppThwackFile which represents Android apk uploaded to AppThwack
     * @param testApp AppThwackFile which represents optional junit/robotium apk uploaded to AppThwack
     * @param name name of this run which appears on AppThwack
     * @param pool AppThwackDevicePool which represents collection of devices to test on
     * @param optionalParams Map of optional parameters to configure this run
     * @return AppThwackRun which represents the scheduled run
     * @throws AppThwackException
     */
    private AppThwackRun scheduleApp(AppThwackFile app, String name, AppThwackDevicePool pool, HashMap<String, String> optionalParams) throws AppThwackException {
        if(app == null) {
            throw new AppThwackException("app cannot be null");
        }
        if(name == null || name.isEmpty()) {
            throw new AppThwackException("run name cannot be null or empty");
        }
        return scheduleRun(name, Integer.toString(app.id), pool, optionalParams);
    }

    /**
     * Schedule a responsive web run.
     * @param name name of this run which appears on AppThwack
     * @param url URL to website to render in mobile browsers
     * @return AppThwackRun which represents the scheduled run
     */
    public AppThwackRun scheduleWebRun(String name, String url) {
        return scheduleRun(name, url, null, null);
    }

    /**
     * Schedule a native app or responsive web run.
     * @param name name of this run which appears on AppThwack
     * @param app upload id of native app or url of responsive site
     * @param pool AppThwackDevicePool which represents collection of devices to test on (native only)
     * @param optionalParams Map of optional parameters to configure this run
     * @return AppThwackRun which represents the scheduled run
     */
    private AppThwackRun scheduleRun(String name, String app, AppThwackDevicePool pool, HashMap<String, String> optionalParams) {
        FormDataMultiPart form = new FormDataMultiPart();
        form.field("project", Integer.toString(id));
        form.field("name", name);
        form.field("app", app);

        if(pool != null) {
            form.field("pool", Integer.toString(pool.id));
        }

        if(optionalParams != null) {
            for(Map.Entry<String, String> entry : optionalParams.entrySet()) {
                form.field(entry.getKey(), entry.getValue());
            }
        }

        AppThwackRun run = root.path("run").type(MediaType.MULTIPART_FORM_DATA).post(AppThwackRun.class, form);
        run.setRoot(root);
        run.setProject(this);
        return run;
    }

    /**
     * Creates a run object from a previous completed run.
     * @param runId Numeric id of previous run
     * @return AppThwackRun which represents a previously completed run.
     */
    public AppThwackRun getRun(Integer runId) {
        return new AppThwackRun(this, runId, root);
    }

    /**
     * Get an individual device pool for the given name.
     * @param name  name of the device pool we want.
     * @return device pool with the given name or null.
     */
    public AppThwackDevicePool getDevicePool(String name) {
        List<AppThwackDevicePool> pools = getDevicePools();
        for(AppThwackDevicePool p : pools) {
            if(p.name.equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Get an individual device pool for the given id.
     * @param id id of the device pool we want.
     * @return device pool with the given id or null.
     */
    public AppThwackDevicePool getDevicePool(Integer pid) {
        List<AppThwackDevicePool> pools = getDevicePools();
        for(AppThwackDevicePool p : pools) {
            if(p.id.equals(pid)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Get a list of all device pools tied to this account.
     * @return list of device pools.
     */
    public List<AppThwackDevicePool> getDevicePools() {
        List<AppThwackDevicePool> pools = root.path("devicepool").path(Integer.toString(id)).get(new GenericType<List<AppThwackDevicePool>>(){});
        for(AppThwackDevicePool p : pools) {
            p.setRoot(root);
            p.setProject(this);
        }
        return pools;
    }

    /**
     * Set the web resource for this project. This is used to perform additional HTTP calls.
     * @param root
     */
    @JsonIgnore
    public void setRoot(WebResource root) {
        this.root = root;
    }

    @Override
    public String toString() {
        return String.format("project/%s", url);
    }
}
