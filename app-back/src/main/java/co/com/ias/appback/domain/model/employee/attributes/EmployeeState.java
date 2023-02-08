package co.com.ias.appback.domain.model.employee.attributes;

import org.springframework.util.Assert;

public class EmployeeState {

    private final Boolean value;

    public EmployeeState(Boolean value) {
        Assert.notNull(value, "the state of the employee can't be null");
        this.value = value;
    }

    public Boolean getValue() {
        return value;
    }
}
