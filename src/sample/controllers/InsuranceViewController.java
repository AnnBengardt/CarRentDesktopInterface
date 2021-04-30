package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Car;
import sample.models.Employee;
import sample.models.Insurance;
import sample.utils.DateUtil;
import sample.utils.RestApiRequests;

import java.time.LocalDate;

public class InsuranceViewController {

    private Main mainApp;
    private Stage stage;
    private Insurance insurance;

    RestApiRequests requests = new RestApiRequests();

    @FXML
    private TextField priceField;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    public void initialize(Main mainApp, Stage stage, Insurance insurance, Car car) {
        this.mainApp = mainApp;
        this.stage = stage;
        if (insurance != null) {
            this.insurance = insurance;}
        else {
            this.insurance = new Insurance(car);
        }

        setInsurance(this.insurance);

    }

    public void setInsurance(Insurance insurance){
        startDate.setValue(insurance.startDateProperty().get());
        endDate.setValue(insurance.endDateProperty().get());
        priceField.setText(String.valueOf(insurance.getPrice()));
    }

    @FXML
    private void handleUpdate(){
        insurance.setStartDate(startDate.getValue());
        insurance.setEndDate(endDate.getValue());
        insurance.setPrice(Double.parseDouble(priceField.getText()));
        if (insurance.insuranceIdProperty() != null){
            requests.updateInsurance(insurance);}
        else {
            requests.createInsurance(insurance);
        }
    }


    public Insurance getInsurance() {
        return insurance;
    }
}
