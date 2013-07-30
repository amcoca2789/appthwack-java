package com.appthwack.appthwack;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.sun.jersey.api.client.WebResource;

import com.appthwack.appthwack.AppThwackProject;


/**
 * Represents a collection of devices used during a test run.
 * @author ahawker
 *
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown=true)
public class AppThwackDevicePool {
	public Integer id;
	public String name;
	
	@JsonIgnore
	private WebResource root;
	
	@JsonIgnore
	private AppThwackProject project;
	
	public AppThwackDevicePool() {
		
	}
	
	public void setRoot(WebResource root) {
		this.root = root;
	}
	
	public void setProject(AppThwackProject project) {
		this.project = project;
	}
	
	public String toString() {
		return String.format("devicepool/%d/%d", project.id, id);
	}
}
