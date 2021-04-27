package sample.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Employee;
import sample.models.Office;
import sample.utils.RestApiRequests;

import java.io.IOException;

public class OfficesViewController {

    private Main mainApp;
    private Stage stage;
    private Employee currentUser;
    private ObservableList<Office> officeData;

    RestApiRequests requests = new RestApiRequests();

    @FXML
    private TableView<Office> officeTableView;

    @FXML
    private TableColumn<Office, String> cityColumn;

    @FXML
    private TableColumn<Office, String> streetColumn;

    @FXML
    private Label cityLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label houseLabel;


    public void initialize(Main mainApp, Stage stage, Employee currentUser, ObservableList<Office> offData){
        this.mainApp = mainApp;
        this.stage = stage;
        this.currentUser = currentUser;
        this.officeData = offData;
        officeTableView.setItems(offData);

        cityColumn.setCellValueFactory(cellData -> cellData.getValue().cityProperty());
        streetColumn.setCellValueFactory(cellData -> cellData.getValue().streetProperty());

        showOfficeDetails(null);
        officeTableView.getSelectionModel().selectedItemProperty().addListener(
                (((observableValue, oldValue, newValue) -> showOfficeDetails(newValue)))
        );
    }

    private void showOfficeDetails(Office currentOffice) {
        if (currentOffice != null) {
            cityLabel.setText(currentOffice.getCity());
            streetLabel.setText(currentOffice.getStreet());
            houseLabel.setText(currentOffice.getHouse());
            emailLabel.setText(currentOffice.getEmail());

        } else {
            cityLabel.setText("No data");
            streetLabel.setText("No data");
            emailLabel.setText("No data");
            houseLabel.setText("No data");
        }
    }

    @FXML
    private void handleDeleteOffice() throws IOException {
        if (currentUser.getJob().getJobId() == 1) {
            int selectedIndex = officeTableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                Boolean result = requests.deleteOffice(officeTableView.getSelectionModel().getSelectedItem());
                if (result)
                    officeTableView.getItems().remove(selectedIndex);
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.initOwner(stage);
                    alert.setTitle("Error!");
                    alert.setHeaderText("Could not delete this user");
                    alert.setContentText("Please try again!");

                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(stage);
                alert.setTitle("Error!");
                alert.setHeaderText("Nothing to delete!");
                alert.setContentText("Please select an office to delete");

                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleNewOffice() throws IOException {
        if (currentUser.getJob().getJobId() == 1){
            Office tempOffice = new Office();
            Office resultOffice = mainApp.showOfficeEditPage(stage, tempOffice);
            if (resultOffice != null) {
                officeData.add(resultOffice);
                requests.createOffice(resultOffice);
                mainApp.showOfficesDB(stage);
            } else {
                mainApp.showOfficesDB(stage);
            }
        }
    }

    @FXML
    private void handleEditOffice() throws IOException {
        if (currentUser.getJob().getJobId() == 1){
            Office tempOffice = officeTableView.getSelectionModel().getSelectedItem();
            Office resultOffice = mainApp.showOfficeEditPage(stage, tempOffice);
            if (resultOffice != null) {
                requests.updateOffice(resultOffice);
                mainApp.showOfficesDB(stage);
            } else {
                mainApp.showOfficesDB(stage);
            }
        }
    }

    public ObservableList<Office> getOfficeData() {
        return officeData;
    }
}
