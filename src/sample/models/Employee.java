package sample.models;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.beans.property.*;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Employee.
 */
public class Employee {

    private final LongProperty employeeId;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty email;
    private final StringProperty hashedPassword;
    private final ObjectProperty<Job> job;
    private final ObjectProperty<Office> office;


    /**
     * Instantiates a new Employee.
     *
     * @param employeeId     the employee id
     * @param firstName      the first name
     * @param lastName       the last name
     * @param email          the email
     * @param hashedPassword the hashed password
     * @param job            the job
     * @param office         the office
     */
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

    /**
     * Instantiates a new Employee.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param email     the email
     * @param password  the password
     */
    public Employee(String firstName, String lastName, String email, String password){
        this.employeeId = null;
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
        this.hashedPassword = new SimpleStringProperty(password);
        this.job = new SimpleObjectProperty<Job>();
        this.office = new SimpleObjectProperty<Office>();
    }


    /**
     * Instantiates a new Employee.
     */
    public Employee(){this(null, null, null ,null);}

    /**
     * Gets employee id.
     *
     * @return the employee id
     */
    public long getEmployeeId() {
        return employeeId.get();
    }

    /**
     * Employee id property long property.
     *
     * @return the long property
     */
    public LongProperty employeeIdProperty() {
        return employeeId;
    }

    /**
     * Sets employee id.
     *
     * @param employeeId the employee id
     */
    public void setEmployeeId(long employeeId) {
        this.employeeId.set(employeeId);
    }


    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName.get();
    }

    /**
     * First name property string property.
     *
     * @return the string property
     */
    public StringProperty firstNameProperty() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName.get();
    }

    /**
     * Last name property string property.
     *
     * @return the string property
     */
    public StringProperty lastNameProperty() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email.get();
    }

    /**
     * Email property string property.
     *
     * @return the string property
     */
    public StringProperty emailProperty() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email.set(email);
    }

    /**
     * Gets hashed password.
     *
     * @return the hashed password
     */
    public String getHashedPassword() {
        return hashedPassword.get();
    }

    /**
     * Hashed password property string property.
     *
     * @return the string property
     */
    public StringProperty hashedPasswordProperty() {
        return hashedPassword;
    }

    /**
     * Sets hashed password.
     *
     * @param hashedPassword the hashed password
     */
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword.set(hashedPassword);
    }

    /**
     * Gets job.
     *
     * @return the job
     */
    public Job getJob() {
        return job.get();
    }

    /**
     * Job property object property.
     *
     * @return the object property
     */
    public ObjectProperty<Job> jobProperty() {
        return job;
    }

    /**
     * Sets job.
     *
     * @param job the job
     */
    public void setJob(Job job) {
        this.job.set(job);
    }

    /**
     * Gets office.
     *
     * @return the office
     */
    public Office getOffice() {
        return office.get();
    }

    /**
     * Office property object property.
     *
     * @return the object property
     */
    public ObjectProperty<Office> officeProperty() {
        return office;
    }

    /**
     * Sets office.
     *
     * @param office the office
     */
    public void setOffice(Office office) {
        this.office.set(office);
    }

    /**
     * To json string.
     *
     * @return the string
     */
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
