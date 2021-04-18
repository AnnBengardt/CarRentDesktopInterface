package sample.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.models.Employee;

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
            Boolean isMainManager = jsonResult.get("isMainManager").getAsBoolean();
            JsonObject job = jsonResult.get("job").getAsJsonObject();
            Long jobId = job.get("jobId").getAsLong();
            Long officeId = jsonResult.get("office").getAsJsonObject().get("officeId").getAsLong();

            Employee resultEmployee = new Employee(employeeId, firstName, lastName, email, hashedPassword, isMainManager,
                    jobId, officeId);
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
                Boolean isMainManager = currentEmployee.get("isMainManager").getAsBoolean();
                JsonObject job = currentEmployee.get("job").getAsJsonObject();
                Long jobId = job.get("jobId").getAsLong();
                Long officeId = currentEmployee.get("office").getAsJsonObject().get("officeId").getAsLong();

                Employee resultEmployee = new Employee(employeeId, firstName, lastName, email, hashedPassword, isMainManager,
                        jobId, officeId);
                employeeData.add(resultEmployee);
            }
        }
        return employeeData;
    }

}
