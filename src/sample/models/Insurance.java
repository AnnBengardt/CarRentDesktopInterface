package sample.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import javafx.beans.property.*;
import sample.utils.LocalDateAdapter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Insurance.
 */
public class Insurance {

    private final SimpleLongProperty insuranceId;
    private final SimpleObjectProperty<LocalDate> startDate;
    private final SimpleObjectProperty<LocalDate> endDate;
    private final SimpleDoubleProperty price;
    private final SimpleObjectProperty<Car> car;


    /**
     * Instantiates a new Insurance.
     *
     * @param id        the id
     * @param startDate the start date
     * @param endDate   the end date
     * @param price     the price
     * @param car       the car
     */
    public Insurance(Long id, LocalDate startDate, LocalDate endDate, Double price, Car car){
        this.insuranceId = new SimpleLongProperty(id);
        this.startDate = new SimpleObjectProperty<LocalDate>(startDate);
        this.endDate = new SimpleObjectProperty<LocalDate>(endDate);
        this.price = new SimpleDoubleProperty(price);
        this.car = new SimpleObjectProperty<>(car);
    }

    /**
     * Instantiates a new Insurance.
     *
     * @param car the car
     */
    public Insurance(Car car){
        this.insuranceId = null;
        this.startDate = new SimpleObjectProperty<LocalDate>(LocalDate.parse("2000-01-01"));
        this.endDate = new SimpleObjectProperty<LocalDate>(LocalDate.parse("2030-12-31"));
        this.price = new SimpleDoubleProperty(0.0);;
        this.car = new SimpleObjectProperty<>(car);
    }

    /**
     * Gets insurance id.
     *
     * @return the insurance id
     */
    public long getInsuranceId() {
        return insuranceId.get();
    }

    /**
     * Insurance id property simple long property.
     *
     * @return the simple long property
     */
    public SimpleLongProperty insuranceIdProperty() {
        return insuranceId;
    }

    /**
     * Gets start date.
     *
     * @return the start date
     */
    public LocalDate getStartDate() {
        return startDate.get();
    }

    /**
     * Start date property simple object property.
     *
     * @return the simple object property
     */
    public SimpleObjectProperty<LocalDate> startDateProperty() {
        return startDate;
    }

    /**
     * Sets start date.
     *
     * @param startDate the start date
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate.set(startDate);
    }

    /**
     * Gets end date.
     *
     * @return the end date
     */
    public LocalDate getEndDate() {
        return endDate.get();
    }

    /**
     * End date property simple object property.
     *
     * @return the simple object property
     */
    public SimpleObjectProperty<LocalDate> endDateProperty() {
        return endDate;
    }

    /**
     * Sets end date.
     *
     * @param endDate the end date
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate.set(endDate);
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public double getPrice() {
        return price.get();
    }

    /**
     * Price property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(double price) {
        this.price.set(price);
    }

    /**
     * Gets car.
     *
     * @return the car
     */
    public Car getCar() {
        return car.get();
    }

    /**
     * Car property simple object property.
     *
     * @return the simple object property
     */
    public SimpleObjectProperty<Car> carProperty() {
        return car;
    }

    /**
     * Sets car.
     *
     * @param car the car
     */
    public void setCar(Car car) {
        this.car.set(car);
    }

    /**
     * To json string.
     *
     * @return the string
     */
    public String toJson() {

        Map<String, Object> map = new HashMap<>();
        if (insuranceId == null){
            map.put("insuranceId", null);
        } else{
            map.put("insuranceId", String.valueOf(insuranceId.get()));
        }
        map.put("startDate", startDateProperty().get());
        map.put("endDate", endDateProperty().get());
        map.put("price", price.get());
        map.put("car", new Gson().fromJson(car.get().toJson(), JsonObject.class));
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        return gson.toJson(map);
    }

}
