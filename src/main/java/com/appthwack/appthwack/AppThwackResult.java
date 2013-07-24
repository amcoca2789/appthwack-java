package com.appthwack.appthwack;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.sun.jersey.api.client.WebResource;


/**
 * Represents the entire result of an individual test run.
 * @author ahawker
 *
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown=true)
public class AppThwackResult {
	@JsonProperty("warnings_by_job")
	public List<ResultContainer> warningsByJob;
	@JsonProperty("warnings_by_type")
	public List<ResultContainer> warningsByType;
	@JsonProperty("warnings_by_device")
	public List<ResultContainer> warningsByDevice;
	
	@JsonProperty("failures_by_job")
	public List<ResultContainer> failuresByJob;
	@JsonProperty("failures_by_type")
	public List<ResultContainer> failuresByType;
	@JsonProperty("failures_by_device")
	public List<ResultContainer> failuresByDevice;
	
	@JsonProperty("performance_summary")
	public PerformanceSummary performanceSummary;
	public List<PerformanceResultContainer> performance;
	
	public ResultSummary summary;
	
	public AppThwackResult() {
		
	}
	
	/**
	 * Represents the high level summary (stats) for a result.
	 * @author ahawker
	 *
	 */
	@JsonAutoDetect
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class ResultSummary {
		public Integer id;
		public String status;
		
		public Integer count;
		public Integer completed;
		
		public String name;
		public String initiator;
		
		public String result;		
		
		@JsonProperty("failure_count")
		public Integer failures;
		@JsonProperty("error_count")
		public Integer errors;
		@JsonProperty("pass_count")
		public Integer passes;
		@JsonProperty("warning_count")
		public Integer warnings;
		
		@JsonProperty("start_time")
		public String startTime;
		@JsonProperty("start_date")
		public String startDate;

		@JsonProperty("report_file")
		public String reportFile;
		
		public ResultSummary() {
			
		}
		
		public String toString() {
			return String.format("[%d] (%s) %s by %s => %s", id, status, name, initiator, result);
		}
	}
	
	/**
	 * Represents an individual result.
	 * @author ahawker
	 *
	 */
	@JsonAutoDetect
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class Result {
		public Integer id;
		public String name;
		public String message;
		public String description;
		
		public Result() {
			
		}
		
		public String toString() {
			return String.format("[%d] %s %s", id, name, message);
		}
	}
	
	/**
	 * Represents a collection of results which are grouped by device/job.
	 * @author ahawker
	 *
	 */
	@JsonAutoDetect
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class ResultContainer {
		public String id;
		public String name;
		public String description;
		public List<Result> results;
		
		public ResultContainer() {
			
		}
		
		public String toString() {
			return String.format("[%s] %s %s", id, name, description);
		}
	}

	/**
	 * Represents a device which performed a job.
	 * @author ahawker
	 *
	 */
	@JsonAutoDetect
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class Device {
		public Integer id;
		public String name;
		
		@JsonProperty("os_version")
		public String osVersion;
		
		public Device() {
			
		}
		
		public String toString() {
			return String.format("[%d] %s (%s)", id, name, osVersion);
		}
	}
	
	/**
	 * Represents an individual performance sampling.
	 * @author ahawker
	 *
	 */
	@JsonAutoDetect
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class PerformanceEntry {
		public String name;
		public String value;
		public String timestamp;
		
		public PerformanceEntry() {
			
		}
		
		public String toString() {
			return String.format("%s => %s", name, value);
		}
	}
	
	/**
	 * Represents a summary of many performance samples across the entire result.
	 * @author ahawker
	 *
	 */
	@JsonAutoDetect
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class PerformanceResult {
		public PerformanceEntry min;
		public PerformanceEntry max;
		public PerformanceEntry avg;
		
		public PerformanceResult() {
			
		}
		
		public String toString() {
			return String.format("%s < %s < %s", min, avg, max);
		}
	}
	
	/**
	 * Represents a summary of multiple performance types for a specific device.
	 * @author ahawker
	 *
	 */
	@JsonAutoDetect
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class PerformanceResultContainer {
		public PerformanceResult threads;
		public PerformanceResult cpu;
		public PerformanceResult memory;
		public Device device;
		
		public PerformanceResultContainer() {
			
		}
		
		public String toString() {
			return String.format("[%s] Threads: (%s) CPU: (%s) Memory: (%s)", device.name, threads, cpu, memory);
		}
	}
	
	/**
	 * Represents the high level performance summary for an specific device.
	 * @author ahawker
	 *
	 */
	@JsonAutoDetect
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class PerformanceResultSummary {
		public Device device;
		public String value;
		
		public PerformanceResultSummary() {
			
		}
		
		public String toString() {
			return String.format("%s => %s", device.name, value);
		}
	}
	
	/**
	 * Represents a collection of high level performance summaries for all devices.
	 * @author ahawker
	 *
	 */
	@JsonAutoDetect
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class PerformanceSummary {
		@JsonProperty("CPU_min")
		public PerformanceResultSummary cpuMin;
		@JsonProperty("CPU_max")
		public PerformanceResultSummary cpuMax;
		@JsonProperty("CPU_avg")
		public PerformanceResultSummary cpuAvg;
		
		@JsonProperty("Threads_min")
		public PerformanceResultSummary threadsMin;
		@JsonProperty("Threads_max")
		public PerformanceResultSummary threadsMax;
		@JsonProperty("Threads_avg")
		public PerformanceResultSummary threadsAvg;
		
		@JsonProperty("Memory_min")
		public PerformanceResultSummary memoryMin;
		@JsonProperty("Memory_max")
		public PerformanceResultSummary memoryMax;
		@JsonProperty("Memory_avg")
		public PerformanceResultSummary memoryAvg;
		
		public PerformanceSummary() {
			
		}
		
		public String toString() {
			return String.format("Threads: (%s, %s, %s) CPU: (%s, %s, %s) Memory: (%s, %s, %s)",
					threadsMin, threadsAvg, threadsMax, cpuMin, cpuAvg, cpuMax, memoryMin, memoryAvg, memoryMax);
		}
	}
	
	public String toString() {
		return summary.toString();
	}
}

