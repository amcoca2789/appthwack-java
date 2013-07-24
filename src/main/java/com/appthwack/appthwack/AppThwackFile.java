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
	
	public String toString() {
		return String.format("File (%d)", id);
	}
}