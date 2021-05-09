package sample.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Employee;
import sample.models.Rate;
import sample.utils.RestApiRequests;

import java.io.IOException;

/**
 * The type Rates view controller.
 */
public class RatesViewController {

    private Main mainApp;
    private Stage stage;
    private Employee currentUser;
    private ObservableList<Rate> rateData;

    /**
     * The Requests.
     */
    RestApiRequests requests = new RestApiRequests();

    @FXML
    private TableView<Rate> rateTableView;

    @FXML
    private TableColumn<Rate, String> nameColumn;

    @FXML
    private TableColumn<Rate, Double> priceColumn;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;


    /**
     * Initialize.
     *
     * @param mainApp     the main app
     * @param stage       the stage
     * @param currentUser the current user
     * @param offData     the off data
     */
    public void initialize(Main mainApp, Stage stage, Employee currentUser, ObservableList<Rate> offData){
        this.mainApp = mainApp;
        this.stage = stage;
        this.currentUser = currentUser;
        this.rateData = offData;
        rateTableView.setItems(offData);

        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().rateNameProperty());

        showRateDetails(null);
        rateTableView.getSelectionModel().selectedItemProperty().addListener(
                (((observableValue, oldValue, newValue) -> showRateDetails(newValue)))
        );
    }

    private void showRateDetails(Rate currentRate) {
        if (currentRate != null) {
            priceLabel.setText(String.valueOf(currentRate.getPrice()));
            nameLabel.setText(currentRate.getRateName());

        } else {
            priceLabel.setText("No data");
            nameLabel.setText("No data");
        }
    }

    @FXML
    private void handleDeleteRate() throws IOException {
        if (currentUser.getJob().getJobId() == 1) {
            int selectedIndex = rateTableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                Boolean result = requests.deleteRate(rateTableView.getSelectionModel().getSelectedItem());
                if (result)
                    rateTableView.getItems().remove(selectedIndex);
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.initOwner(stage);
                    alert.setTitle("Error!");
                    alert.setHeaderText("Could not delete this rate");
                    alert.setContentText("Please try again!");

                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(stage);
                alert.setTitle("Error!");
                alert.setHeaderText("Nothing to delete!");
                alert.setContentText("Please select a rate to delete");

                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleNewRate() throws IOException {
        if (currentUser.getJob().getJobId() == 1){
            Rate tempRate = new Rate();
            Rate resultRate = mainApp.showRateEditPage(stage, tempRate);
            if (resultRate != null) {
                rateData.add(resultRate);
                requests.createRate(resultRate);
                mainApp.showRatesDB(stage);
            } else {
                mainApp.showRatesDB(stage);
            }
        }
    }

    @FXML
    private void handleEditRate() throws IOException {
        if (currentUser.getJob().getJobId() == 1){
            Rate tempRate = rateTableView.getSelectionModel().getSelectedItem();
            Rate resultRate = mainApp.showRateEditPage(stage, tempRate);
            if (resultRate != null) {
                requests.updateRate(resultRate);
                mainApp.showRatesDB(stage);
            } else {
                mainApp.showRatesDB(stage);
            }
        }
    }

    /**
     * Gets rate data.
     *
     * @return the rate data
     */
    public ObservableList<Rate> getRateData() {
        return rateData;
    }
}
