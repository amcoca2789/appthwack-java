package com.appthwack.appthwack;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.sun.jersey.api.client.WebResource;


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
	
	public AppThwackDevicePool() {
		
	}
	
	public void setRoot(WebResource root) {
		this.root = root;
	}
	
	public String toString() {
		return String.format("%s/devicepool/%d", root, id);
	}
}
