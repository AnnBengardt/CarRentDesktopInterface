package sample.models;

import com.google.gson.Gson;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.HashMap;
import java.util.Map;

public class Job {

    private final LongProperty jobId;
    private final StringProperty jobName;

    public Job(Long jobId, String jobName){
        this.jobId = new SimpleLongProperty(jobId);
        this.jobName = new SimpleStringProperty(jobName);
    }

    public Job(String jobName){
        this.jobId = null;
        this.jobName = new SimpleStringProperty(jobName);
    }

    public Job(){ this(null, null);}

    public long getJobId() {
        return jobId.get();
    }

    public LongProperty jobIdProperty() {
        return jobId;
    }

    public String getJobName() {
        return jobName.get();
    }

    public StringProperty jobNameProperty() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName.set(jobName);
    }

    public String toJson() {

        Map<String, String> map = new HashMap<>();
        map.put("jobId", String.valueOf(jobId.get()));
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
