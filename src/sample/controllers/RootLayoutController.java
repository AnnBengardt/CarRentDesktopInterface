package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.Main;

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
    public void handleLogout() {
        mainApp.setCurrentUser(null);
        mainApp.start(stage);
    }

    @FXML
    public void handleOpenProfile(){
        mainApp.showProfilePage(stage);
    }

}
