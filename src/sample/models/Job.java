package sample.models;

import com.google.gson.Gson;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Job.
 */
public class Job {

    private final LongProperty jobId;
    private final StringProperty jobName;

    /**
     * Instantiates a new Job.
     *
     * @param jobId   the job id
     * @param jobName the job name
     */
    public Job(Long jobId, String jobName){
        this.jobId = new SimpleLongProperty(jobId);
        this.jobName = new SimpleStringProperty(jobName);
    }

    /**
     * Instantiates a new Job.
     *
     * @param jobName the job name
     */
    public Job(String jobName){
        this.jobId = null;
        this.jobName = new SimpleStringProperty(jobName);
    }

    /**
     * Instantiates a new Job.
     */
    public Job(){ this(null);}

    /**
     * Gets job id.
     *
     * @return the job id
     */
    public long getJobId() {
        return jobId.get();
    }

    /**
     * Job id property long property.
     *
     * @return the long property
     */
    public LongProperty jobIdProperty() {
        return jobId;
    }

    /**
     * Gets job name.
     *
     * @return the job name
     */
    public String getJobName() {
        return jobName.get();
    }

    /**
     * Job name property string property.
     *
     * @return the string property
     */
    public StringProperty jobNameProperty() {
        return jobName;
    }

    /**
     * Sets job name.
     *
     * @param jobName the job name
     */
    public void setJobName(String jobName) {
        this.jobName.set(jobName);
    }

    /**
     * To json string.
     *
     * @return the string
     */
    public String toJson() {

        Map<String, String> map = new HashMap<>();
        if (jobId == null){
            map.put("jobId", null);
        } else{
            map.put("jobId", String.valueOf(jobId.get()));
        }
        map.put("jobName", String.valueOf(jobName.get()));

        Gson gson = new Gson();
        return gson.toJson(map);
    }

    @Override
    public String toString() {
        return "{" +
                "jobId:" + jobId.get() +
                ", jobName:" + jobName.get() +
                '}';
    }
}
