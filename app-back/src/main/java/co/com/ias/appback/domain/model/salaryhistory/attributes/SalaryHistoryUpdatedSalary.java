package co.com.ias.appback.domain.model.salaryhistory.attributes;

import java.time.LocalDate;

import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

public class SalaryHistoryUpdatedSalary {

    private final Double value;

    public SalaryHistoryUpdatedSalary(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }


}
