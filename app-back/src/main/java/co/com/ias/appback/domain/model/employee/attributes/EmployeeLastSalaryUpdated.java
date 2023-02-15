package co.com.ias.appback.domain.model.employee.attributes;

import java.time.LocalDate;

import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

public class EmployeeLastSalaryUpdated {

    private final LocalDate value;

    public EmployeeLastSalaryUpdated(LocalDate value) {
        validationContractStart(value);
        this.value = value;
    }

    public LocalDate getValue() {
        return value;
    }


    /**
     * validation of value "Last Salary updated"
     * @param value of value "Last Salary updated"
     * @throws IllegalArgumentException if the object isn't valid
     */
    private void validationContractStart(LocalDate value) throws IllegalArgumentException{
        notNull(value, "the date can't be empty");
        isTrue(value.isAfter(LocalDate.of(2015,1,1)),
                "the date can't be before of 01/01/2015");

    }
}
