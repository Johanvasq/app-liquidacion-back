package co.com.ias.appback.domain.model.employee.attributes;
import java.time.LocalDate;

import static org.springframework.util.Assert.*;

public class EmployeeContractStart {

    private final LocalDate value;

    public EmployeeContractStart(LocalDate value) {
        validation(value);
        this.value = value;
    }

    public LocalDate getValue() {
        return value;
    }


    /**
     * validation of value "Contract Start"
     * @param value of value "Contract start"
     * @throws IllegalArgumentException if the object isn't valid
     */
    private void validation(LocalDate value) throws IllegalArgumentException{
        notNull(value, "the date can't be empty");
        isTrue(value.isAfter(LocalDate.of(2015,1,1)),
                "the date can't be before of 01/01/2015");
        isTrue(value.isBefore(LocalDate.of(2015,6,6)),
                "the date can't be after of 06/06/2015");

    }

}
