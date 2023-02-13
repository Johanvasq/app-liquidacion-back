package co.com.ias.appback.domain.model.liquidation_payment_request.attributes;

import java.time.LocalDate;

import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

public class EmployeeContractEnd {

    private final LocalDate value;

    public EmployeeContractEnd(LocalDate value) {
        validation(value);
        this.value = value;
    }

    public LocalDate getValue() {
        return value;
    }

    private void validation(LocalDate value) throws IllegalArgumentException{
        notNull(value, "the date can't be empty");
        isTrue(value.isAfter(LocalDate.of(2015,1,1)),
                "the date can't be before of 01/01/2015");


    }
}
