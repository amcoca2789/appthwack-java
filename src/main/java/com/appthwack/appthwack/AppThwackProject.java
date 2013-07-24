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
	 * Schedule an Android AppExplorer test run.
	 * @param app AppThwackFile which represents Android apk uploaded to AppThwack
	 * @param name name of this run which appears on AppThwack
	 * @param pool AppThwackDevicePool which represents collection of devices to test on
	 * @param explorerParams Map of optional params to tweak the AppThwack AppExplorer
	 * @return AppThwackRun which represents the active run
	 * @throws AppThwackException
	 */
	public AppThwackRun scheduleAppExplorerRun(AppThwackFile app, String name, AppThwackDevicePool pool, HashMap<String, String> explorerParams) throws AppThwackException {
		return scheduleAndroidRun(app, null, name, pool, explorerParams);
	}
	
	/**
	 * Schedule an Android Calabash test run.
	 * @param app AppThwackFile which represents Android apk uploaded to AppThwack
	 * @param scripts AppThwackFile which represents a .zip of calabash content uploaded to AppThwack
	 * @param name name of this run which appears on AppThwack
	 * @param pool AppThwackDevicePool which represents collection of devices to test on
	 * @return AppThwackRun which represents the active run
	 * @throws AppThwackException
	 */
	public AppThwackRun scheduleCalabashRun(AppThwackFile app, AppThwackFile scripts, String name, AppThwackDevicePool pool) throws AppThwackException {
		return scheduleAndroidRun(app, scripts, name, pool, null);
	}
	
	/**
	 * Schedule an Android JUnit/Robotium test run.
	 * @param app AppThwackFile which represents Android apk uploaded to AppThwack
	 * @param testApp AppThwackFile which represents an Android test apk uploaded to AppThwack
	 * @param name name of this run which appears on AppThwack
	 * @param pool AppThwackDevicePool which represents collection of devices to test on
	 * @return AppThwackRun which represents the active run
	 * @throws AppThwackException
	 */
	public AppThwackRun scheduleJUnitRun(AppThwackFile app, AppThwackFile testApp, String name, AppThwackDevicePool pool) throws AppThwackException {
		return scheduleAndroidRun(app, testApp, name, pool, null);
	}
	
	/**
	 * Schedule an Android app test run.
	 * @param app AppThwackFile which represents Android apk uploaded to AppThwack
	 * @param testApp AppThwackFile which represents optional junit/robotium apk uploaded to AppThwack
	 * @param name name of this run which appears on AppThwack
	 * @param pool AppThwackDevicePool which represents collection of devices to test on
	 * @param optionalParams Map of optional parameters to configure this run
	 * @return AppThwackRun which represents the active run
	 * @throws AppThwackException
	 */
	public AppThwackRun scheduleAndroidRun(AppThwackFile app, AppThwackFile testApp, String name, AppThwackDevicePool pool, HashMap<String, String> optionalParams) throws AppThwackException {
		if(app == null) {
			throw new AppThwackException("app cannot be null");
		}
		if(name == null || name.isEmpty()) {
			throw new AppThwackException("run name cannot be null or empty");
		}
		
		FormDataMultiPart form = new FormDataMultiPart();
		form.field("project", Integer.toString(id));
		form.field("name", name);
		form.field("app", Integer.toString(app.id));
		
		if(testApp != null) {
			form.field("junit", Integer.toString(testApp.id));
		}
		
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
		run.setProject(id);
		return run;
	}
	
	public AppThwackRun getRun(Integer runId) {
		return new AppThwackRun(id, runId, root);
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
	public AppThwackDevicePool getDevicePool(Integer id) {
		List<AppThwackDevicePool> pools = getDevicePools();
		for(AppThwackDevicePool p : pools) {
			if(p.id == id) {
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
		return root.path("devicepool").path(Integer.toString(id)).get(new GenericType<List<AppThwackDevicePool>>(){});
	}
	
	/**
	 * Set the web resource for this project. This is used to perform additional HTTP calls.
	 * @param root
	 */
	@JsonIgnore
	public void setRoot(WebResource root) {
		this.root = root;
	}
	
	public String toString() {
		return String.format("%s/project/%s", root, url);
	}
}
