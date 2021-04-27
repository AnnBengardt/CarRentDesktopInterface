package sample.controllers;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Employee;
import sample.models.Job;
import sample.models.Office;
import sample.utils.RestApiRequests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

public class OfficeEditController {

    @FXML
    private TextField cityField;

    @FXML
    private TextField streetField;

    @FXML
    private TextField buildingField;

    @FXML
    private TextField emailField;


    private Main mainApp;
    private Stage dialogueStage;
    private Office clickedOffice;
    private boolean okClicked = false;
    private RestApiRequests requests = new RestApiRequests();

    public void initialize(Main mainApp, Stage stage, Office clickedOffice) throws IOException {
        this.mainApp = mainApp;
        this.dialogueStage = stage;
        this.clickedOffice = clickedOffice;
        setOffice(clickedOffice);
    }


    public void setOffice(Office office) {
        cityField.setText(office.getCity());
        streetField.setText(office.getStreet());
        emailField.setText(office.getEmail());
        buildingField.setText(office.getHouse());
    }

    public boolean isOkClicked(){
        return okClicked;
    }

    public Office getClickedOffice() {
        return clickedOffice;
    }

    @FXML
    private void handleCancel(){
        dialogueStage.close();
    }

    @FXML
    private void handleOk() throws IOException {
        if(isInputValid()){
            clickedOffice.setCity(cityField.getText());
            clickedOffice.setStreet(streetField.getText());
            clickedOffice.setEmail(emailField.getText());
            clickedOffice.setHouse(buildingField.getText());
            okClicked = true;
            dialogueStage.close();
        }
    }


    private boolean isInputValid(){
        String errorMessage = "";
        if(cityField.getText() == null || cityField.getText().length() == 0){
            errorMessage += "No name input\n";
        }

        if(streetField.getText() == null || streetField.getText().length() == 0){
            errorMessage += "No last name input\n";
        }

        if(emailField.getText() == null || emailField.getText().length() == 0){
            errorMessage += "No street input\n";
        }

        if(buildingField.getText() == null || buildingField.getText().length() == 0) {
            errorMessage += "No city input\n";
        }

        if (errorMessage.length()==0){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogueStage);
            alert.setTitle("Error!");
            alert.setHeaderText("Wrong input!");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            return false;
        }

    }

}
