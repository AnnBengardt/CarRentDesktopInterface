package sample.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Office;
import sample.models.Rent;
import sample.utils.RestApiRequests;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Stream;

/**
 * The type Rents view controller.
 */
public class RentsViewController {

    private Main mainApp;
    private Stage stage;
    private ObservableList<Rent> rentData;

    /**
     * The Requests.
     */
    RestApiRequests requests = new RestApiRequests();

    @FXML
    private TableView<Rent> rentTableView;

    @FXML
    private TableColumn<Rent, String> clientColumn;

    @FXML
    private TableColumn<Rent, String> carColumn;

    @FXML
    private TableColumn<Rent, Double> rateColumn;

    @FXML
    private TableColumn<Rent, Double> priceColumn;

    @FXML
    private TableColumn<Rent, LocalDate> startColumn;

    @FXML
    private TableColumn<Rent, LocalDate> endColumn;

    @FXML
    private ChoiceBox<String> officeFilter;

    /**
     * Sets rent table view.
     *
     * @param rentTableView the rent table view
     */
    public void setRentTableView(TableView<Rent> rentTableView) {
        this.rentTableView = rentTableView;
    }


    /**
     * Initialize.
     *
     * @param mainApp  the main app
     * @param stage    the stage
     * @param rentData the rent data
     * @throws IOException the io exception
     */
    public void initialize(Main mainApp, Stage stage, ObservableList<Rent> rentData) throws IOException {
        this.mainApp = mainApp;
        this.stage = stage;
        this.rentData = rentData;
        if (rentData != null){
            rentTableView.setItems(rentData);
        } else{
            this.rentData = FXCollections.observableArrayList();
        }

        clientColumn.setCellValueFactory(cellData -> cellData.getValue().clientProperty().get().fullNameProperty());
        carColumn.setCellValueFactory(cellData -> cellData.getValue().carProperty().get().brandAndPriceProperty());
        rateColumn.setCellValueFactory(cellData -> cellData.getValue().rateProperty().get().priceProperty().asObject());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().finalPriceProperty().asObject());
        startColumn.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());
        endColumn.setCellValueFactory(cellData -> cellData.getValue().endDateProperty());

        ObservableList<String> officeValues = FXCollections.observableArrayList(getOfficeValues(requests).values());
        officeFilter.setItems(officeValues);

        officeFilter.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            public void changed(ObservableValue ov, String value, String new_value)
            {
                if (new_value.equals("All")){
                    mainApp.showRentDB(stage);
                } else {
                    try {
                        ObservableList<Rent> newRentData;
                        newRentData = requests.getRentsByOfficeId(
                                keys(getOfficeValues(requests), officeFilter.getValue()).findAny().get());
                        mainApp.showRentDB(stage, newRentData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private static <K, V> Stream<K> keys(Map<K, V> map, V value) {
        return map
                .entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey);
    }

    private static ObservableMap<Long, String> getOfficeValues(RestApiRequests requests) throws IOException {
        ObservableList<Office> list = requests.getOffices();
        ObservableMap<Long, String> values = FXCollections.observableHashMap();
        for (int i = 0; i<list.size(); i++){
            values.put(list.get(i).getOfficeId(), list.get(i).getFullName());
        }
        values.put(Long.valueOf(0), "All");
        return values;
    }



    @FXML
    private void handleDeleteRent() throws IOException {
            int selectedIndex = rentTableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                Boolean result = requests.deleteRent(rentTableView.getSelectionModel().getSelectedItem());
                if (result)
                    rentTableView.getItems().remove(selectedIndex);
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.initOwner(stage);
                    alert.setTitle("Error!");
                    alert.setHeaderText("Could not delete this rent");
                    alert.setContentText("Please try again!");

                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(stage);
                alert.setTitle("Error!");
                alert.setHeaderText("Nothing to delete!");
                alert.setContentText("Please select a rent to delete");

                alert.showAndWait();
            }
        }

    @FXML
    private void handleNewRent() throws IOException {
            Rent tempRent = new Rent();
            Rent resultRent = mainApp.showRentsEditPage(stage, tempRent);
            if (resultRent != null) {
                rentData.add(resultRent);
                requests.createRent(resultRent);
                mainApp.showRentDB(stage);
            } else {
                mainApp.showRentDB(stage);
            }
        }

    @FXML
    private void handleEditRent() throws IOException {
            Rent tempRent = rentTableView.getSelectionModel().getSelectedItem();
            Rent resultRent = mainApp.showRentsEditPage(stage, tempRent);
            if (resultRent != null) {
                requests.updateRent(resultRent);
                mainApp.showRentDB(stage);
            } else {
                mainApp.showRentDB(stage);
            }
        }

    /**
     * Gets rent data.
     *
     * @return the rent data
     */
    public ObservableList<Rent> getRentData() {
        return rentData;
    }
}
