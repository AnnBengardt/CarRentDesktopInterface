package sample.models;

import com.google.gson.Gson;
import javafx.beans.property.*;

import java.util.HashMap;
import java.util.Map;

public class Rate {

    private final LongProperty rateId;
    private final StringProperty rateName;
    private final DoubleProperty price;

    public Rate(Long rateId, String rateName, Double price){
        this.rateId = new SimpleLongProperty(rateId);
        this.rateName = new SimpleStringProperty(rateName);
        this.price = new SimpleDoubleProperty(price);
    }

    public Rate(String rateName){
        this.rateId = null;
        this.rateName = new SimpleStringProperty(rateName);
        this.price = new SimpleDoubleProperty();
    }

    public Rate(){this(null);}

    public long getRateId() {
        return rateId.get();
    }

    public LongProperty rateIdProperty() {
        return rateId;
    }

    public String getRateName() {
        return rateName.get();
    }

    public StringProperty rateNameProperty() {
        return rateName;
    }

    public void setRateName(String rateName) {
        this.rateName.set(rateName);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public String toJson() {

        Map<String, String> map = new HashMap<>();
        if (rateId == null){
            map.put("rateId", null);
        } else{
            map.put("rateId", String.valueOf(rateId.get()));
        }
        map.put("rateName", String.valueOf(rateName.get()));
        map.put("price", String.valueOf(price.get()));
        Gson gson = new Gson();
        return gson.toJson(map);
    }
}
