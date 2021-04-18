package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.controllers.EmployeesViewController;
import sample.controllers.ProfileController;
import sample.controllers.RootLayoutController;
import sample.models.Employee;
import sample.controllers.LoginController;
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




    public static void main(String[] args) {
        launch(args);
    }
}