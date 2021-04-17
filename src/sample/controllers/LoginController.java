package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Employee;
import sample.utils.RestApiRequests;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private Employee employee;
    private Main mainApp;
    private Stage stage;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    RestApiRequests requests = new RestApiRequests();

    public void initialize(Main mainApp, Stage stage){
        this.mainApp = mainApp;
        this.stage = stage;
    }

    @FXML
    public void handleLogin() throws IOException {
        if (emailField.getText() != null){
            employee = requests.getEmployeeByEmail(emailField.getText());
            if (employee.getHashedPassword().equals(passwordField.getText())){
                this.mainApp.setCurrentUser(employee);
                this.mainApp.showMainApp(stage);}
        }

    }

}
