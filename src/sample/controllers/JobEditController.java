package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Job;
import sample.utils.RestApiRequests;

import java.io.IOException;

public class JobEditController {

    @FXML
    private TextField nameField;
    
    private Main mainApp;
    private Stage dialogueStage;
    private Job clickedJob;
    private boolean okClicked = false;
    private RestApiRequests requests = new RestApiRequests();

    public void initialize(Main mainApp, Stage stage, Job clickedJob) throws IOException {
        this.mainApp = mainApp;
        this.dialogueStage = stage;
        this.clickedJob = clickedJob;
        setJob(clickedJob);
    }


    public void setJob(Job job) {
        nameField.setText(job.getJobName());
    }

    public boolean isOkClicked(){
        return okClicked;
    }

    public Job getClickedJob() {
        return clickedJob;
    }

    @FXML
    private void handleCancel(){
        dialogueStage.close();
    }

    @FXML
    private void handleOk() throws IOException {
        if(isInputValid()){
            clickedJob.setJobName(nameField.getText());
            okClicked = true;
            dialogueStage.close();
        }
    }


    private boolean isInputValid(){
        String errorMessage = "";
        if(nameField.getText() == null || nameField.getText().length() == 0){
            errorMessage += "No name input\n";
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
