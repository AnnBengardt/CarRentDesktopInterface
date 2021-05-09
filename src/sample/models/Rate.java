package sample.models;

import com.google.gson.Gson;
import javafx.beans.property.*;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Rate.
 */
public class Rate {

    private final LongProperty rateId;
    private final StringProperty rateName;
    private final DoubleProperty price;

    /**
     * Instantiates a new Rate.
     *
     * @param rateId   the rate id
     * @param rateName the rate name
     * @param price    the price
     */
    public Rate(Long rateId, String rateName, Double price){
        this.rateId = new SimpleLongProperty(rateId);
        this.rateName = new SimpleStringProperty(rateName);
        this.price = new SimpleDoubleProperty(price);
    }

    /**
     * Instantiates a new Rate.
     *
     * @param rateName the rate name
     */
    public Rate(String rateName){
        this.rateId = null;
        this.rateName = new SimpleStringProperty(rateName);
        this.price = new SimpleDoubleProperty();
    }

    /**
     * Instantiates a new Rate.
     */
    public Rate(){this(null);}

    /**
     * Gets rate id.
     *
     * @return the rate id
     */
    public long getRateId() {
        return rateId.get();
    }

    /**
     * Rate id property long property.
     *
     * @return the long property
     */
    public LongProperty rateIdProperty() {
        return rateId;
    }

    /**
     * Gets rate name.
     *
     * @return the rate name
     */
    public String getRateName() {
        return rateName.get();
    }

    /**
     * Rate name property string property.
     *
     * @return the string property
     */
    public StringProperty rateNameProperty() {
        return rateName;
    }

    /**
     * Sets rate name.
     *
     * @param rateName the rate name
     */
    public void setRateName(String rateName) {
        this.rateName.set(rateName);
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
     * Price property double property.
     *
     * @return the double property
     */
    public DoubleProperty priceProperty() {
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
     * To json string.
     *
     * @return the string
     */
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
