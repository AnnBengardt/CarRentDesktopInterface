package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;
import sample.models.*;
import sample.utils.RestApiRequests;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Stream;

/**
 * The type Rent edit controller.
 */
public class RentEditController {

    @FXML
    private TextField priceField;

    @FXML
    private ChoiceBox<String> clientBox;

    @FXML
    private ChoiceBox<String> carBox;

    @FXML
    private ChoiceBox<String> rateBox;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    private Main mainApp;
    private Stage dialogueStage;
    private Rent clickedRent;
    private boolean okClicked = false;
    private RestApiRequests requests = new RestApiRequests();

    /**
     * Initialize.
     *
     * @param mainApp     the main app
     * @param stage       the stage
     * @param clickedRent the clicked rent
     * @throws IOException the io exception
     */
    public void initialize(Main mainApp, Stage stage, Rent clickedRent) throws IOException {
        this.mainApp = mainApp;
        this.dialogueStage = stage;
        this.clickedRent = clickedRent;
        setRent(clickedRent);
    }

    private static ObservableMap<Long, String> getClientValues(RestApiRequests requests) throws IOException {
        ObservableList<Client> list = requests.getClients();
        ObservableMap<Long, String> values = FXCollections.observableHashMap();
        for (int i = 0; i<list.size(); i++){
            values.put(list.get(i).getClientId(), list.get(i).getFullName());
        }
        return values;
    }

    private static  ObservableMap<Long, String> getCarValues(RestApiRequests requests, Rent rent) throws IOException {
        ObservableList<Car> list = requests.getCars();
        ObservableMap<Long, String> values = FXCollections.observableHashMap();
        for (int i = 0; i<list.size(); i++){
            if (list.get(i).statusProperty().get() ||
                    (rent.rentIdProperty() != null && list.get(i).getCarId()==rent.getCar().getCarId())){
            values.put(list.get(i).getCarId(), list.get(i).getBrand()+" - "+list.get(i).getOffice().getFullName());}
        }
        return values;
    }

    private static ObservableMap<Long, String> getRateValues(RestApiRequests requests) throws IOException{
        ObservableList<Rate> list = requests.getRates();
        ObservableMap<Long, String> values = FXCollections.observableHashMap();
        for (int i = 0; i<list.size(); i++){
            values.put(list.get(i).getRateId(), list.get(i).getRateName() + " - " + list.get(i).getPrice());
        }
        return values;
    }

    /**
     * Sets rent.
     *
     * @param rent the rent
     * @throws IOException the io exception
     */
    public void setRent(Rent rent) throws IOException {
        ObservableList<String> clientValues = FXCollections.observableArrayList(getClientValues(requests).values());
        ObservableList<String> carValues = FXCollections.observableArrayList(getCarValues(requests, rent).values());
        ObservableList<String> rateValues = FXCollections.observableArrayList(getRateValues(requests).values());
        clientBox.setItems(clientValues);
        carBox.setItems(carValues);
        rateBox.setItems(rateValues);
        priceField.setDisable(true);
        if (rent.rentIdProperty() != null) {
            clientBox.setValue(rent.getClient().getFullName());
            carBox.setValue(rent.getCar().getBrand() + " - " + rent.getCar().getOffice());
            rateBox.setValue(rent.getRate().getRateName() + " - " + rent.getRate().getPrice());
            priceField.setText(String.valueOf(rent.getFinalPrice()));
            startDate.setValue(rent.getStartDate());
            endDate.setValue(rent.getEndDate());}
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
     * Gets clicked rent.
     *
     * @return the clicked rent
     */
    public Rent getClickedRent() {
        return clickedRent;
    }

    @FXML
    private void handleCancel(){
        dialogueStage.close();
    }

    @FXML
    private void handleOk() throws IOException {
        if(isInputValid()){
            Client client = requests.getClientById(keys(getClientValues(requests), clientBox.getValue()).findAny().get());
            if (!client.isBlackListedProperty().get()){
                Car car = requests.getCarById(keys(getCarValues(requests, clickedRent),carBox.getValue()).findAny().get());
                Rate rate = requests.getRateById(keys(getRateValues(requests), rateBox.getValue()).findAny().get());
                clickedRent.setClient(client);
                car.setStatus(false);
                clickedRent.setCar(car);
                System.out.println(car.toJson());
                clickedRent.setRate(rate);
                clickedRent.setFinalPrice(car.getStartingPrice()+rate.getPrice()*
                        (ChronoUnit.DAYS.between(startDate.getValue(), endDate.getValue())+1));
                clickedRent.setStartDate(startDate.getValue());
                clickedRent.setEndDate(endDate.getValue());
                requests.updateCar(car);
                okClicked = true;
                dialogueStage.close();}
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(dialogueStage);
                alert.setTitle("Error!");
                alert.setHeaderText("This client is in a black list");

                alert.showAndWait();
            }
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
        if(clientBox.getValue() == null){
            errorMessage += "No Client input\n";
        }

        if(carBox.getValue() == null){
            errorMessage += "No Car input\n";
        }

        if(rateBox.getValue() == null){
            errorMessage += "No Rate input\n";
        }

        if (startDate.getValue() == null){
            errorMessage += "No start date input, ";
        }

        if (endDate.getValue() == null){
            errorMessage += "No end date input";
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
