package co.com.ias.appback.domain.usecase;

import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.employee.attributes.*;
import co.com.ias.appback.domain.model.gateway.IFindEmployeeByIdGateway;
import co.com.ias.appback.domain.model.gateway.ISaveEmployeeGateway;
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
    private ISaveEmployeeGateway ISaveEmployeeGateway;
    @Mock
    private IFindEmployeeByIdGateway IFindEmployeeByIdGateway;

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
                new EmployeeCurrentSalary(1500000.0)
        );
        when(ISaveEmployeeGateway.saveEmployee(any(Employee.class)))
                .thenReturn(employee);
        when(IFindEmployeeByIdGateway.findById("1234598411"))
                .thenReturn(null);
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
                new EmployeeCurrentSalary(1500000.0)
        );
        /*when(saveEmployeeGateway.saveEmployee(any(Employee.class)))
                .thenReturn(employee);*/
        when(IFindEmployeeByIdGateway.findById("12345678"))
                .thenReturn(employee);
        //Act
        //Assert
        Throwable exception = assertThrows(EntityExistsException.class, () -> saveEmployeeUseCase.saveEmployee(employee));
        assertTrue(exception.getMessage().contains("The id already exists, please try to modify the existing employee or check the provided id"));
    }
}