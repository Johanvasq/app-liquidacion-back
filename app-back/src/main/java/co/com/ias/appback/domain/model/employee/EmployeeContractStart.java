package co.com.ias.appback.domain.model.employee;

import java.time.LocalDate;

public class EmployeeContractStart {

    private final LocalDate value;

    public EmployeeContractStart(LocalDate value) {
        this.value = value;
    }

    public LocalDate getValue() {
        return value;
    }
}
