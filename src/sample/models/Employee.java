package sample.models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Employee {

    private final LongProperty employeeId;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty email;
    private final StringProperty hashedPassword;
    private final BooleanProperty isMainManager;
    private final LongProperty jobId;
    private final LongProperty officeId;

    public Employee(Long employeeId, String firstName, String lastName, String email, String hashedPassword, Boolean isMainManager,
                    Long jobId, Long officeId){
        this.employeeId = new SimpleLongProperty(employeeId);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
        this.hashedPassword = new SimpleStringProperty(hashedPassword);
        this.isMainManager = new SimpleBooleanProperty(isMainManager);
        this.jobId = new SimpleLongProperty(jobId);
        this.officeId = new SimpleLongProperty(officeId);
    }

    public Employee(String firstName, String lastName, String email, String password){
        this.employeeId = null;
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
        this.hashedPassword = new SimpleStringProperty(password);
        this.isMainManager = null;
        this.jobId = null;
        this.officeId = null;
    }

    public Employee(){this(null, null, null ,null);}

    public long getEmployeeId() {
        return employeeId.get();
    }

    public LongProperty employeeIdProperty() {
        return employeeId;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getHashedPassword() {
        return hashedPassword.get();
    }

    public Boolean getIsMainManager() {
        return isMainManager.get();
    }

    public Long getOfficeId() {
        return officeId.get();
    }

    public Long getJobId() {return jobId.get();}


    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword.set(hashedPassword);
    }

    public void setIsMainManager(Boolean isMainManager) {
        this.isMainManager.set(isMainManager);
    }

    public void setJobId(Long jobId) {
        this.jobId.set(jobId);
    }

    public void setOfficeId(Long officeId) {
        this.officeId.set(officeId);
    }


    public StringProperty getFirstNameProperty() {
        return firstName;
    }

    public StringProperty getLastNameProperty() {
        return lastName;
    }

    public StringProperty getEmailProperty() {
        return email;
    }

    public StringProperty getHashedPasswordProperty() {
        return hashedPassword;
    }

    public BooleanProperty getIsMainManagerProperty() {
        return isMainManager;
    }

    public LongProperty getJobIdProperty() {return jobId;}

    public LongProperty getOfficeIdProperty() {return officeId;}


}
