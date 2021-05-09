package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Employee;
import sample.utils.RestApiRequests;

import java.io.IOException;

/**
 * The type Login controller.
 */
public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private Employee employee;
    private Main mainApp;
    private Stage stage;

    /**
     * The Requests.
     */
    RestApiRequests requests = new RestApiRequests();

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
     * Handle login.
     *
     * @throws IOException the io exception
     */
    @FXML
    public void handleLogin() throws IOException {
        if (isInputValid()){
            employee = requests.getEmployeeByEmail(emailField.getText());
            if ((employee.getHashedPassword()!=null) && (employee.getHashedPassword().equals(passwordField.getText()))){
                this.mainApp.setCurrentUser(employee);
                this.mainApp.showMainApp(stage);}
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(stage);
                alert.setTitle("Error!");
                alert.setHeaderText("Wrong input!");
                alert.setContentText("Wrong username or password! Please try again");

                alert.showAndWait();
            }
        }

    }

    private Boolean isInputValid(){
        String errorMessage = "";
        if(emailField.getText() == null || emailField.getText().length() == 0){
            errorMessage += "No email input\n";
        }

        if(passwordField.getText() == null || passwordField.getText().length() == 0){
            errorMessage += "No password input\n";
        }

        if (errorMessage.length()==0){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Error!");
            alert.setHeaderText("Wrong input!");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            return false;
        }
    }

}
