package sample.models;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Car.
 */
public class Car {

    private final SimpleLongProperty carId;
    private final SimpleStringProperty brand;
    private final SimpleDoubleProperty startingPrice;
    private final SimpleBooleanProperty status;
    private final SimpleObjectProperty<Office> office;
    private final ReadOnlyStringWrapper brandAndPrice = new ReadOnlyStringWrapper();


    /**
     * Instantiates a new Car.
     *
     * @param id            the id
     * @param brand         the brand
     * @param startingPrice the starting price
     * @param status        the status
     * @param office        the office
     */
    public Car(Long id, String brand, Double startingPrice, Boolean status, Office office){
        this.carId = new SimpleLongProperty(id);
        this.brand = new SimpleStringProperty(brand);
        this.startingPrice = new SimpleDoubleProperty(startingPrice);
        this.status = new SimpleBooleanProperty(status);
        this.office = new SimpleObjectProperty<>(office);
        brandAndPrice.bind(Bindings.concat(this.brand, " - ", this.startingPrice));
    }

    /**
     * Instantiates a new Car.
     *
     * @param brand the brand
     */
    public Car(String brand){
        this.carId = null;
        this.brand = new SimpleStringProperty(brand);
        this.startingPrice = new SimpleDoubleProperty();
        this.status = new SimpleBooleanProperty();
        this.office = new SimpleObjectProperty<Office>();
        brandAndPrice.bind(Bindings.concat(this.brand, " - ", this.startingPrice));
    }

    /**
     * Instantiates a new Car.
     */
    public Car(){this(null);}


    /**
     * Gets brand and price.
     *
     * @return the brand and price
     */
    public String getBrandAndPrice() {
        return brandAndPrice.get();
    }

    /**
     * Brand and price property read only string wrapper.
     *
     * @return the read only string wrapper
     */
    public ReadOnlyStringWrapper brandAndPriceProperty() {
        return brandAndPrice;
    }

    /**
     * Gets car id.
     *
     * @return the car id
     */
    public long getCarId() {
        return carId.get();
    }

    /**
     * Car id property simple long property.
     *
     * @return the simple long property
     */
    public SimpleLongProperty carIdProperty() {
        return carId;
    }

    /**
     * Gets brand.
     *
     * @return the brand
     */
    public String getBrand() {
        return brand.get();
    }

    /**
     * Brand property simple string property.
     *
     * @return the simple string property
     */
    public SimpleStringProperty brandProperty() {
        return brand;
    }

    /**
     * Sets brand.
     *
     * @param brand the brand
     */
    public void setBrand(String brand) {
        this.brand.set(brand);
    }

    /**
     * Gets starting price.
     *
     * @return the starting price
     */
    public double getStartingPrice() {
        return startingPrice.get();
    }

    /**
     * Starting price property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty startingPriceProperty() {
        return startingPrice;
    }

    /**
     * Sets starting price.
     *
     * @param startingPrice the starting price
     */
    public void setStartingPrice(double startingPrice) {
        this.startingPrice.set(startingPrice);
    }

    /**
     * Is status boolean.
     *
     * @return the boolean
     */
    public boolean isStatus() {
        return status.get();
    }

    /**
     * Status property simple boolean property.
     *
     * @return the simple boolean property
     */
    public SimpleBooleanProperty statusProperty() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(boolean status) {
        this.status.set(status);
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
     * Office property simple object property.
     *
     * @return the simple object property
     */
    public SimpleObjectProperty<Office> officeProperty() {
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
        if (carId == null){
            map.put("carId", null);
        } else{
            map.put("carId", String.valueOf(carId.get()));
        }
        map.put("brand", String.valueOf(brand.get()));
        map.put("startingPrice", String.valueOf(startingPrice.get()));
        map.put("status", String.valueOf(status.get()));
        map.put("office", new Gson().fromJson(office.get().toJson(), JsonObject.class));
        Gson gson = new Gson();
        return gson.toJson(map);
    }


}
