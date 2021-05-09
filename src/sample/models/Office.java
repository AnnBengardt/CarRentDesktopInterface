package sample.models;

import com.google.gson.Gson;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Office.
 */
public class Office {

    private final SimpleLongProperty officeId;
    private final SimpleStringProperty city;
    private final SimpleStringProperty street;
    private final SimpleStringProperty house;
    private final SimpleStringProperty email;
    private final ReadOnlyStringWrapper fullName = new ReadOnlyStringWrapper();

    /**
     * Instantiates a new Office.
     *
     * @param id     the id
     * @param city   the city
     * @param street the street
     * @param house  the house
     * @param email  the email
     */
    public Office(Long id, String city, String street, String house, String email){
        this.officeId = new SimpleLongProperty(id);
        this.city = new SimpleStringProperty(city);
        this.street = new SimpleStringProperty(street);
        this.house = new SimpleStringProperty(house);
        this.email = new SimpleStringProperty(email);
        fullName.bind(Bindings.concat(city, ", ", street));
    }

    /**
     * Instantiates a new Office.
     *
     * @param city   the city
     * @param street the street
     * @param house  the house
     * @param email  the email
     */
    public Office(String city, String street, String house, String email){
        this.officeId = null;
        this.city = new SimpleStringProperty(city);
        this.street = new SimpleStringProperty(street);
        this.house = new SimpleStringProperty(house);
        this.email = new SimpleStringProperty(email);
        fullName.bind(Bindings.concat(city, ", ", street));
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
     * Instantiates a new Office.
     */
    public Office(){this(null, null, null, null);}

    /**
     * Gets office id.
     *
     * @return the office id
     */
    public long getOfficeId() {
        return officeId.get();
    }

    /**
     * Office id property simple long property.
     *
     * @return the simple long property
     */
    public SimpleLongProperty officeIdProperty() {
        return officeId;
    }

    /**
     * Gets city.
     *
     * @return the city
     */
    public String getCity() {
        return city.get();
    }

    /**
     * City property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty cityProperty() {
        return city;
    }

    /**
     * Sets city.
     *
     * @param city the city
     */
    public void setCity(String city) {
        this.city.set(city);
    }

    /**
     * Gets street.
     *
     * @return the street
     */
    public String getStreet() {
        return street.get();
    }

    /**
     * Street property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty streetProperty() {
        return street;
    }

    /**
     * Sets street.
     *
     * @param street the street
     */
    public void setStreet(String street) {
        this.street.set(street);
    }

    /**
     * Gets house.
     *
     * @return the house
     */
    public String getHouse() {
        return house.get();
    }

    /**
     * House property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty houseProperty() {
        return house;
    }

    /**
     * Sets house.
     *
     * @param house the house
     */
    public void setHouse(String house) {
        this.house.set(house);
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
     * Email property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty emailProperty() {
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
     * To json string.
     *
     * @return the string
     */
    public String toJson() {

        Map<String, String> map = new HashMap<>();
        if (officeId == null){
            map.put("officeId", null);
        } else{
            map.put("officeId", String.valueOf(officeId.get()));
        }
        map.put("city", String.valueOf(city.get()));
        map.put("street", String.valueOf(street.get()));
        map.put("house", String.valueOf(house.get()));
        map.put("email", String.valueOf(email.get()));

        Gson gson = new Gson();
        return gson.toJson(map);
    }

    @Override
    public String toString() {
        return "{" +
                "officeId:" + officeId.get() +
                ", city:" + city.get() +
                ", street:" + street.get() +
                ", house:" + house.get() +
                ", email:" + email.get() +
                '}';
    }
}
