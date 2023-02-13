package co.com.ias.appback.domain.model.salary_history.attributes;

import java.time.LocalDate;

import static org.springframework.util.Assert.notNull;

public class SalaryHistoryModificationDate {

    private final LocalDate value;

    public SalaryHistoryModificationDate(LocalDate value) {
        validationContractStart(value);
        this.value = value;
    }

    public LocalDate getValue() {
        return value;
    }

    private void validationContractStart(LocalDate value) throws IllegalArgumentException{
        notNull(value, "the date can't be empty");

    }
}
