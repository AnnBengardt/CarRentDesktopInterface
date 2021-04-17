package sample.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import sample.models.Employee;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RestApiRequests {

    private static final String ServerURL = "http://localhost:8080/api/";

    public Employee getEmployeeByEmail(String loginEmail){
        String value = HttpConnection.GetRequest(ServerURL + "employees/email/" + loginEmail);
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
