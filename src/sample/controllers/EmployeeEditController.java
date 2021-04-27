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

public class EmployeeEditController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private ChoiceBox<String> jobBox;

    @FXML
    private ChoiceBox<String> officeBox;

    private Main mainApp;
    private Stage dialogueStage;
    private Employee clickedUser;
    private boolean okClicked = false;
    private RestApiRequests requests = new RestApiRequests();

    public void initialize(Main mainApp, Stage stage, Employee clickedUser) throws IOException {
        this.mainApp = mainApp;
        this.dialogueStage = stage;
        this.clickedUser = clickedUser;
        setEmployee(clickedUser);
    }

    private static ObservableMap<Long, String> getJobValues(RestApiRequests requests) throws IOException {
        ObservableList<Job> list = requests.getJobs();
        ObservableMap<Long, String> values = FXCollections.observableHashMap();
        for (int i = 0; i<list.size(); i++){
            values.put(list.get(i).getJobId(), list.get(i).getJobName());
        }
        return values;
    }

    private static  ObservableMap<Long, String> getOfficeValues(RestApiRequests requests) throws IOException {
        ObservableList<Office> list = requests.getOffices();
        ObservableMap<Long, String> values = FXCollections.observableHashMap();
        for (int i = 0; i<list.size(); i++){
            values.put(list.get(i).getOfficeId(), list.get(i).getCity()+", "+list.get(i).getStreet());
        }
        return values;
    }

    public void setEmployee(Employee employee) throws IOException {
        ObservableList<String> jobValues = FXCollections.observableArrayList(getJobValues(requests).values());
        ObservableList<String> officeValues = FXCollections.observableArrayList(getOfficeValues(requests).values());
        firstNameField.setText(employee.getFirstName());
        lastNameField.setText(employee.getLastName());
        emailField.setText(employee.getEmail());
        passwordField.setText(employee.getHashedPassword());
        jobBox.setItems(jobValues);
        officeBox.setItems(officeValues);
        if (employee.getJob() != null){
            jobBox.setValue(employee.getJob().getJobName());
        }
        if (employee.getOffice() != null){
            officeBox.setValue(employee.getOffice().getCity() +", "+employee.getOffice().getStreet());
        }
    }

    public boolean isOkClicked(){
        return okClicked;
    }

    public Employee getClickedUser() {
        return clickedUser;
    }

    @FXML
    private void handleCancel(){
        dialogueStage.close();
    }

    @FXML
    private void handleOk() throws IOException {
        if(isInputValid()){
            clickedUser.setFirstName(firstNameField.getText());
            clickedUser.setLastName(lastNameField.getText());
            clickedUser.setEmail(emailField.getText());
            clickedUser.setHashedPassword(passwordField.getText());
            System.out.println(jobBox.getValue());
            System.out.println(keys(getJobValues(requests), jobBox.getValue()));
            System.out.println(keys(getJobValues(requests), jobBox.getValue()).findAny().get());
            clickedUser.setJob(requests.getJobById(keys(getJobValues(requests), jobBox.getValue()).findAny().get()));
            clickedUser.setOffice(requests.getOfficeById(keys(getOfficeValues(requests), officeBox.getValue()).findAny().get()));

            okClicked = true;
            dialogueStage.close();
        }
    }

    private static <K, V> Stream<K> keys(Map<K, V> map, V value) {
        return map
                .entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey);
    }

    private boolean isInputValid(){
        String errorMessage = "";
        if(firstNameField.getText() == null || firstNameField.getText().length() == 0){
            errorMessage += "No name input\n";
        }

        if(lastNameField.getText() == null || lastNameField.getText().length() == 0){
            errorMessage += "No last name input\n";
        }

        if(emailField.getText() == null || emailField.getText().length() == 0){
            errorMessage += "No street input\n";
        }

        if(passwordField.getText() == null || passwordField.getText().length() == 0) {
            errorMessage += "No city input\n";
        }

        if (jobBox.getValue() == null){
            errorMessage += "No job input, ";
        }

        if (officeBox.getValue() == null){
            errorMessage += "No office input";
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
