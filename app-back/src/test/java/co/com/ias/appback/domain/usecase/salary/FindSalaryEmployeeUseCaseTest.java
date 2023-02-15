package co.com.ias.appback.domain.usecase.salary;

import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.employee.attributes.*;
import co.com.ias.appback.domain.model.gateway.employee.IFindEmployeeByIdGateway;
import co.com.ias.appback.domain.model.gateway.salary.IFindAllSalaryHistoryGateway;
import co.com.ias.appback.domain.model.salary_history.SalaryHistory;
import co.com.ias.appback.domain.model.salary_history.attributes.SalaryHistoryModificationDate;
import co.com.ias.appback.domain.model.salary_history.attributes.SalaryHistoryUpdatedSalary;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class FindSalaryEmployeeUseCaseTest {

    @InjectMocks
    private FindSalaryEmployeeUseCase findSalaryEmployeeUseCase;

    @Mock
    private IFindAllSalaryHistoryGateway iFindAllSalaryHistoryGateway;
    @Mock
    private IFindEmployeeByIdGateway iFindEmployeeByIdGateway;

    @Test
    void findSalaryByEmployeeId() {
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

        when(iFindEmployeeByIdGateway.findById("12345678"))
                .thenReturn(employee);

        when(iFindAllSalaryHistoryGateway.findAllSalaryHistoryByEmployeeId("12345678"))
                .thenReturn(List.of(salaryHistory));

        List<SalaryHistory> rta = findSalaryEmployeeUseCase.findSalaryByEmployeeId("12345678");
        assertEquals(1500000.0, rta.get(0).getShUpdatedSalary().getValue());
        assertEquals(LocalDate.of(2015,2,2), rta.get(0).getShModificationDate().getValue());
    }

    @Test
    void testFindSalaryByEmployeeId() {
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

        when(iFindEmployeeByIdGateway.findById("12345678"))
                .thenReturn(null);

        EntityExistsException rta = assertThrows(EntityExistsException.class, () -> {
            findSalaryEmployeeUseCase.findSalaryByEmployeeId("12345678");
        });
        assertEquals("ID doesn't exist, please try to provide an existing employee ID or check the provided ID", rta.getMessage());
    }
}