package sample.models;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.beans.property.*;

import java.util.HashMap;
import java.util.Map;

public class Employee {

    private final LongProperty employeeId;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty email;
    private final StringProperty hashedPassword;
    private final ObjectProperty<Job> job;
    private final ObjectProperty<Office> office;


    public Employee(Long employeeId, String firstName, String lastName, String email, String hashedPassword,
                    Job job, Office office){
        this.employeeId = new SimpleLongProperty(employeeId);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
        this.hashedPassword = new SimpleStringProperty(hashedPassword);
        this.job = new SimpleObjectProperty<Job>(job);
        this.office = new SimpleObjectProperty<Office>(office);
    }

    public Employee(String firstName, String lastName, String email, String password){
        this.employeeId = null;
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
        this.hashedPassword = new SimpleStringProperty(password);
        this.job = new SimpleObjectProperty<Job>();
        this.office = new SimpleObjectProperty<Office>();
    }

    public Employee(){this(null, null, null ,null);}

    public long getEmployeeId() {
        return employeeId.get();
    }

    public LongProperty employeeIdProperty() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId.set(employeeId);
    }


    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getHashedPassword() {
        return hashedPassword.get();
    }

    public StringProperty hashedPasswordProperty() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword.set(hashedPassword);
    }

    public Job getJob() {
        return job.get();
    }

    public ObjectProperty<Job> jobProperty() {
        return job;
    }

    public void setJob(Job job) {
        this.job.set(job);
    }

    public Office getOffice() {
        return office.get();
    }

    public ObjectProperty<Office> officeProperty() {
        return office;
    }

    public void setOffice(Office office) {
        this.office.set(office);
    }

    public String toJson() {

        Map<String, Object> map = new HashMap<>();
        if (employeeId == null){
            map.put("employeeId", null);
        } else{
            map.put("employeeId", String.valueOf(employeeId.get()));
        }
        map.put("lastName", String.valueOf(lastName.get()));
        map.put("firstName", String.valueOf(firstName.get()));
        map.put("email", String.valueOf(email.get()));
        map.put("hashedPassword", String.valueOf(hashedPassword.get()));
        map.put("job", new Gson().fromJson(job.get().toJson(), JsonObject.class));
        map.put("office", new Gson().fromJson(office.get().toJson(), JsonObject.class));

        Gson gson = new Gson();
        return gson.toJson(map);
    }

}
