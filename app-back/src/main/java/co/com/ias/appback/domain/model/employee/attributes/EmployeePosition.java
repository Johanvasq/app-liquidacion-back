package co.com.ias.appback.domain.model.employee.attributes;



import static java.util.regex.Pattern.*;
import static org.springframework.util.Assert.*;

public class EmployeePosition {

    private final String value;

    public EmployeePosition(String value) {
        validation(value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }


    /**
     * validation of value "Employee position"
     * @param value String
     * @throws IllegalArgumentException if the object isn't valid
     */
    private void validation(String value) throws IllegalArgumentException{
        if (value != null){
            isTrue(value.length() >= 10 && value.length() <= 30,
                    "position size is only between 10 and 30 characters");
            isTrue(!matches(".*[^a-zA-Z0-9 ].*", value),
                    "the position cannot contain special characters");
        }
    }
}
