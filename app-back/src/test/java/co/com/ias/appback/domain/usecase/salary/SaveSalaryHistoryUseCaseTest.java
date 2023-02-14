package co.com.ias.appback.domain.usecase.salary;

import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.employee.attributes.*;
import co.com.ias.appback.domain.model.gateway.salary.ISaveSalaryHistoryGateway;
import co.com.ias.appback.domain.model.salary_history.SalaryHistory;
import co.com.ias.appback.domain.model.salary_history.attributes.SalaryHistoryModificationDate;
import co.com.ias.appback.domain.model.salary_history.attributes.SalaryHistoryUpdatedSalary;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class SaveSalaryHistoryUseCaseTest {

    @InjectMocks
    private SaveSalaryHistoryUseCase saveSalaryHistoryUseCase;

    @Mock
    private ISaveSalaryHistoryGateway iSaveSalaryHistoryGateway;

    @Test
    void saveSalary() {
        Employee employee = new Employee(
                new EmployeeId("12345678"),
                new EmployeeName("Johan"),
                new EmployeeContractStart(LocalDate.of(2015,2,2)),
                new EmployeePosition("software developer"),
                new EmployeeState(true),
                new EmployeeCurrentSalary(1500000.0),
                new EmployeeLastSalaryUpdated(LocalDate.of(2015,2,2))

        );
        SalaryHistory salaryHistory = new SalaryHistory(
                employee,
                new SalaryHistoryUpdatedSalary(1500000.0),
                new SalaryHistoryModificationDate(LocalDate.of(2015,2,2))
        );

        when(iSaveSalaryHistoryGateway.saveSalaryHistory(salaryHistory))
                .thenReturn(salaryHistory);

        SalaryHistory rta = saveSalaryHistoryUseCase.saveSalary(salaryHistory);

        assertEquals(1500000.0, rta.getShUpdatedSalary().getValue());
        assertEquals(LocalDate.of(2015,2,2), rta.getShModificationDate().getValue());
    }
}