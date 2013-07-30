package com.appthwack.appthwack;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * Represents a file which has been upload to AppThwack.
 * @author ahawker
 *
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown=true)
public class AppThwackFile {
	@JsonProperty("file_id")
	public Integer id; 
	
	public AppThwackFile() {
		
	}
	
	public AppThwackFile(Integer id) {
		this.id = id;
	}
	
	public String toString() {
		return String.format("file/%d", id);
	}
}