package sample.utils;

import com.google.gson.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.models.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RestApiRequests {

    private static final String ServerURL = "http://localhost:8080/api/";

    public Employee getEmployeeByEmail(String loginEmail){
        String value = HttpConnection.GetRequest(ServerURL + "employees/email/" + loginEmail);
        if (value.equals("null")){
            Employee resultEmployee = new Employee();
            return resultEmployee;}
        else{
            JsonObject jsonResult = new JsonParser().parse(value).getAsJsonObject();

            Long employeeId = jsonResult.get("employeeId").getAsLong();
            String lastName = jsonResult.get("lastName").getAsString();
            String firstName = jsonResult.get("firstName").getAsString();
            String email = jsonResult.get("email").getAsString();
            String hashedPassword = jsonResult.get("hashedPassword").getAsString();
            JsonObject jobJson = jsonResult.get("job").getAsJsonObject();
            Job job = parseJob(jobJson);
            JsonObject officeJson = jsonResult.get("office").getAsJsonObject();
            Office office = parseOffice(officeJson);
            Employee resultEmployee = new Employee(employeeId, firstName, lastName, email, hashedPassword,
                    job, office);
            return resultEmployee;
        }

    }

    public Boolean deletePerson(Employee employee) throws IOException {
        Long id = employee.getEmployeeId();
        if (id == null)
            return false;
        Boolean value = HttpConnection.DeleteRequest(ServerURL + "employees/" + id);
        return value;
    }

    public ObservableList<Employee> getEmployees() throws IOException{
        ObservableList<Employee> employeeData = FXCollections.observableArrayList();
        String value = HttpConnection.GetRequest(ServerURL + "employees");
        if (value.equals("null")){
            return null;}
        else {
            JsonArray jsonResult = new JsonParser().parse(value).getAsJsonArray();

            for (int i = 0; i < jsonResult.size(); i++) {
                JsonObject currentEmployee = jsonResult.get(i).getAsJsonObject();

                Long employeeId = currentEmployee.get("employeeId").getAsLong();
                String lastName = currentEmployee.get("lastName").getAsString();
                String firstName = currentEmployee.get("firstName").getAsString();
                String email = currentEmployee.get("email").getAsString();
                String hashedPassword = currentEmployee.get("hashedPassword").getAsString();
                JsonObject jobJson = currentEmployee.get("job").getAsJsonObject();
                Job job = parseJob(jobJson);
                JsonObject officeJson = currentEmployee.get("office").getAsJsonObject();
                Office office = parseOffice(officeJson);
                Employee resultEmployee = new Employee(employeeId, firstName, lastName, email, hashedPassword,
                        job, office);
                employeeData.add(resultEmployee);
            }
        }
        return employeeData;
    }

    private static Job parseJob(JsonObject job){
        Long jobId = job.get("jobId").getAsLong();
        String jobName = job.get("jobName").getAsString();
        return new Job(jobId, jobName);
    }

    private static Office parseOffice(JsonObject office){
        Long officeId = office.get("officeId").getAsLong();
        String city = office.get("city").getAsString();
        String street = office.get("street").getAsString();
        String house = office.get("house").getAsString();
        String email = office.get("email").getAsString();
        return new Office(officeId, city, street, house, email);
    }

    public ObservableList<Job> getJobs() throws IOException {
        ObservableList<Job> jobData = FXCollections.observableArrayList();
        String value = HttpConnection.GetRequest(ServerURL + "jobs");
        if (value.equals("null")) {
            return null;
        } else {
            JsonArray jsonResult = new JsonParser().parse(value).getAsJsonArray();

            for (int i = 0; i < jsonResult.size(); i++) {
                JsonObject currentJob = jsonResult.get(i).getAsJsonObject();
                Job job = parseJob(currentJob);
                jobData.add(job);
            }
            return jobData;
        }
    }

    public ObservableList<Office> getOffices() throws IOException {
        ObservableList<Office> officeData = FXCollections.observableArrayList();
        String value = HttpConnection.GetRequest(ServerURL + "offices");
        if (value.equals("null")) {
            return null;
        } else {
            JsonArray jsonResult = new JsonParser().parse(value).getAsJsonArray();

            for (int i = 0; i < jsonResult.size(); i++) {
                JsonObject currentOffice = jsonResult.get(i).getAsJsonObject();
                Office office = parseOffice(currentOffice);
                officeData.add(office);
            }
            return officeData;
        }
    }

    public ObservableList<Rent> getRentsByOfficeId(Long id) throws IOException {
        ObservableList<Rent> rentData = FXCollections.observableArrayList();
        String value = HttpConnection.GetRequest(ServerURL + "rents/findbyoffice/"+id);
        if (value.equals("null")) {
            return null;
        } else {

            JsonElement jsonResult = new JsonParser().parse(value);
            if (jsonResult instanceof JsonObject){
                rentData.add(parseRent(jsonResult.getAsJsonObject()));
            } else {
                JsonArray rents = jsonResult.getAsJsonArray();
                for (int i = 0; i < rents.size(); i++) {
                    JsonObject currentRent = rents.get(i).getAsJsonObject();
                    Rent rent = parseRent(currentRent);
                    rentData.add(rent);}
            }
            return rentData;
        }
    }


    public ObservableList<Car> getCarsByOfficeId(Long id) throws IOException {
        ObservableList<Car> carData = FXCollections.observableArrayList();
        String value = HttpConnection.GetRequest(ServerURL + "cars/findbyoffice/"+id);
        if (value==null) {
            return null;
        } else {

            JsonElement jsonResult = new JsonParser().parse(value);
            if (jsonResult instanceof JsonObject){
                carData.add(parseCar(jsonResult.getAsJsonObject()));
            } else {
                JsonArray cars = jsonResult.getAsJsonArray();
                for (int i = 0; i < cars.size(); i++) {
                    JsonObject currentCar = cars.get(i).getAsJsonObject();
                    Car car = parseCar(currentCar);
                    carData.add(car);}
            }
            return carData;
        }
    }



    public Job getJobById(Long jobId) {
        System.out.println(jobId);
        String value = HttpConnection.GetRequest(ServerURL + "jobs/" + jobId);
        if (value.equals("null")) {
            Job resultJob = new Job();
            return resultJob;
        } else {
            System.out.println("hi");
            JsonObject jsonResult = new JsonParser().parse(value).getAsJsonObject();
            System.out.println(jsonResult);
            Job job = parseJob(jsonResult);
            System.out.println(job);
            return job;
        }
    }

    public Boolean deleteJob(Job job) throws IOException {
        Long id = job.getJobId();
        if (id == null)
            return false;
        Boolean value = HttpConnection.DeleteRequest(ServerURL + "jobs/" + id);
        return value;
    }

    public void createJob(Job job){
        System.out.println(job.toJson());
        HttpConnection.PostRequest(ServerURL + "jobs", job.toJson());
    }

    public void updateJob(Job job){
        System.out.println(job.toJson());
        HttpConnection.PutRequest(ServerURL + "jobs/" +job.getJobId(), job.toJson());
    }

    public Office getOfficeById(Long officeId) {
        String value = HttpConnection.GetRequest(ServerURL + "offices/" + officeId);
        if (value.equals("null")) {
            Office resultOffice = new Office();
            return null;
        } else {
            JsonObject jsonResult = new JsonParser().parse(value).getAsJsonObject();
            Office office = parseOffice(jsonResult);
            return office;
        }
    }

    public Boolean deleteOffice(Office office) throws IOException {
        Long id = office.getOfficeId();
        if (id == null)
            return false;
        Boolean value = HttpConnection.DeleteRequest(ServerURL + "offices/" + id);
        return value;
    }

    public void createOffice(Office office){
        System.out.println(office.toJson());
        HttpConnection.PostRequest(ServerURL + "offices", office.toJson());
    }

    public void updateOffice(Office office){
        System.out.println(office.toJson());
        HttpConnection.PutRequest(ServerURL + "offices/" +office.getOfficeId(), office.toJson());
    }

    public void createEmployee(Employee employee){
        System.out.println(employee.toJson());
        HttpConnection.PostRequest(ServerURL + "employees/", employee.toJson());
    }

    public void updateEmployee(Employee employee){
        System.out.println(employee.toJson());
        HttpConnection.PutRequest(ServerURL + "employees/" +employee.getEmployeeId(), employee.toJson());
    }

    public Employee getEmployeeById(Long id){
        String value = HttpConnection.GetRequest(ServerURL + "employees/" + id);
        if (value.equals("null")){
            Employee resultEmployee = new Employee();
            return resultEmployee;}
        else{
            JsonObject jsonResult = new JsonParser().parse(value).getAsJsonObject();

            Long employeeId = jsonResult.get("employeeId").getAsLong();
            String lastName = jsonResult.get("lastName").getAsString();
            String firstName = jsonResult.get("firstName").getAsString();
            String email = jsonResult.get("email").getAsString();
            String hashedPassword = jsonResult.get("hashedPassword").getAsString();
            JsonObject jobJson = jsonResult.get("job").getAsJsonObject();
            Job job = parseJob(jobJson);
            JsonObject officeJson = jsonResult.get("office").getAsJsonObject();
            Office empOffice = parseOffice(officeJson);
            Employee resultEmployee = new Employee(employeeId, firstName, lastName, email, hashedPassword,
                    job, empOffice);
            return resultEmployee;
        }
    }


    private static Rate parseRate(JsonObject rate){
        Long rateId = rate.get("rateId").getAsLong();
        String rateName = rate.get("rateName").getAsString();
        Double price = rate.get("price").getAsDouble();
        return new Rate(rateId, rateName, price);
    }

    public ObservableList<Rate> getRates() throws IOException {
        ObservableList<Rate> rateData = FXCollections.observableArrayList();
        String value = HttpConnection.GetRequest(ServerURL + "rates");
        if (value == null) {
            return null;
        } else {
            JsonArray jsonResult = new JsonParser().parse(value).getAsJsonArray();

            for (int i = 0; i < jsonResult.size(); i++) {
                JsonObject currentRate = jsonResult.get(i).getAsJsonObject();
                Rate rate = parseRate(currentRate);
                rateData.add(rate);
            }
            return rateData;
        }
    }

    public Boolean deleteRate(Rate rate) throws IOException {
        Long id = rate.getRateId();
        if (id == null)
            return false;
        Boolean value = HttpConnection.DeleteRequest(ServerURL + "rates/" + id);
        return value;
    }

    public void createRate(Rate rate){
        System.out.println(rate.toJson());
        HttpConnection.PostRequest(ServerURL + "rates", rate.toJson());
    }

    public void updateRate(Rate rate){
        System.out.println(rate.toJson());
        HttpConnection.PutRequest(ServerURL + "rates/" +rate.getRateId(), rate.toJson());
    }


    public Boolean deleteCar(Car car) throws IOException {
        Long id = car.getCarId();
        if (id == null)
            return false;
        Boolean value = HttpConnection.DeleteRequest(ServerURL + "cars/" + id);
        return value;
    }

    public void createCar(Car car){
        System.out.println(car.toJson());
        HttpConnection.PostRequest(ServerURL + "cars", car.toJson());
    }

    public void updateCar(Car car){
        System.out.println(car.toJson());
        HttpConnection.PutRequest(ServerURL + "cars/" +car.getCarId(), car.toJson());
    }

    private static Insurance parseInsurance(JsonObject jsonInsurance){
        Long insuranceId = jsonInsurance.get("insuranceId").getAsLong();
        LocalDate startDate = DateUtil.parse(jsonInsurance.get("startDate").getAsString());
        LocalDate endDate = DateUtil.parse(jsonInsurance.get("endDate").getAsString());
        Car car = parseCar(jsonInsurance.get("car").getAsJsonObject());
        Double price = jsonInsurance.get("price").getAsDouble();
        return new Insurance(insuranceId, startDate, endDate, price, car);
    }

    private static Car parseCar(JsonObject car){
        Long carId = car.get("carId").getAsLong();
        String brand = car.get("brand").getAsString();
        Double startingPrice = car.get("startingPrice").getAsDouble();
        Boolean status = car.get("status").getAsBoolean();
        Office office = parseOffice(car.get("office").getAsJsonObject());
        return new Car(carId, brand, startingPrice, status, office);
    }

    public ObservableList<Car> getCars() throws IOException {
        ObservableList<Car> carData = FXCollections.observableArrayList();
        String value = HttpConnection.GetRequest(ServerURL + "cars");
        if (value.equals("null")) {
            return null;
        } else {
            JsonArray jsonResult = new JsonParser().parse(value).getAsJsonArray();

            for (int i = 0; i < jsonResult.size(); i++) {
                JsonObject currentCar = jsonResult.get(i).getAsJsonObject();
                Car car = parseCar(currentCar);
                carData.add(car);
            }
            return carData;
        }
    }

    public void createInsurance(Insurance insurance){
        System.out.println(insurance.toJson());
        HttpConnection.PostRequest(ServerURL + "insurances", insurance.toJson());
    }

    public void updateInsurance(Insurance insurance){
        System.out.println(insurance.toJson());
        HttpConnection.PutRequest(ServerURL + "insurances/" +insurance.getInsuranceId(), insurance.toJson());
    }

    public Insurance getInsuranceByCarId(Car car){
        Long id = car.getCarId();
        String value = HttpConnection.GetRequest(ServerURL + "insurances/findbycar/" + id);
        if (value.equals("null")) {
            return null;
        } else {
            JsonObject jsonInsurance = new JsonParser().parse(value).getAsJsonObject();
            return parseInsurance(jsonInsurance);
        }
    }

    public Boolean deleteClient(Client client) throws IOException {
        Long id = client.getClientId();
        if (id == null)
            return false;
        Boolean value = HttpConnection.DeleteRequest(ServerURL + "clients/" + id);
        return value;
    }

    public void createClient(Client client){
        System.out.println(client.toJson());
        HttpConnection.PostRequest(ServerURL + "clients", client.toJson());
    }

    public void updateClient(Client client){
        System.out.println(client.toJson());
        HttpConnection.PutRequest(ServerURL + "clients/" +client.getClientId(), client.toJson());
    }

    private static Client parseClient(JsonObject jsonClient){
        Long clientId = jsonClient.get("clientId").getAsLong();
        String lastName = jsonClient.get("lastName").getAsString();
        String firstName = jsonClient.get("firstName").getAsString();
        String driverLicense = jsonClient.get("driverLicense").getAsString();
        String passport = jsonClient.get("passport").getAsString();
        String phone = jsonClient.get("phone").getAsString();
        Boolean isBlackListed = jsonClient.get("isBlackListed").getAsBoolean();
        return new Client(clientId, lastName, firstName, driverLicense, passport, phone, isBlackListed);
    }

    public ObservableList<Client> getClients() throws IOException {
        ObservableList<Client> clientData = FXCollections.observableArrayList();
        String value = HttpConnection.GetRequest(ServerURL + "clients");
        if (value==null) {
            return null;
        } else {
            JsonArray jsonResult = new JsonParser().parse(value).getAsJsonArray();

            for (int i = 0; i < jsonResult.size(); i++) {
                JsonObject currentClient = jsonResult.get(i).getAsJsonObject();
                Client client = parseClient(currentClient);
                clientData.add(client);
            }
            return clientData;
        }
    }

    public Boolean deleteRent(Rent rent) throws IOException {
        Long id = rent.getRentId();
        if (id == null)
            return false;
        Boolean value = HttpConnection.DeleteRequest(ServerURL + "rents/" + id);
        return value;
    }

    public void createRent(Rent rent){
        System.out.println(rent.toJson());
        HttpConnection.PostRequest(ServerURL + "rents", rent.toJson());
    }

    public void updateRent(Rent rent){
        System.out.println(rent.toJson());
        HttpConnection.PutRequest(ServerURL + "rents/" +rent.getRentId(), rent.toJson());
    }

    private static Rent parseRent(JsonObject jsonRent){
        Long rentId = jsonRent.get("rentId").getAsLong();
        LocalDate startDate = DateUtil.parse(jsonRent.get("startDate").getAsString());
        LocalDate endDate = DateUtil.parse(jsonRent.get("endDate").getAsString());
        Double finalPrice = jsonRent.get("finalPrice").getAsDouble();
        Rate rate = parseRate(jsonRent.get("rate").getAsJsonObject());
        Client client = parseClient(jsonRent.get("client").getAsJsonObject());
        Car car = parseCar(jsonRent.get("car").getAsJsonObject());
        return new Rent(rentId, startDate, endDate, finalPrice, rate, client, car);
    }

    public ObservableList<Rent> getRents() throws IOException {
        ObservableList<Rent> rentData = FXCollections.observableArrayList();
        String value = HttpConnection.GetRequest(ServerURL + "rents");
        if (value==null) {
            return null;
        } else {
            JsonArray jsonResult = new JsonParser().parse(value).getAsJsonArray();

            for (int i = 0; i < jsonResult.size(); i++) {
                JsonObject currentRent = jsonResult.get(i).getAsJsonObject();
                Rent rent = parseRent(currentRent);
                rentData.add(rent);
            }
            return rentData;
        }
    }

    public Client getClientById(Long clientId) {
        String value = HttpConnection.GetRequest(ServerURL + "clients/" + clientId);
        if (value.equals("null")) {
            Client resultClient = new Client();
            return null;
        } else {
            JsonObject jsonResult = new JsonParser().parse(value).getAsJsonObject();
            Client client = parseClient(jsonResult);
            return client;
        }
    }

    public Car getCarById(Long carId) {
        String value = HttpConnection.GetRequest(ServerURL + "cars/" + carId);
        if (value.equals("null")) {
            Car resultCar = new Car();
            return null;
        } else {
            JsonObject jsonResult = new JsonParser().parse(value).getAsJsonObject();
            Car car = parseCar(jsonResult);
            return car;
        }
    }

    public Rate getRateById(Long rateId) {
        String value = HttpConnection.GetRequest(ServerURL + "rates/" + rateId);
        if (value.equals("null")) {
            Rate resultRate = new Rate();
            return null;
        } else {
            JsonObject jsonResult = new JsonParser().parse(value).getAsJsonObject();
            Rate rate = parseRate(jsonResult);
            return rate;
        }
    }

}
