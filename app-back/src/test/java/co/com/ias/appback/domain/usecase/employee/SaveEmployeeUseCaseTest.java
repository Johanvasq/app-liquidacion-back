package co.com.ias.appback.domain.usecase.employee;

import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.employee.attributes.*;
import co.com.ias.appback.domain.model.gateway.employee.IFindEmployeeByIdGateway;
import co.com.ias.appback.domain.model.gateway.employee.ISaveEmployeeGateway;
import co.com.ias.appback.domain.model.salary_history.SalaryHistory;
import co.com.ias.appback.domain.usecase.salary.SaveSalaryHistoryUseCase;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class SaveEmployeeUseCaseTest {

    @InjectMocks
    private SaveEmployeeUseCase saveEmployeeUseCase;

    @Mock
    private ISaveEmployeeGateway iSaveEmployeeGateway;
    @Mock
    private IFindEmployeeByIdGateway iFindEmployeeByIdGateway;

    @Mock
    private SaveSalaryHistoryUseCase saveSalaryHistoryUseCase;

    @Test
    @DisplayName("save employee ok")
    void saveEmployee() {
        //Arrange
        Employee employee = new Employee(
                new EmployeeId("12345678"),
                new EmployeeName("Johan"),
                new EmployeeContractStart(LocalDate.of(2015,2,2)),
                new EmployeePosition("software developer"),
                new EmployeeState(true),
                new EmployeeCurrentSalary(1500000.0),
                new EmployeeLastSalaryUpdated(LocalDate.of(2015,2,2))

        );
        when(iSaveEmployeeGateway.saveEmployee(any(Employee.class)))
                .thenReturn(employee);

        when(iFindEmployeeByIdGateway.findById("1234598411"))
                .thenReturn(null);

        when(saveSalaryHistoryUseCase.saveSalary(any(SalaryHistory.class)))
                .thenReturn(any(SalaryHistory.class));
        //Act
        //Assert
        Employee employeeTest = saveEmployeeUseCase.saveEmployee(employee);
        assertEquals(employeeTest, employee);
        assertEquals(employeeTest.getEmployeeId().getValue(), employee.getEmployeeId().getValue());
    }

    @Test
    @DisplayName("save employee throws exception")
    void testSaveEmployee() {
        //Arrange
        Employee employee = new Employee(
                new EmployeeId("12345678"),
                new EmployeeName("Johan"),
                new EmployeeContractStart(LocalDate.of(2015,2,2)),
                new EmployeePosition("software developer"),
                new EmployeeState(true),
                new EmployeeCurrentSalary(1500000.0),
                new EmployeeLastSalaryUpdated(LocalDate.of(2015,2,2))
        );
        when(iFindEmployeeByIdGateway.findById("12345678"))
                .thenReturn(employee);
        Throwable exception = assertThrows(EntityExistsException.class, () -> saveEmployeeUseCase.saveEmployee(employee));
        assertTrue(exception.getMessage().contains("The id already exists, please try to modify the existing employee or check the provided id"));
    }
}