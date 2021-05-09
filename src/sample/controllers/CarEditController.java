package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Car;
import sample.models.Office;
import sample.utils.RestApiRequests;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

/**
 * The type Car edit controller.
 */
public class CarEditController {

    @FXML
    private TextField brandField;

    @FXML
    private TextField priceField;

    @FXML
    private CheckBox statusCheck;

    @FXML
    private ChoiceBox<String> officeBox;

    private Main mainApp;
    private Stage dialogueStage;
    private Car clickedCar;
    private boolean okClicked = false;
    private RestApiRequests requests = new RestApiRequests();

    /**
     * Initialize.
     *
     * @param mainApp    the main app
     * @param stage      the stage
     * @param clickedCar the clicked car
     * @throws IOException the io exception
     */
    public void initialize(Main mainApp, Stage stage, Car clickedCar) throws IOException {
        this.mainApp = mainApp;
        this.dialogueStage = stage;
        this.clickedCar = clickedCar;
        setCar(clickedCar);
    }

    private static  ObservableMap<Long, String> getOfficeValues(RestApiRequests requests) throws IOException {
        ObservableList<Office> list = requests.getOffices();
        ObservableMap<Long, String> values = FXCollections.observableHashMap();
        for (int i = 0; i<list.size(); i++){
            values.put(list.get(i).getOfficeId(), list.get(i).getCity()+", "+list.get(i).getStreet());
        }
        return values;
    }

    /**
     * Sets car.
     *
     * @param car the car
     * @throws IOException the io exception
     */
    public void setCar(Car car) throws IOException {
        ObservableList<String> officeValues = FXCollections.observableArrayList(getOfficeValues(requests).values());
        brandField.setText(car.getBrand());
        priceField.setText(String.valueOf(car.getStartingPrice()));
        if (car.statusProperty().get()){
            statusCheck.setSelected(true);
        }
        officeBox.setItems(officeValues);
        if (car.getOffice() != null){
            officeBox.setValue(car.getOffice().getCity() +", "+car.getOffice().getStreet());
        }
    }

    /**
     * Is ok clicked boolean.
     *
     * @return the boolean
     */
    public boolean isOkClicked(){
        return okClicked;
    }

    /**
     * Gets clicked car.
     *
     * @return the clicked car
     */
    public Car getClickedCar() {
        return clickedCar;
    }

    @FXML
    private void handleCancel(){
        dialogueStage.close();
    }

    @FXML
    private void handleOk() throws IOException {
        if(isInputValid()){
            clickedCar.setBrand(brandField.getText());
            clickedCar.setStartingPrice(Double.valueOf(priceField.getText()));
            clickedCar.setStatus(statusCheck.isSelected());
            clickedCar.setOffice(requests.getOfficeById(keys(getOfficeValues(requests), officeBox.getValue()).findAny().get()));

            okClicked = true;
            dialogueStage.close();
        }
    }

    private static <K, V> Stream<K> keys(Map<K, V> map, V value) {
        return map
                .entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey);
    }

    private boolean isInputValid(){
        String errorMessage = "";
        if(brandField.getText() == null || brandField.getText().length() == 0){
            errorMessage += "No name input\n";
        }

        if(priceField.getText() == null || priceField.getText().length() == 0){
            errorMessage += "No last name input\n";
        }

        if (officeBox.getValue() == null){
            errorMessage += "No office input";
        }

        if (errorMessage.length()==0){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogueStage);
            alert.setTitle("Error!");
            alert.setHeaderText("Wrong input!");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            return false;
        }

    }

}
