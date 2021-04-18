package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Employee;

public class ProfileController {
    private Main mainApp;
    private Stage stage;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label jobLabel;
    @FXML
    private Label officeLabel;


    public void initialize(Main mainApp, Stage stage, Employee currentUser){
        this.mainApp = mainApp;
        this.stage = stage;
        showEmployeeDetails(currentUser);
    }

    private void showEmployeeDetails(Employee currentUser){
        if (currentUser != null){
            firstNameLabel.setText(currentUser.getFirstName());
            lastNameLabel.setText(currentUser.getLastName());
            emailLabel.setText(currentUser.getEmail());
            jobLabel.setText(Long.toString(currentUser.getJobId()));
            officeLabel.setText(Long.toString(currentUser.getOfficeId()));

        }else{
            firstNameLabel.setText("No data");
            lastNameLabel.setText("No data");
            emailLabel.setText("No data");
            jobLabel.setText("No data");
            officeLabel.setText("No data");
        }
    }



}
