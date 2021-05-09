package sample.controllers;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;

/**
 * The type Root layout controller.
 */
public class RootLayoutController {

    private Main mainApp;
    private Stage stage;

    /**
     * Initialize.
     *
     * @param mainApp the main app
     * @param stage   the stage
     */
    public void initialize(Main mainApp, Stage stage){
        this.mainApp = mainApp;
        this.stage = stage;
    }

    /**
     * Handle show employees.
     */
    @FXML
    public void handleShowEmployees(){
        mainApp.showEmployeesDB(stage);
    }

    /**
     * Handle show offices.
     */
    @FXML
    public void handleShowOffices() {mainApp.showOfficesDB(stage);}

    /**
     * Handle show jobs.
     */
    @FXML
    public void handleShowJobs() {mainApp.showJobsDB(stage);}

    /**
     * Handle show rates.
     */
    @FXML
    public void handleShowRates() {mainApp.showRatesDB(stage);}

    /**
     * Handle show cars.
     */
    @FXML
    public void handleShowCars() {mainApp.showCarsDB(stage);}

    /**
     * Handle show clients.
     */
    @FXML
    public void handleShowClients() {mainApp.showClientsDB(stage);}

    /**
     * Handle show rents.
     */
    @FXML
    public void handleShowRents() {mainApp.showRentDB(stage);}

    /**
     * Handle show about.
     *
     * @throws IOException the io exception
     */
    @FXML
    public void handleShowAbout() throws IOException {mainApp.showAbout();}

    /**
     * Handle logout.
     */
    @FXML
    public void handleLogout() {
        mainApp.setCurrentUser(null);
        mainApp.start(stage);
    }

    /**
     * Handle open profile.
     */
    @FXML
    public void handleOpenProfile(){
        mainApp.showProfilePage(stage);
    }

}
