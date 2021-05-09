package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Employee;

/**
 * The type Profile controller.
 */
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


    /**
     * Initialize.
     *
     * @param mainApp     the main app
     * @param stage       the stage
     * @param currentUser the current user
     */
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
            jobLabel.setText(currentUser.getJob().getJobName());
            officeLabel.setText(currentUser.getOffice().getCity() + ", " + currentUser.getOffice().getStreet());

        }else{
            firstNameLabel.setText("No data");
            lastNameLabel.setText("No data");
            emailLabel.setText("No data");
            jobLabel.setText("No data");
            officeLabel.setText("No data");
        }
    }



}
