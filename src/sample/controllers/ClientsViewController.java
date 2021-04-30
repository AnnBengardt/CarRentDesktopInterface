package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Client;
import sample.models.Employee;
import sample.models.Office;
import sample.utils.RestApiRequests;

import java.io.IOException;

public class ClientsViewController {

    private Main mainApp;
    private Stage stage;
    private ObservableList<Client> clientData;

    RestApiRequests requests = new RestApiRequests();

    @FXML
    private TableView<Client> clientTableView;

    @FXML
    private TableColumn<Client, String> firstNameColumn;

    @FXML
    private TableColumn<Client, String> lastNameColumn;

    @FXML
    private Label lastNameLabel;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label licenseLabel;
    @FXML
    private Label passportLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private CheckBox blackListCheck;


    public void initialize(Main mainApp, Stage stage, ObservableList<Client> offData){
        this.mainApp = mainApp;
        this.stage = stage;
        this.clientData = offData;
        if (clientData != null){
            clientTableView.setItems(offData);
        } else{
            clientData = FXCollections.observableArrayList();
        }

        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        showClientDetails(null);
        clientTableView.getSelectionModel().selectedItemProperty().addListener(
                (((observableValue, oldValue, newValue) -> showClientDetails(newValue)))
        );
    }

    private void showClientDetails(Client currentClient) {
        if (currentClient != null) {
            firstNameLabel.setText(currentClient.getFirstName());
            lastNameLabel.setText(currentClient.getLastName());
            licenseLabel.setText(currentClient.getDriverLicense());
            passportLabel.setText(currentClient.getPassport());
            phoneLabel.setText(currentClient.getPhone());
            if (currentClient.isBlackListedProperty().get()){
                blackListCheck.setSelected(true);
            } else {
                blackListCheck.setSelected(false);
            }

        } else {
            firstNameLabel.setText("No data");
            lastNameLabel.setText("No data");
            licenseLabel.setText("No data");
            passportLabel.setText("No data");
            phoneLabel.setText("No data");
        }
    }

    @FXML
    private void handleDeleteClient() throws IOException {
            int selectedIndex = clientTableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                Boolean result = requests.deleteClient(clientTableView.getSelectionModel().getSelectedItem());
                if (result)
                    clientTableView.getItems().remove(selectedIndex);
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.initOwner(stage);
                    alert.setTitle("Error!");
                    alert.setHeaderText("Could not delete this client");
                    alert.setContentText("Please try again!");

                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(stage);
                alert.setTitle("Error!");
                alert.setHeaderText("Nothing to delete!");
                alert.setContentText("Please select a client to delete");

                alert.showAndWait();
            }
    }

    @FXML
    private void handleNewClient() throws IOException {
            Client tempClient = new Client();
            Client resultClient = mainApp.showClientEditPage(stage, tempClient);
            if (resultClient != null) {
                clientData.add(resultClient);
                requests.createClient(resultClient);
                mainApp.showClientsDB(stage);
            } else {
                mainApp.showClientsDB(stage);
            }
        }

    @FXML
    private void handleEditClient() throws IOException {
            Client tempClient = clientTableView.getSelectionModel().getSelectedItem();
            Client resultClient = mainApp.showClientEditPage(stage, tempClient);
            if (resultClient != null) {
                requests.updateClient(resultClient);
                mainApp.showClientsDB(stage);
            } else {
                mainApp.showClientsDB(stage);
            }
        }


    public ObservableList<Client> getClientData() {
        return clientData;
    }
}
