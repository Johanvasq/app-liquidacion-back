package co.com.ias.appback.domain.model.employee.attributes;


import java.util.regex.Pattern;

import static org.springframework.util.Assert.*;

public class EmployeeName {

    private final String value;

    public EmployeeName(String value) {
       validation(value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }


    /**
     * validation of value "Employee name"
     * @param value String
     * @throws IllegalArgumentException if the object isn't valid
     */
    private void validation(String value) throws IllegalArgumentException{
        isTrue(!Pattern.matches(".*[^a-zA-Z0-9 ].*", value),
                "name cannot contain special characters");
        isTrue(value.length() <= 50, "the size of the name is maximum 50 characters");
        isTrue(!value.isEmpty(), "The name cannot be empty");

    }
}
