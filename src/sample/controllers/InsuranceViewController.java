package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Car;
import sample.models.Insurance;
import sample.utils.RestApiRequests;

import java.time.LocalDate;

/**
 * The type Insurance view controller.
 */
public class InsuranceViewController {

    private Main mainApp;
    private Stage stage;
    private Insurance insurance;

    /**
     * The Requests.
     */
    RestApiRequests requests = new RestApiRequests();

    @FXML
    private TextField priceField;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    /**
     * Initialize.
     *
     * @param mainApp   the main app
     * @param stage     the stage
     * @param insurance the insurance
     * @param car       the car
     */
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

    /**
     * Set insurance.
     *
     * @param insurance the insurance
     */
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


    /**
     * Gets insurance.
     *
     * @return the insurance
     */
    public Insurance getInsurance() {
        return insurance;
    }
}
