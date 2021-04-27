package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.controllers.*;
import sample.models.Employee;
import sample.models.Job;
import sample.models.Office;
import sample.models.Rate;
import sample.utils.RestApiRequests;

import java.io.IOException;

public class Main extends Application {
    RestApiRequests requests = new RestApiRequests();
    private Stage primaryStage;
    public Employee currentUser;
    private BorderPane rootLayout;
    private ObservableList<Employee> employeeData = FXCollections.observableArrayList();

    public Employee getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Employee currentUser) {
        this.currentUser = currentUser;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setEmployeeData(ObservableList<Employee> employeeData) {
        this.employeeData = employeeData;
    }

    @Override
    public void start(Stage primaryStage) {
        try{
            employeeData = requests.getEmployees();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/login.fxml"));
            AnchorPane loginPage = (AnchorPane) loader.load();
            Scene scene = new Scene(loginPage, 900, 400);
            primaryStage.setScene(scene);
            LoginController loginController = loader.getController();
            loginController.initialize(this, primaryStage);
            primaryStage.show();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showMainApp(Stage primaryStage){
        try{
            employeeData = requests.getEmployees();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/rootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout, 900, 400);
            primaryStage.setScene(scene);
            RootLayoutController layoutController = loader.getController();
            layoutController.initialize(this, primaryStage);
            primaryStage.show();
            showEmployeesDB(primaryStage);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showProfilePage(Stage primaryStage){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/profile.fxml"));
            AnchorPane profile = (AnchorPane) loader.load();

            rootLayout.setCenter(profile);
            ProfileController profileController = loader.getController();
            profileController.initialize(this, primaryStage, currentUser);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showEmployeesDB(Stage primaryStage){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/employees.fxml"));
            AnchorPane employees = (AnchorPane) loader.load();

            rootLayout.setCenter(employees);
            EmployeesViewController employeesViewController = loader.getController();
            employeesViewController.initialize(this, primaryStage, currentUser, employeeData);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public Employee showEmployeesEditPage(Stage primaryStage, Employee clickedEmployee) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/employeeEdit.fxml"));
        AnchorPane editPage = (AnchorPane) loader.load();

        Stage dialogueStage = new Stage();
        dialogueStage.setTitle("EDIT");
        dialogueStage.initOwner(primaryStage);
        dialogueStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(editPage);
        dialogueStage.setScene(scene);

        EmployeeEditController controller = loader.getController();
        controller.initialize(this, dialogueStage, clickedEmployee);
        dialogueStage.showAndWait();
        if (controller.isOkClicked()){
            return controller.getClickedUser();
        }else{
            return null;
        }
    }

    public void showOfficesDB(Stage primaryStage){
        try{
            ObservableList<Office> officeData = requests.getOffices();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/offices.fxml"));
            AnchorPane offices = (AnchorPane) loader.load();

            rootLayout.setCenter(offices);
            OfficesViewController officesViewController = loader.getController();
            officesViewController.initialize(this, primaryStage, currentUser, officeData);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public Office showOfficeEditPage(Stage primaryStage, Office clickedOffice) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/officeEdit.fxml"));
        AnchorPane editPage = (AnchorPane) loader.load();

        Stage dialogueStage = new Stage();
        dialogueStage.setTitle("EDIT");
        dialogueStage.initOwner(primaryStage);
        dialogueStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(editPage);
        dialogueStage.setScene(scene);

        OfficeEditController controller = loader.getController();
        controller.initialize(this, dialogueStage, clickedOffice);
        dialogueStage.showAndWait();
        if (controller.isOkClicked()){
            return controller.getClickedOffice();
        }else{
            return null;
        }
    }

    public void showJobsDB(Stage primaryStage){
        try{
            ObservableList<Job> jobData = requests.getJobs();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/jobs.fxml"));
            AnchorPane jobs = (AnchorPane) loader.load();

            rootLayout.setCenter(jobs);
            JobsViewController jobsViewController = loader.getController();
            jobsViewController.initialize(this, primaryStage, currentUser, jobData);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public Job showJobEditPage(Stage primaryStage, Job clickedJob) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/jobEdit.fxml"));
        AnchorPane editPage = (AnchorPane) loader.load();

        Stage dialogueStage = new Stage();
        dialogueStage.setTitle("EDIT");
        dialogueStage.initOwner(primaryStage);
        dialogueStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(editPage);
        dialogueStage.setScene(scene);

        JobEditController controller = loader.getController();
        controller.initialize(this, dialogueStage, clickedJob);
        dialogueStage.showAndWait();
        if (controller.isOkClicked()){
            return controller.getClickedJob();
        }else{
            return null;
        }
    }


    public void showRatesDB(Stage primaryStage){
        try{
            ObservableList<Rate> rateData = requests.getRates();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/rates.fxml"));
            AnchorPane rates = (AnchorPane) loader.load();

            rootLayout.setCenter(rates);
            RatesViewController ratesViewController = loader.getController();
            ratesViewController.initialize(this, primaryStage, currentUser, rateData);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public Rate showRateEditPage(Stage primaryStage, Rate clickedRate) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/rateEdit.fxml"));
        AnchorPane editPage = (AnchorPane) loader.load();

        Stage dialogueStage = new Stage();
        dialogueStage.setTitle("EDIT");
        dialogueStage.initOwner(primaryStage);
        dialogueStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(editPage);
        dialogueStage.setScene(scene);

        RateEditController controller = loader.getController();
        controller.initialize(this, dialogueStage, clickedRate);
        dialogueStage.showAndWait();
        if (controller.isOkClicked()){
            return controller.getClickedRate();
        }else{
            return null;
        }
    }




    public static void main(String[] args) {
        launch(args);
    }
}