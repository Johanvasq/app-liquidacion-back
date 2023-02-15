package co.com.ias.appback.domain.model.employee.attributes;


import static org.springframework.util.Assert.*;

public class EmployeeCurrentSalary {

    private final Double value;

    public EmployeeCurrentSalary(Double value) {
        validation(value);
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    /**
     * validation of value "Current Salary"
     * @param value
     * @return true or false
     * @throws IllegalArgumentException if the object isn't valid
     */
    private void validation(double value) throws IllegalArgumentException{
        notNull(value, "the salary can't be empty");
        notNull(value >= 1160000, "the salary can't be less than SMMLV ($1.160.000)");
        notNull(value <= 7000000, "the salary can't be more than $7.000.000");

    }
}
