package sample.models;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.HashMap;
import java.util.Map;

public class Client {

    private final SimpleLongProperty clientId;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty driverLicense;
    private final SimpleStringProperty passport;
    private final SimpleStringProperty phone;
    private final SimpleBooleanProperty isBlackListed;

    private final ReadOnlyStringWrapper fullName = new ReadOnlyStringWrapper();


    public Client(Long id, String firstName, String lastName, String driverLicense, String passport,
                  String phone, Boolean isBlackListed){
        this.clientId = new SimpleLongProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.driverLicense = new SimpleStringProperty(driverLicense);
        this.passport = new SimpleStringProperty(passport);
        this.phone = new SimpleStringProperty(phone);
        this.isBlackListed = new SimpleBooleanProperty(isBlackListed);
        fullName.bind(Bindings.concat(firstName, " ", lastName));
    }

    public Client(String firstName, String lastName, String driverLicense, String passport, String phone){
        this.clientId = null;
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.driverLicense = new SimpleStringProperty(driverLicense);
        this.passport = new SimpleStringProperty(passport);
        this.phone = new SimpleStringProperty(phone);
        this.isBlackListed = new SimpleBooleanProperty();
        fullName.bind(Bindings.concat(firstName, " ", lastName));
    }

    public String getFullName() {
        return fullName.get();
    }

    public ReadOnlyStringWrapper fullNameProperty() {
        return fullName;
    }


    public Client() {this(null, null, null, null, null);}

    public long getClientId() {
        return clientId.get();
    }

    public SimpleLongProperty clientIdProperty() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId.set(clientId);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getDriverLicense() {
        return driverLicense.get();
    }

    public SimpleStringProperty driverLicenseProperty() {
        return driverLicense;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense.set(driverLicense);
    }

    public String getPassport() {
        return passport.get();
    }

    public SimpleStringProperty passportProperty() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport.set(passport);
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public boolean isIsBlackListed() {
        return isBlackListed.get();
    }

    public SimpleBooleanProperty isBlackListedProperty() {
        return isBlackListed;
    }

    public void setIsBlackListed(boolean isBlackListed) {
        this.isBlackListed.set(isBlackListed);
    }

    public String toJson() {

        Map<String, Object> map = new HashMap<>();
        if (clientId == null){
            map.put("clientId", null);
        } else{
            map.put("clientId", String.valueOf(clientId.get()));
        }
        map.put("lastName", String.valueOf(lastName.get()));
        map.put("firstName", String.valueOf(firstName.get()));
        map.put("phone", String.valueOf(phone.get()));
        map.put("driverLicense", String.valueOf(driverLicense.get()));
        map.put("passport", String.valueOf(passport.get()));
        map.put("isBlackListed", String.valueOf(isBlackListed.get()));

        Gson gson = new Gson();
        return gson.toJson(map);
    }


}
