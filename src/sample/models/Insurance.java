package sample.models;

import com.google.gson.Gson;
import javafx.beans.property.*;
import sample.utils.DateUtil;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Insurance {

    private final SimpleLongProperty insuranceId;
    private final SimpleObjectProperty<LocalDate> startDate;
    private final SimpleObjectProperty<LocalDate> endDate;
    private final SimpleDoubleProperty price;


    public Insurance(Long id, LocalDate startDate, LocalDate endDate, Double price){
        this.insuranceId = new SimpleLongProperty(id);
        this.startDate = new SimpleObjectProperty<LocalDate>(startDate);
        this.endDate = new SimpleObjectProperty<LocalDate>(endDate);
        this.price = new SimpleDoubleProperty(price);
    }

    public Insurance(){
        this.insuranceId = null;
        this.startDate = new SimpleObjectProperty<LocalDate>();
        this.endDate = new SimpleObjectProperty<LocalDate>();
        this.price = new SimpleDoubleProperty();;
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


    public String toJson() {

        Map<String, Object> map = new HashMap<>();
        if (insuranceId == null){
            map.put("insuranceId", null);
        } else{
            map.put("insurnceId", String.valueOf(insuranceId.get()));
        }
        map.put("startDate", startDate.get());
        map.put("endDate", startDate.get());
        map.put("price", price.get());
        Gson gson = new Gson();
        return gson.toJson(map);
    }

}
