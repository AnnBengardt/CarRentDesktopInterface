package sample.models;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Car {

    private final SimpleLongProperty carId;
    private final SimpleStringProperty brand;
    private final SimpleDoubleProperty startingPrice;
    private final SimpleBooleanProperty status;
    private final SimpleObjectProperty<Office> office;
    private final ReadOnlyStringWrapper brandAndPrice = new ReadOnlyStringWrapper();


    public Car(Long id, String brand, Double startingPrice, Boolean status, Office office){
        this.carId = new SimpleLongProperty(id);
        this.brand = new SimpleStringProperty(brand);
        this.startingPrice = new SimpleDoubleProperty(startingPrice);
        this.status = new SimpleBooleanProperty(status);
        this.office = new SimpleObjectProperty<>(office);
        brandAndPrice.bind(Bindings.concat(this.brand, " - ", this.startingPrice));
    }

    public Car(String brand){
        this.carId = null;
        this.brand = new SimpleStringProperty(brand);
        this.startingPrice = new SimpleDoubleProperty();
        this.status = new SimpleBooleanProperty();
        this.office = new SimpleObjectProperty<Office>();
        brandAndPrice.bind(Bindings.concat(this.brand, " - ", this.startingPrice));
    }

    public Car(){this(null);}


    public String getBrandAndPrice() {
        return brandAndPrice.get();
    }

    public ReadOnlyStringWrapper brandAndPriceProperty() {
        return brandAndPrice;
    }

    public long getCarId() {
        return carId.get();
    }

    public SimpleLongProperty carIdProperty() {
        return carId;
    }

    public String getBrand() {
        return brand.get();
    }

    public SimpleStringProperty brandProperty() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand.set(brand);
    }

    public double getStartingPrice() {
        return startingPrice.get();
    }

    public SimpleDoubleProperty startingPriceProperty() {
        return startingPrice;
    }

    public void setStartingPrice(double startingPrice) {
        this.startingPrice.set(startingPrice);
    }

    public boolean isStatus() {
        return status.get();
    }

    public SimpleBooleanProperty statusProperty() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status.set(status);
    }

    public Office getOffice() {
        return office.get();
    }

    public SimpleObjectProperty<Office> officeProperty() {
        return office;
    }

    public void setOffice(Office office) {
        this.office.set(office);
    }


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
