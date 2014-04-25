package com.appthwack.appthwack;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonProperty("passes_by_job")
    public List<ResultContainer> passesByJob;
    @JsonProperty("passes_by_type")
    public List<ResultContainer> passesByType;
    @JsonProperty("passes_by_device")
    public List<ResultContainer> passesByDevice;

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

    @JsonIgnore
    private transient AppThwackRun run;

    @JsonIgnore
    private transient AppThwackProject project;

    @JsonIgnore
    private String webUrl;

    public AppThwackResult() {

    }

    /**
     * Helper function which returns True if the given result has completed
     * execution and has its archives ready to download.
     * @return
     */
    public Boolean isCompleted() {
        return summary != null
                && summary.status == "completed"
                && summary.reportFile != null
                && !summary.reportFile.isEmpty();
    }

    /**
     * Set the AppThwackRun which owns this result.
     * @param run
     */
    public void setRun(AppThwackRun run) {
        this.run = run;
        this.project = run.getProject();
        this.webUrl = run.getWebUrl();
        if (passesByJob != null) {
            for (ResultContainer result : passesByJob) {
                result.setRun(run);
            }
        }
        if (passesByType != null) {
            for (ResultContainer result : passesByType) {
                result.setRun(run);
            }
        }
        if (passesByDevice != null) {
            for (ResultContainer result : passesByDevice) {
                result.setRun(run);
            }
        }
        if (warningsByJob != null) {
            for (ResultContainer result : warningsByJob) {
                result.setRun(run);
            }
        }
        if (warningsByType != null) {
            for (ResultContainer result : warningsByType) {
                result.setRun(run);
            }
        }
        if (warningsByDevice != null) {
            for (ResultContainer result : warningsByDevice) {
                result.setRun(run);
            }
        }
        if (failuresByJob != null) {
            for (ResultContainer result : failuresByJob) {
                result.setRun(run);
            }
        }
        if (failuresByType != null) {
            for (ResultContainer result : failuresByType) {
                result.setRun(run);
            }
        }
        if (failuresByDevice != null) {
            for (ResultContainer result : failuresByDevice) {
                result.setRun(run);
            }
        }
    }

    /**
     * Return URL to this result visible on the site.
     * Note: The result overview page is the same URL as that of the run.
     * @return
     */
    public String getWebUrl() {
        return webUrl;
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

        @JsonProperty("public_url")
        public String publicUrl;

        @JsonProperty("minutes_used")
        public Integer minutesUsed;

        @JsonIgnore
        private transient AppThwackRun run;

        @JsonIgnore
        private String webUrl;

        public void setRun(AppThwackRun run) {
            this.run = run;
            this.webUrl = run.getWebUrl();
        }

        public ResultSummary() {

        }

        /**
         * Return URL to this result visible on the site.
         * Note: This is just the result summary, so it points to the run/result overview page.
         * @return
         */
        public String getWebUrl() {
            return webUrl;
        }

        @Override
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

        @JsonIgnore
        private transient AppThwackRun run;

        @JsonIgnore
        private String webUrl;

        public void setRun(AppThwackRun run) {
            this.run = run;
            this.webUrl = String.format("%s/jobrun/%d", run.getWebUrl(), id);
        }

        public Result() {

        }

        /**
         * Return URL to this result visible on the site.
         * Note: This result object represents an individual test.
         * @return
         */
        public String getWebUrl() {
            return webUrl;
        }

        @Override
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

        @JsonIgnore
        private transient AppThwackRun run;

        @JsonIgnore
        private String webUrl;

        public ResultContainer() {

        }

        public void setRun(AppThwackRun run) {
            this.run = run;
            this.webUrl = String.format("%s/device/%s", run.getWebUrl(), id);
            for (Result result : results) {
                result.setRun(run);
            }
        }

        public String getWebUrl() {
            return webUrl;
        }

        @Override
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

        @Override
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

        @Override
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

        @Override
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

        @Override
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

        @Override
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

        @JsonProperty("AvgFrameDrawTime_min")
        public PerformanceResultSummary drawMin;
        @JsonProperty("AvgFrameDrawTime_max")
        public PerformanceResultSummary drawMax;
        @JsonProperty("AvgFrameDrawTime_avg")
        public PerformanceResultSummary drawAvg;

        @JsonProperty("FPS_min")
        public PerformanceResultSummary fpsMin;
        @JsonProperty("FPS_max")
        public PerformanceResultSummary fpsMax;
        @JsonProperty("FPS_avg")
        public PerformanceResultSummary fpsAvg;

        public PerformanceSummary() {

        }

        @Override
        public String toString() {
            return String.format("Threads: (%s, %s, %s) CPU: (%s, %s, %s) Memory: (%s, %s, %s)",
                    threadsMin, threadsAvg, threadsMax, cpuMin, cpuAvg, cpuMax, memoryMin, memoryAvg, memoryMax);
        }
    }

    @Override
    public String toString() {
        return summary.toString();
    }
}

