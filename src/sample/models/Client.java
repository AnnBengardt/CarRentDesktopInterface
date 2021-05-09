package sample.models;

import com.google.gson.Gson;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Client.
 */
public class Client {

    private final SimpleLongProperty clientId;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty driverLicense;
    private final SimpleStringProperty passport;
    private final SimpleStringProperty phone;
    private final SimpleBooleanProperty isBlackListed;

    private final ReadOnlyStringWrapper fullName = new ReadOnlyStringWrapper();


    /**
     * Instantiates a new Client.
     *
     * @param id            the id
     * @param firstName     the first name
     * @param lastName      the last name
     * @param driverLicense the driver license
     * @param passport      the passport
     * @param phone         the phone
     * @param isBlackListed the is black listed
     */
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

    /**
     * Instantiates a new Client.
     *
     * @param firstName     the first name
     * @param lastName      the last name
     * @param driverLicense the driver license
     * @param passport      the passport
     * @param phone         the phone
     */
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

    /**
     * Gets full name.
     *
     * @return the full name
     */
    public String getFullName() {
        return fullName.get();
    }

    /**
     * Full name property read only string wrapper.
     *
     * @return the read only string wrapper
     */
    public ReadOnlyStringWrapper fullNameProperty() {
        return fullName;
    }


    /**
     * Instantiates a new Client.
     */
    public Client() {this(null, null, null, null, null);}

    /**
     * Gets client id.
     *
     * @return the client id
     */
    public long getClientId() {
        return clientId.get();
    }

    /**
     * Client id property simple long property.
     *
     * @return the simple long property
     */
    public SimpleLongProperty clientIdProperty() {
        return clientId;
    }

    /**
     * Sets client id.
     *
     * @param clientId the client id
     */
    public void setClientId(long clientId) {
        this.clientId.set(clientId);
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
     * First name property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty firstNameProperty() {
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
     * Last name property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty lastNameProperty() {
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
     * Gets driver license.
     *
     * @return the driver license
     */
    public String getDriverLicense() {
        return driverLicense.get();
    }

    /**
     * Driver license property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty driverLicenseProperty() {
        return driverLicense;
    }

    /**
     * Sets driver license.
     *
     * @param driverLicense the driver license
     */
    public void setDriverLicense(String driverLicense) {
        this.driverLicense.set(driverLicense);
    }

    /**
     * Gets passport.
     *
     * @return the passport
     */
    public String getPassport() {
        return passport.get();
    }

    /**
     * Passport property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty passportProperty() {
        return passport;
    }

    /**
     * Sets passport.
     *
     * @param passport the passport
     */
    public void setPassport(String passport) {
        this.passport.set(passport);
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone.get();
    }

    /**
     * Phone property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    /**
     * Is is black listed boolean.
     *
     * @return the boolean
     */
    public boolean isIsBlackListed() {
        return isBlackListed.get();
    }

    /**
     * Is black listed property simple boolean property.
     *
     * @return the simple boolean property
     */
    public SimpleBooleanProperty isBlackListedProperty() {
        return isBlackListed;
    }

    /**
     * Sets is black listed.
     *
     * @param isBlackListed the is black listed
     */
    public void setIsBlackListed(boolean isBlackListed) {
        this.isBlackListed.set(isBlackListed);
    }

    /**
     * To json string.
     *
     * @return the string
     */
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
