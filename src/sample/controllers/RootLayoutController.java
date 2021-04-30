package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;

public class RootLayoutController {

    /*@FXML
    private Button dbButton;

    @FXML
    private Button helpButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button openProfileButton;

    @FXML
    private Button logOutbutton;*/

    private Main mainApp;
    private Stage stage;

    public void initialize(Main mainApp, Stage stage){
        this.mainApp = mainApp;
        this.stage = stage;
    }

    @FXML
    public void handleShowEmployees(){
        mainApp.showEmployeesDB(stage);
    }

    @FXML
    public void handleShowOffices() {mainApp.showOfficesDB(stage);}

    @FXML
    public void handleShowJobs() {mainApp.showJobsDB(stage);}

    @FXML
    public void handleShowRates() {mainApp.showRatesDB(stage);}

    @FXML
    public void handleShowCars() {mainApp.showCarsDB(stage);}

    @FXML
    public void handleShowClients() {mainApp.showClientsDB(stage);}

    @FXML
    public void handleShowRents() {mainApp.showRentDB(stage);}

    @FXML
    public void handleShowAbout() throws IOException {mainApp.showAbout();}

    @FXML
    public void handleLogout() {
        mainApp.setCurrentUser(null);
        mainApp.start(stage);
    }

    @FXML
    public void handleOpenProfile(){
        mainApp.showProfilePage(stage);
    }

}
