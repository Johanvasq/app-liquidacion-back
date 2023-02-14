package co.com.ias.appback.domain.usecase.employee;

import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.employee.attributes.*;
import co.com.ias.appback.domain.model.gateway.employee.IFindEmployeeByIdGateway;
import co.com.ias.appback.domain.usecase.salary.SaveSalaryHistoryUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class FindEmployeeByIdUseCaseTest {
    @InjectMocks
    private FindEmployeeByIdUseCase findEmployeeByIdUseCase;

    @Mock
    private IFindEmployeeByIdGateway IFindEmployeeByIdGateway;

    @Mock
    private SaveSalaryHistoryUseCase saveSalaryHistoryUseCase;
    @Test
    @DisplayName("Find Employee by id")
    void findEmployeeById() {
        Employee employee = new Employee(
                new EmployeeId("12345678"),
                new EmployeeName("Johan"),
                new EmployeeContractStart(LocalDate.of(2015,2,2)),
                new EmployeePosition("software developer"),
                new EmployeeState(true),
                new EmployeeCurrentSalary(1500000.0),
                new EmployeeLastSalaryUpdated(LocalDate.of(2015,2,2))

        );
        when(IFindEmployeeByIdGateway.findById("12345678"))
                .thenReturn(employee);
        //Act
        //Assert
        Employee employeeTest = findEmployeeByIdUseCase.findEmployeeById("12345678");
        assertEquals(employeeTest, employee);
        assertEquals(employeeTest.getEmployeeId().getValue(), employee.getEmployeeId().getValue());
    }
}