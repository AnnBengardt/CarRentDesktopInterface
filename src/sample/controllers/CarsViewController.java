package sample.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;
import sample.models.*;
import sample.utils.RestApiRequests;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

/**
 * The type Cars view controller.
 */
public class CarsViewController {

    private Main mainApp;
    private Stage stage;
    private Employee currentUser;
    private ObservableList<Car> carData;

    /**
     * The Requests.
     */
    RestApiRequests requests = new RestApiRequests();

    @FXML
    private TableView<Car> carTableView;

    @FXML
    private TableColumn<Car, Long> idColumn;

    @FXML
    private TableColumn<Car, String> brandColumn;

    @FXML
    private TableColumn<Car, String> statusColumn;

    /**
     * Sets car table view.
     *
     * @param carTableView the car table view
     */
    public void setCarTableView(TableView<Car> carTableView) {
        this.carTableView = carTableView;
    }

    @FXML
    private Label idLabel;
    @FXML
    private Label brandLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private CheckBox statusCheck;
    @FXML
    private Label officeLabel;

    @FXML
    private ChoiceBox<String> officeFilter;

    /**
     * Initialize.
     *
     * @param mainApp     the main app
     * @param stage       the stage
     * @param currentUser the current user
     * @param carData     the car data
     * @throws IOException the io exception
     */
    public void initialize(Main mainApp, Stage stage, Employee currentUser, ObservableList<Car> carData) throws IOException {
        this.mainApp = mainApp;
        this.stage = stage;
        this.currentUser = currentUser;
        this.carData = carData;
        carTableView.setItems(carData);

        idColumn.setCellValueFactory(cellData -> cellData.getValue().carIdProperty().asObject());
        brandColumn.setCellValueFactory(cellData -> cellData.getValue().brandProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty().get()
                                                    ? new SimpleStringProperty("Free")
                                                    : new SimpleStringProperty("Rented"));

        showCarDetails(null);
        carTableView.getSelectionModel().selectedItemProperty().addListener(
                (((observableValue, oldValue, newValue) -> showCarDetails(newValue)))
        );

        ObservableList<String> officeValues = FXCollections.observableArrayList(getOfficeValues(requests).values());
        officeFilter.setItems(officeValues);

        officeFilter.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            public void changed(ObservableValue ov, String value, String new_value)
            {
                if (new_value.equals("All")){
                    mainApp.showCarsDB(stage);
                } else {
                    try {
                        ObservableList<Car> newCarData;
                        newCarData = requests.getCarsByOfficeId(
                                keys(getOfficeValues(requests), officeFilter.getValue()).findAny().get());
                        mainApp.showCarsDB(stage, newCarData);
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


    private void showCarDetails(Car currentCar) {
        if (currentCar != null) {
            idLabel.setText(String.valueOf(currentCar.getCarId()));
            brandLabel.setText(currentCar.getBrand());
            priceLabel.setText(String.valueOf(currentCar.getStartingPrice()));
            if (currentCar.statusProperty().get()){
                statusCheck.setSelected(true);
            } else {
                statusCheck.setSelected(false);
            }
            officeLabel.setText(currentCar.getOffice().getCity() + ", " + currentCar.getOffice().getStreet());

        } else {
            idLabel.setText("No data");
            brandLabel.setText("No data");
            priceLabel.setText("No data");
            officeLabel.setText("No data");
        }
    }

    @FXML
    private void handleDeleteCar() throws IOException {
            int selectedIndex = carTableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                Boolean result = requests.deleteCar(carTableView.getSelectionModel().getSelectedItem());
                if (result)
                    carTableView.getItems().remove(selectedIndex);
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.initOwner(stage);
                    alert.setTitle("Error!");
                    alert.setHeaderText("Could not delete this car");
                    alert.setContentText("Please try again!");

                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(stage);
                alert.setTitle("Error!");
                alert.setHeaderText("Nothing to delete!");
                alert.setContentText("Please select a car to delete");

                alert.showAndWait();
            }
        }

    @FXML
    private void handleNewCar() throws IOException {
            Car tempCar = new Car();
            Car resultCar = mainApp.showCarEditPage(stage, tempCar);
            if (resultCar != null) {
                carData.add(resultCar);
                requests.createCar(resultCar);
                mainApp.showCarsDB(stage);
            } else {
                mainApp.showCarsDB(stage);
            }
        }

    @FXML
    private void handleEditCar() throws IOException {
            Car tempCar = carTableView.getSelectionModel().getSelectedItem();
            Car resultCar = mainApp.showCarEditPage(stage, tempCar);
            if (resultCar != null) {
                requests.updateCar(resultCar);
                mainApp.showCarsDB(stage);
            } else {
                mainApp.showCarsDB(stage);
            }
        }

    @FXML
    private void handleGetStatistics() throws IOException {
        mainApp.showStats(stage, carTableView.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleOpenInsurance() throws IOException {
        Insurance insurance = requests.getInsuranceByCarId(carTableView.getSelectionModel().getSelectedItem());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(CarsViewController.class.getResource("../view/insurance.fxml"));
        AnchorPane insurancePage = (AnchorPane) loader.load();

        Stage dialogueStage = new Stage();
        dialogueStage.setTitle("Insurance");
        dialogueStage.getIcons().add(new Image(
                CarsViewController.class.getResourceAsStream( "money.png" )));
        dialogueStage.initOwner(stage);
        dialogueStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(insurancePage);
        dialogueStage.setScene(scene);

        InsuranceViewController controller = loader.getController();
        controller.initialize(mainApp, dialogueStage,insurance, carTableView.getSelectionModel().getSelectedItem());
        dialogueStage.showAndWait();
    }

    /**
     * Gets car data.
     *
     * @return the car data
     */
    public ObservableList<Car> getCarData() {
        return carData;
    }
}
