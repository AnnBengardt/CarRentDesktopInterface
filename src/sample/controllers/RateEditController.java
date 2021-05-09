package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Rate;
import sample.utils.RestApiRequests;

import java.io.IOException;

/**
 * The type Rate edit controller.
 */
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

    /**
     * Initialize.
     *
     * @param mainApp     the main app
     * @param stage       the stage
     * @param clickedRate the clicked rate
     * @throws IOException the io exception
     */
    public void initialize(Main mainApp, Stage stage, Rate clickedRate) throws IOException {
        this.mainApp = mainApp;
        this.dialogueStage = stage;
        this.clickedRate = clickedRate;
        setRate(clickedRate);
    }


    /**
     * Sets rate.
     *
     * @param rate the rate
     */
    public void setRate(Rate rate) {
        nameField.setText(rate.getRateName());
        if (rate.priceProperty() == null){
            priceField.setText("00.00");
        } else {
            priceField.setText(String.valueOf(rate.priceProperty().get()));}
    }

    /**
     * Is ok clicked boolean.
     *
     * @return the boolean
     */
    public boolean isOkClicked(){
        return okClicked;
    }

    /**
     * Gets clicked rate.
     *
     * @return the clicked rate
     */
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

