package sample.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Employee;
import sample.utils.RestApiRequests;

import java.io.IOException;

public class EmployeesViewController {

    private Main mainApp;
    private Stage stage;
    private Employee currentUser;
    private ObservableList<Employee> employeeData;

    RestApiRequests requests = new RestApiRequests();

    @FXML
    private TableView<Employee> employeeTableView;

    @FXML
    private TableColumn<Employee, String> firstNameColumn;

    @FXML
    private TableColumn<Employee, String> lastNameColumn;

    @FXML
    private TableColumn<Employee, String> officeColumn;

    public void setEmployeeTableView(TableView<Employee> employeeTableView) {
        this.employeeTableView = employeeTableView;
    }

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label jobLabel;
    @FXML
    private Label officeLabel;

    public void initialize(Main mainApp, Stage stage, Employee currentUser, ObservableList<Employee> empData){
        this.mainApp = mainApp;
        this.stage = stage;
        this.currentUser = currentUser;
        this.employeeData = empData;
        employeeTableView.setItems(empData);

        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        officeColumn.setCellValueFactory(cellData -> cellData.getValue().getOffice().fullNameProperty());

        showEmployeeDetails(null);
        employeeTableView.getSelectionModel().selectedItemProperty().addListener(
                (((observableValue, oldValue, newValue) -> showEmployeeDetails(newValue)))
        );
    }

    private void showEmployeeDetails(Employee currentUser) {
        if (currentUser != null) {
            firstNameLabel.setText(currentUser.getFirstName());
            lastNameLabel.setText(currentUser.getLastName());
            emailLabel.setText(currentUser.getEmail());
            passwordField.setText(currentUser.getHashedPassword());
            jobLabel.setText(currentUser.getJob().getJobName());
            officeLabel.setText(currentUser.getOffice().getCity() + ", " + currentUser.getOffice().getStreet());

        } else {
            firstNameLabel.setText("No data");
            lastNameLabel.setText("No data");
            emailLabel.setText("No data");
            passwordField.setText("No data");
            jobLabel.setText("No data");
            officeLabel.setText("No data");
        }
    }

    @FXML
    private void handleDeleteEmployee() throws IOException {
        if (currentUser.getJob().getJobId() == 1) {
            int selectedIndex = employeeTableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                Boolean result = requests.deletePerson(employeeTableView.getSelectionModel().getSelectedItem());
                if (result)
                    employeeTableView.getItems().remove(selectedIndex);
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.initOwner(stage);
                    alert.setTitle("Error!");
                    alert.setHeaderText("Could not delete this user");
                    alert.setContentText("Please try again!");

                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(stage);
                alert.setTitle("Error!");
                alert.setHeaderText("Nothing to delete!");
                alert.setContentText("Please select a user to delete");

                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleNewEmployee() throws IOException {
        if (currentUser.getJob().getJobId() == 1){
            Employee tempEmployee = new Employee();
            Employee resultEmployee = mainApp.showEmployeesEditPage(stage, tempEmployee);
            if (resultEmployee != null) {
                employeeData.add(resultEmployee);
                requests.createEmployee(resultEmployee);
                mainApp.showMainApp(stage);
            } else {
                mainApp.showMainApp(stage);
            }
        }
    }

    @FXML
    private void handleEditEmployee() throws IOException {
        if (currentUser.getJob().getJobId() == 1){
            Employee tempEmployee = employeeTableView.getSelectionModel().getSelectedItem();
            Employee resultEmployee = mainApp.showEmployeesEditPage(stage, tempEmployee);
            if (resultEmployee != null) {
                requests.updateEmployee(resultEmployee);
                mainApp.showMainApp(stage);
            } else {
                mainApp.showMainApp(stage);
            }
        }
    }

    public ObservableList<Employee> getEmployeeData() {
        return employeeData;
    }
}
