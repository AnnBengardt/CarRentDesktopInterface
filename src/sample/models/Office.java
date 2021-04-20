package sample.models;

import com.google.gson.Gson;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.HashMap;
import java.util.Map;

public class Office {

    private final SimpleLongProperty officeId;
    private final SimpleStringProperty city;
    private final SimpleStringProperty street;
    private final SimpleStringProperty house;
    private final SimpleStringProperty email;

    public Office(Long id, String city, String street, String house, String email){
        this.officeId = new SimpleLongProperty(id);
        this.city = new SimpleStringProperty(city);
        this.street = new SimpleStringProperty(street);
        this.house = new SimpleStringProperty(house);
        this.email = new SimpleStringProperty(email);
    }

    public Office(String city, String street, String house, String email){
        this.officeId = null;
        this.city = new SimpleStringProperty(city);
        this.street = new SimpleStringProperty(street);
        this.house = new SimpleStringProperty(house);
        this.email = new SimpleStringProperty(email);
    }

    public Office(){this(null, null, null, null);}

    public long getOfficeId() {
        return officeId.get();
    }

    public SimpleLongProperty officeIdProperty() {
        return officeId;
    }

    public String getCity() {
        return city.get();
    }

    public SimpleStringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getStreet() {
        return street.get();
    }

    public SimpleStringProperty streetProperty() {
        return street;
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public String getHouse() {
        return house.get();
    }

    public SimpleStringProperty houseProperty() {
        return house;
    }

    public void setHouse(String house) {
        this.house.set(house);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String toJson() {

        Map<String, String> map = new HashMap<>();
        map.put("officeId", String.valueOf(officeId.get()));
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
