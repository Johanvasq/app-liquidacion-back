package co.com.ias.appback.domain.model.employee.attributes;



import static org.springframework.util.Assert.*;

public class EmployeeId {
    private final String value;

    public EmployeeId(String value) {
        validation(value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * validation of value "Employee Id"
     * @param value String
     * @return true or false
     * @throws IllegalArgumentException if the object isn't valid
     */
    private void validation(String value) throws IllegalArgumentException{
        isTrue(value.length() >= 7 && value.length() <= 15,
                "the size of the id is only between 7 and 15 digits");
        isTrue(!value.isEmpty(), "the id cannot be empty");


    }
}
