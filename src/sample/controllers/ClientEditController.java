package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Client;
import sample.models.Employee;
import sample.models.Job;
import sample.models.Office;
import sample.utils.RestApiRequests;

import java.io.IOException;


/**
 * The type Client edit controller.
 */
public class ClientEditController {

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField licenseField;

    @FXML
    private TextField passportField;

    @FXML
    private TextField phoneField;

    @FXML
    private CheckBox blackListedBox;


    private Main mainApp;
    private Stage dialogueStage;
    private Client clickedClient;
    private boolean okClicked = false;
    private RestApiRequests requests = new RestApiRequests();

    /**
     * Initialize.
     *
     * @param mainApp       the main app
     * @param stage         the stage
     * @param clickedClient the clicked client
     * @throws IOException the io exception
     */
    public void initialize(Main mainApp, Stage stage, Client clickedClient) throws IOException {
        this.mainApp = mainApp;
        this.dialogueStage = stage;
        this.clickedClient = clickedClient;
        setClient(clickedClient);
    }


    /**
     * Sets client.
     *
     * @param client the client
     */
    public void setClient(Client client) {
        lastNameField.setText(client.getLastName());
        firstNameField.setText(client.getFirstName());
        licenseField.setText(client.getDriverLicense());
        passportField.setText(client.getPassport());
        phoneField.setText(client.getPhone());
        if (client.isBlackListedProperty().get()){
            blackListedBox.setSelected(true);
        }
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
     * Gets clicked client.
     *
     * @return the clicked client
     */
    public Client getClickedClient() {
        return clickedClient;
    }

    @FXML
    private void handleCancel(){
        dialogueStage.close();
    }

    @FXML
    private void handleOk() throws IOException {
        if(isInputValid()){
            clickedClient.setFirstName(firstNameField.getText());
            clickedClient.setLastName(lastNameField.getText());
            clickedClient.setDriverLicense(licenseField.getText());
            clickedClient.setPassport(passportField.getText());
            clickedClient.setPhone(phoneField.getText());
            clickedClient.setIsBlackListed(blackListedBox.isSelected());
            okClicked = true;
            dialogueStage.close();
        }
    }


    private boolean isInputValid(){
        String errorMessage = "";
        if(lastNameField.getText() == null || lastNameField.getText().length() == 0){
            errorMessage += "No name input\n";
        }

        if(firstNameField.getText() == null || firstNameField.getText().length() == 0){
            errorMessage += "No last name input\n";
        }

        if(licenseField.getText() == null || licenseField.getText().length() == 0){
            errorMessage += "No street input\n";
        }

        if(passportField.getText() == null || passportField.getText().length() == 0) {
            errorMessage += "No city input\n";
        }

        if(phoneField.getText() == null || phoneField.getText().length() == 0) {
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
