package sample.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import sample.utils.LocalDateAdapter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Rent {

    private final SimpleLongProperty rentId;
    private final SimpleObjectProperty<LocalDate> startDate;
    private final SimpleObjectProperty<LocalDate> endDate;
    private final SimpleDoubleProperty finalPrice;
    private final SimpleObjectProperty<Rate> rate;
    private final SimpleObjectProperty<Client> client;
    private final SimpleObjectProperty<Car> car;

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

    public Rent(){
        this.rentId = null;
        this.startDate = new SimpleObjectProperty<>();
        this.endDate = new SimpleObjectProperty<>();
        this.finalPrice = new SimpleDoubleProperty();
        this.rate = new SimpleObjectProperty<Rate>();
        this.client = new SimpleObjectProperty<Client>();
        this.car = new SimpleObjectProperty<Car>();
    }

    public long getRentId() {
        return rentId.get();
    }

    public SimpleLongProperty rentIdProperty() {
        return rentId;
    }

    public void setRentId(long rentId) {
        this.rentId.set(rentId);
    }

    public LocalDate getStartDate() {
        return startDate.get();
    }

    public SimpleObjectProperty<LocalDate> startDateProperty() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate.set(startDate);
    }

    public LocalDate getEndDate() {
        return endDate.get();
    }

    public SimpleObjectProperty<LocalDate> endDateProperty() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate.set(endDate);
    }

    public double getFinalPrice() {
        return finalPrice.get();
    }

    public SimpleDoubleProperty finalPriceProperty() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice.set(finalPrice);
    }

    public Rate getRate() {
        return rate.get();
    }

    public SimpleObjectProperty<Rate> rateProperty() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate.set(rate);
    }

    public Client getClient() {
        return client.get();
    }

    public SimpleObjectProperty<Client> clientProperty() {
        return client;
    }

    public void setClient(Client client) {
        this.client.set(client);
    }

    public Car getCar() {
        return car.get();
    }

    public SimpleObjectProperty<Car> carProperty() {
        return car;
    }

    public void setCar(Car car) {
        this.car.set(car);
    }


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
