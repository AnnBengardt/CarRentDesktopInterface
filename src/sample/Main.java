package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.controllers.*;
import sample.models.*;
import sample.utils.RestApiRequests;

import java.io.IOException;

/**
 * The type Main.
 */
public class Main extends Application {
    /**
     * The Requests.
     */
    RestApiRequests requests = new RestApiRequests();
    private Stage primaryStage;
    /**
     * The Current user.
     */
    public Employee currentUser;
    private BorderPane rootLayout;
    private ObservableList<Employee> employeeData = FXCollections.observableArrayList();

    /**
     * Gets current user.
     *
     * @return the current user
     */
    public Employee getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets current user.
     *
     * @param currentUser the current user
     */
    public void setCurrentUser(Employee currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Gets primary stage.
     *
     * @return the primary stage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Sets employee data.
     *
     * @param employeeData the employee data
     */
    public void setEmployeeData(ObservableList<Employee> employeeData) {
        this.employeeData = employeeData;
    }

    @Override
    public void start(Stage primaryStage) {
        try{
            primaryStage.setTitle("Car rental management system");
            primaryStage.getIcons().add(
                    new Image(
                            Main.class.getResourceAsStream( "icon.png" )));
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

    /**
     * Show main app.
     *
     * @param primaryStage the primary stage
     */
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

    /**
     * Show profile page.
     *
     * @param primaryStage the primary stage
     */
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

    /**
     * Show employees db.
     *
     * @param primaryStage the primary stage
     */
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

    /**
     * Show employees edit page employee.
     *
     * @param primaryStage    the primary stage
     * @param clickedEmployee the clicked employee
     * @return the employee
     * @throws IOException the io exception
     */
    public Employee showEmployeesEditPage(Stage primaryStage, Employee clickedEmployee) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/employeeEdit.fxml"));
        AnchorPane editPage = (AnchorPane) loader.load();

        Stage dialogueStage = new Stage();
        dialogueStage.setTitle("Edit employee");
        dialogueStage.getIcons().add(new Image(
                        Main.class.getResourceAsStream( "icon.png" )));
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

    /**
     * Show offices db.
     *
     * @param primaryStage the primary stage
     */
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

    /**
     * Show office edit page office.
     *
     * @param primaryStage  the primary stage
     * @param clickedOffice the clicked office
     * @return the office
     * @throws IOException the io exception
     */
    public Office showOfficeEditPage(Stage primaryStage, Office clickedOffice) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/officeEdit.fxml"));
        AnchorPane editPage = (AnchorPane) loader.load();

        Stage dialogueStage = new Stage();
        dialogueStage.setTitle("Edit office");
        dialogueStage.getIcons().add(new Image(
                Main.class.getResourceAsStream( "icon.png" )));
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

    /**
     * Show jobs db.
     *
     * @param primaryStage the primary stage
     */
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

    /**
     * Show job edit page job.
     *
     * @param primaryStage the primary stage
     * @param clickedJob   the clicked job
     * @return the job
     * @throws IOException the io exception
     */
    public Job showJobEditPage(Stage primaryStage, Job clickedJob) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/jobEdit.fxml"));
        AnchorPane editPage = (AnchorPane) loader.load();

        Stage dialogueStage = new Stage();
        dialogueStage.setTitle("Edit job");
        dialogueStage.getIcons().add(new Image(
                Main.class.getResourceAsStream( "icon.png" )));
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


    /**
     * Show rates db.
     *
     * @param primaryStage the primary stage
     */
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

    /**
     * Show rate edit page rate.
     *
     * @param primaryStage the primary stage
     * @param clickedRate  the clicked rate
     * @return the rate
     * @throws IOException the io exception
     */
    public Rate showRateEditPage(Stage primaryStage, Rate clickedRate) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/rateEdit.fxml"));
        AnchorPane editPage = (AnchorPane) loader.load();

        Stage dialogueStage = new Stage();
        dialogueStage.setTitle("Edit rate");
        dialogueStage.getIcons().add(new Image(
                Main.class.getResourceAsStream( "icon.png" )));
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

    /**
     * Show cars db.
     *
     * @param primaryStage the primary stage
     */
    public void showCarsDB(Stage primaryStage){
        try{
            ObservableList<Car> carData = requests.getCars();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/cars.fxml"));
            AnchorPane cars = (AnchorPane) loader.load();

            rootLayout.setCenter(cars);
            CarsViewController viewController = loader.getController();
            viewController.initialize(this, primaryStage, currentUser, carData);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Show cars db.
     *
     * @param primaryStage the primary stage
     * @param newCarData   the new car data
     */
    public void showCarsDB(Stage primaryStage, ObservableList<Car> newCarData){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/cars.fxml"));
            AnchorPane cars = (AnchorPane) loader.load();

            rootLayout.setCenter(cars);
            CarsViewController viewController = loader.getController();
            viewController.initialize(this, primaryStage, currentUser, newCarData);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Show car edit page car.
     *
     * @param primaryStage the primary stage
     * @param clickedCar   the clicked car
     * @return the car
     * @throws IOException the io exception
     */
    public Car showCarEditPage(Stage primaryStage, Car clickedCar) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/carEdit.fxml"));
        AnchorPane editPage = (AnchorPane) loader.load();

        Stage dialogueStage = new Stage();
        dialogueStage.setTitle("Edit car");
        dialogueStage.getIcons().add(new Image(
                Main.class.getResourceAsStream( "icon.png" )));
        dialogueStage.initOwner(primaryStage);
        dialogueStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(editPage);
        dialogueStage.setScene(scene);

        CarEditController controller = loader.getController();
        controller.initialize(this, dialogueStage, clickedCar);
        dialogueStage.showAndWait();
        if (controller.isOkClicked()){
            return controller.getClickedCar();
        }else{
            return null;
        }
    }


    /**
     * Show clients db.
     *
     * @param primaryStage the primary stage
     */
    public void showClientsDB(Stage primaryStage){
        try{
            ObservableList<Client> clientData = requests.getClients();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/clients.fxml"));
            AnchorPane clients = (AnchorPane) loader.load();

            rootLayout.setCenter(clients);
            ClientsViewController viewController = loader.getController();
            viewController.initialize(this, primaryStage, clientData);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Show client edit page client.
     *
     * @param primaryStage  the primary stage
     * @param clickedClient the clicked client
     * @return the client
     * @throws IOException the io exception
     */
    public Client showClientEditPage(Stage primaryStage, Client clickedClient) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/clientEdit.fxml"));
        AnchorPane editPage = (AnchorPane) loader.load();

        Stage dialogueStage = new Stage();
        dialogueStage.setTitle("Edit client");
        dialogueStage.getIcons().add(new Image(
                Main.class.getResourceAsStream( "icon.png" )));
        dialogueStage.initOwner(primaryStage);
        dialogueStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(editPage);
        dialogueStage.setScene(scene);

        ClientEditController controller = loader.getController();
        controller.initialize(this, dialogueStage, clickedClient);
        dialogueStage.showAndWait();
        if (controller.isOkClicked()){
            return controller.getClickedClient();
        }else{
            return null;
        }
    }


    /**
     * Show rent db.
     *
     * @param primaryStage the primary stage
     */
    public void showRentDB(Stage primaryStage){
        try{
            ObservableList<Rent> rentData = requests.getRents();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/rents.fxml"));
            AnchorPane rents = (AnchorPane) loader.load();

            rootLayout.setCenter(rents);
            RentsViewController viewController = loader.getController();
            viewController.initialize(this, primaryStage, rentData);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Show rent db.
     *
     * @param primaryStage the primary stage
     * @param newRentData  the new rent data
     */
    public void showRentDB(Stage primaryStage, ObservableList<Rent> newRentData){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/rents.fxml"));
            AnchorPane rents = (AnchorPane) loader.load();

            rootLayout.setCenter(rents);
            RentsViewController viewController = loader.getController();
            viewController.initialize(this, primaryStage, newRentData);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Show rents edit page rent.
     *
     * @param primaryStage the primary stage
     * @param clickedRent  the clicked rent
     * @return the rent
     * @throws IOException the io exception
     */
    public Rent showRentsEditPage(Stage primaryStage, Rent clickedRent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/rentEdit.fxml"));
        AnchorPane editPage = (AnchorPane) loader.load();

        Stage dialogueStage = new Stage();
        dialogueStage.setTitle("Edit rent");
        dialogueStage.getIcons().add(new Image(
                Main.class.getResourceAsStream( "icon.png" )));
        dialogueStage.initOwner(primaryStage);
        dialogueStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(editPage);
        dialogueStage.setScene(scene);

        RentEditController controller = loader.getController();
        controller.initialize(this, dialogueStage, clickedRent);
        dialogueStage.showAndWait();
        if (controller.isOkClicked()){
            return controller.getClickedRent();
        }else{
            return null;
        }
    }

    /**
     * Show about.
     *
     * @throws IOException the io exception
     */
    public void showAbout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/about.fxml"));
        AnchorPane aboutPage = (AnchorPane) loader.load();

        rootLayout.setCenter(aboutPage);
    }

    /**
     * Show stats.
     *
     * @param primaryStage the primary stage
     * @param clickedCar   the clicked car
     * @throws IOException the io exception
     */
    public void showStats(Stage primaryStage, Car clickedCar) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/statistics.fxml"));
        AnchorPane editPage = (AnchorPane) loader.load();

        Stage dialogueStage = new Stage();
        dialogueStage.setTitle("Car statistics");
        dialogueStage.getIcons().add(new Image(
                Main.class.getResourceAsStream( "chart_icon.png" )));
        dialogueStage.initOwner(primaryStage);
        dialogueStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(editPage);
        dialogueStage.setScene(scene);

        StatisticsController controller = loader.getController();
        controller.initialize(this, dialogueStage, clickedCar);
        dialogueStage.showAndWait();
    }


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}