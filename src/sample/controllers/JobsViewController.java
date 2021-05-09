package sample.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Employee;
import sample.models.Job;
import sample.utils.RestApiRequests;

import java.io.IOException;

/**
 * The type Jobs view controller.
 */
public class JobsViewController {

    private Main mainApp;
    private Stage stage;
    private Employee currentUser;
    private ObservableList<Job> jobData;

    /**
     * The Requests.
     */
    RestApiRequests requests = new RestApiRequests();

    @FXML
    private TableView<Job> jobTableView;

    @FXML
    private TableColumn<Job, Long> idColumn;

    @FXML
    private TableColumn<Job, String> nameColumn;

    @FXML
    private Label idLabel;

    @FXML
    private Label nameLabel;


    /**
     * Initialize.
     *
     * @param mainApp     the main app
     * @param stage       the stage
     * @param currentUser the current user
     * @param offData     the off data
     */
    public void initialize(Main mainApp, Stage stage, Employee currentUser, ObservableList<Job> offData){
        this.mainApp = mainApp;
        this.stage = stage;
        this.currentUser = currentUser;
        this.jobData = offData;
        jobTableView.setItems(offData);

        idColumn.setCellValueFactory(cellData -> cellData.getValue().jobIdProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().jobNameProperty());

        showJobDetails(null);
        jobTableView.getSelectionModel().selectedItemProperty().addListener(
                (((observableValue, oldValue, newValue) -> showJobDetails(newValue)))
        );
    }

    private void showJobDetails(Job currentJob) {
        if (currentJob != null) {
            idLabel.setText(String.valueOf(currentJob.getJobId()));
            nameLabel.setText(currentJob.getJobName());

        } else {
            idLabel.setText("No data");
            nameLabel.setText("No data");
        }
    }

    @FXML
    private void handleDeleteJob() throws IOException {
        if (currentUser.getJob().getJobId() == 1) {
            int selectedIndex = jobTableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                Boolean result = requests.deleteJob(jobTableView.getSelectionModel().getSelectedItem());
                if (result)
                    jobTableView.getItems().remove(selectedIndex);
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.initOwner(stage);
                    alert.setTitle("Error!");
                    alert.setHeaderText("Could not delete this job");
                    alert.setContentText("Please try again!");

                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(stage);
                alert.setTitle("Error!");
                alert.setHeaderText("Nothing to delete!");
                alert.setContentText("Please select a job to delete");

                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleNewJob() throws IOException {
        if (currentUser.getJob().getJobId() == 1){
            Job tempJob = new Job();
            Job resultJob = mainApp.showJobEditPage(stage, tempJob);
            if (resultJob != null) {
                jobData.add(resultJob);
                requests.createJob(resultJob);
                mainApp.showJobsDB(stage);
            } else {
                mainApp.showJobsDB(stage);
            }
        }
    }

    @FXML
    private void handleEditJob() throws IOException {
        if (currentUser.getJob().getJobId() == 1){
            Job tempJob = jobTableView.getSelectionModel().getSelectedItem();
            Job resultJob = mainApp.showJobEditPage(stage, tempJob);
            if (resultJob != null) {
                requests.updateJob(resultJob);
                mainApp.showJobsDB(stage);
            } else {
                mainApp.showJobsDB(stage);
            }
        }
    }

    /**
     * Gets job data.
     *
     * @return the job data
     */
    public ObservableList<Job> getJobData() {
        return jobData;
    }
}
