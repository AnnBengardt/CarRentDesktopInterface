package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Job;
import sample.models.Rate;
import sample.utils.RestApiRequests;

import java.io.IOException;

public class RateEditController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    private Main mainApp;
    private Stage dialogueStage;
    private Rate clickedRate;
    private boolean okClicked = false;
    private RestApiRequests requests = new RestApiRequests();

    public void initialize(Main mainApp, Stage stage, Rate clickedRate) throws IOException {
        this.mainApp = mainApp;
        this.dialogueStage = stage;
        this.clickedRate = clickedRate;
        setRate(clickedRate);
    }


    public void setRate(Rate rate) {
        nameField.setText(rate.getRateName());
        if (rate.priceProperty() == null){
            priceField.setText("00.00");
        } else {
            priceField.setText(String.valueOf(rate.priceProperty().get()));}
    }

    public boolean isOkClicked(){
        return okClicked;
    }

    public Rate getClickedRate() {
        return clickedRate;
    }

    @FXML
    private void handleCancel(){
        dialogueStage.close();
    }

    @FXML
    private void handleOk() throws IOException {
        if(isInputValid()){
            clickedRate.setRateName(nameField.getText());
            clickedRate.setPrice(Double.parseDouble(priceField.getText()));
            okClicked = true;
            dialogueStage.close();
        }
    }


    private boolean isInputValid(){
        String errorMessage = "";
        if(nameField.getText() == null || nameField.getText().length() == 0){
            errorMessage += "No name input\n";
        }

        if (priceField.getText() == null || priceField.getText().length() == 0){
            errorMessage += "No price input\n";
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

