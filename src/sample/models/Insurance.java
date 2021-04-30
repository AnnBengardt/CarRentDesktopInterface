package sample.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import javafx.beans.property.*;
import sample.utils.DateUtil;
import sample.utils.LocalDateAdapter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Insurance {

    private final SimpleLongProperty insuranceId;
    private final SimpleObjectProperty<LocalDate> startDate;
    private final SimpleObjectProperty<LocalDate> endDate;
    private final SimpleDoubleProperty price;
    private final SimpleObjectProperty<Car> car;


    public Insurance(Long id, LocalDate startDate, LocalDate endDate, Double price, Car car){
        this.insuranceId = new SimpleLongProperty(id);
        this.startDate = new SimpleObjectProperty<LocalDate>(startDate);
        this.endDate = new SimpleObjectProperty<LocalDate>(endDate);
        this.price = new SimpleDoubleProperty(price);
        this.car = new SimpleObjectProperty<>(car);
    }

    public Insurance(Car car){
        this.insuranceId = null;
        this.startDate = new SimpleObjectProperty<LocalDate>(LocalDate.parse("1900-01-01"));
        this.endDate = new SimpleObjectProperty<LocalDate>(LocalDate.parse("1999-12-31"));
        this.price = new SimpleDoubleProperty(0.0);;
        this.car = new SimpleObjectProperty<>(car);
    }

    public long getInsuranceId() {
        return insuranceId.get();
    }

    public SimpleLongProperty insuranceIdProperty() {
        return insuranceId;
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

    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
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
