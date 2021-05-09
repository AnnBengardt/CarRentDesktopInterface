package sample.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import sample.utils.LocalDateAdapter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Rent.
 */
public class Rent {

    private final SimpleLongProperty rentId;
    private final SimpleObjectProperty<LocalDate> startDate;
    private final SimpleObjectProperty<LocalDate> endDate;
    private final SimpleDoubleProperty finalPrice;
    private final SimpleObjectProperty<Rate> rate;
    private final SimpleObjectProperty<Client> client;
    private final SimpleObjectProperty<Car> car;

    /**
     * Instantiates a new Rent.
     *
     * @param id         the id
     * @param startDate  the start date
     * @param endDate    the end date
     * @param finalPrice the final price
     * @param rate       the rate
     * @param client     the client
     * @param car        the car
     */
    public Rent(Long id, LocalDate startDate, LocalDate endDate, Double finalPrice, Rate rate,
                Client client, Car car){
        this.rentId = new SimpleLongProperty(id);
        this.startDate = new SimpleObjectProperty<LocalDate>(startDate);
        this.endDate = new SimpleObjectProperty<LocalDate>(endDate);
        this.finalPrice = new SimpleDoubleProperty(finalPrice);
        this.rate = new SimpleObjectProperty<>(rate);
        this.client = new SimpleObjectProperty<>(client);
        this.car = new SimpleObjectProperty<>(car);
    }

    /**
     * Instantiates a new Rent.
     */
    public Rent(){
        this.rentId = null;
        this.startDate = new SimpleObjectProperty<>();
        this.endDate = new SimpleObjectProperty<>();
        this.finalPrice = new SimpleDoubleProperty();
        this.rate = new SimpleObjectProperty<Rate>();
        this.client = new SimpleObjectProperty<Client>();
        this.car = new SimpleObjectProperty<Car>();
    }

    /**
     * Gets rent id.
     *
     * @return the rent id
     */
    public long getRentId() {
        return rentId.get();
    }

    /**
     * Rent id property simple long property.
     *
     * @return the simple long property
     */
    public SimpleLongProperty rentIdProperty() {
        return rentId;
    }

    /**
     * Sets rent id.
     *
     * @param rentId the rent id
     */
    public void setRentId(long rentId) {
        this.rentId.set(rentId);
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
     * Gets final price.
     *
     * @return the final price
     */
    public double getFinalPrice() {
        return finalPrice.get();
    }

    /**
     * Final price property simple double property.
     *
     * @return the simple double property
     */
    public SimpleDoubleProperty finalPriceProperty() {
        return finalPrice;
    }

    /**
     * Sets final price.
     *
     * @param finalPrice the final price
     */
    public void setFinalPrice(double finalPrice) {
        this.finalPrice.set(finalPrice);
    }

    /**
     * Gets rate.
     *
     * @return the rate
     */
    public Rate getRate() {
        return rate.get();
    }

    /**
     * Rate property simple object property.
     *
     * @return the simple object property
     */
    public SimpleObjectProperty<Rate> rateProperty() {
        return rate;
    }

    /**
     * Sets rate.
     *
     * @param rate the rate
     */
    public void setRate(Rate rate) {
        this.rate.set(rate);
    }

    /**
     * Gets client.
     *
     * @return the client
     */
    public Client getClient() {
        return client.get();
    }

    /**
     * Client property simple object property.
     *
     * @return the simple object property
     */
    public SimpleObjectProperty<Client> clientProperty() {
        return client;
    }

    /**
     * Sets client.
     *
     * @param client the client
     */
    public void setClient(Client client) {
        this.client.set(client);
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
        if (rentId == null){
            map.put("rentId", null);
        } else{
            map.put("rentId", String.valueOf(rentId.get()));
        }
        map.put("startDate", startDate.get());
        map.put("endDate", endDate.get());
        map.put("finalPrice", finalPrice.get());
        map.put("rate", new Gson().fromJson(rate.get().toJson(), JsonObject.class));
        map.put("client", new Gson().fromJson(client.get().toJson(), JsonObject.class));
        map.put("car", new Gson().fromJson(car.get().toJson(), JsonObject.class));
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        return gson.toJson(map);
    }

}
