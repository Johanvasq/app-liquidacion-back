package co.com.ias.appback.domain.model.salary_history.attributes;

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
