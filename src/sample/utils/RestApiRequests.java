package sample.utils;

import com.google.gson.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.models.Employee;
import sample.models.Job;
import sample.models.Office;
import sample.models.Rate;

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


}
