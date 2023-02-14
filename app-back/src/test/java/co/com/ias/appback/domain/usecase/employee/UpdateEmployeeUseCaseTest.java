package co.com.ias.appback.domain.usecase.employee;

import co.com.ias.appback.domain.model.constants.GlobalConstants;
import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.employee.attributes.*;
import co.com.ias.appback.domain.model.gateway.employee.IFindEmployeeByIdGateway;
import co.com.ias.appback.domain.model.gateway.employee.IUpdateEmployeeGateway;
import co.com.ias.appback.domain.model.gateway.salary.ISaveSalaryHistoryGateway;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UpdateEmployeeUseCaseTest {
    @InjectMocks
    private UpdateEmployeeUseCase updateEmployeeUseCase;
    @Mock
    private IUpdateEmployeeGateway iupdateEmployeeGateway;
    @Mock
    private IFindEmployeeByIdGateway iFindEmployeeByIdGateway;
    @Mock
    private ISaveSalaryHistoryGateway iSaveSalaryHistoryGateway;


    @Test
    @DisplayName("Update employee ok")
    void updateEmployee() {
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
        when(iupdateEmployeeGateway.updateEmployee(any(Employee.class)))
                .thenReturn(employee);

        Employee rta = updateEmployeeUseCase.updateEmployee("12345678", "software developer",
                2000000d, LocalDate.of(2016,2,2) );

        assertEquals(rta.getEmployeeId().getValue(), employee.getEmployeeId().getValue());
    }

    @Test
    @DisplayName("Update employee not found")
    void testUpdateEmployee() {
        Employee employee = new Employee(
                new EmployeeId("12345678"),
                new EmployeeName("Johan"),
                new EmployeeContractStart(LocalDate.of(2015,2,2)),
                new EmployeePosition("software developer"),
                new EmployeeState(true),
                new EmployeeCurrentSalary(1500000.0),
                new EmployeeLastSalaryUpdated(LocalDate.of(2015,2,2))
        );
        when(iFindEmployeeByIdGateway.findById("1234598411"))
                .thenReturn(null);

        EntityExistsException exception = assertThrows(EntityExistsException.class,
                () -> updateEmployeeUseCase.updateEmployee("1234598411", "new position", 2000000.0, LocalDate.of(2023, 2, 14)));
        assertEquals("ID doesn't exist, please try to provide an existing employee ID or check the provided ID", exception.getMessage());
    }
    @Test
    @DisplayName("Update employee inactive")
    void test2UpdateEmployee() {
        Employee employee = new Employee(
                new EmployeeId("12345678"),
                new EmployeeName("Johan"),
                new EmployeeContractStart(LocalDate.of(2015,2,2)),
                new EmployeePosition("software developer"),
                new EmployeeState(false),
                new EmployeeCurrentSalary(1500000.0),
                new EmployeeLastSalaryUpdated(LocalDate.of(2015,2,2))
        );
        when(iFindEmployeeByIdGateway.findById("1234598411"))
                .thenReturn(employee);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> updateEmployeeUseCase.updateEmployee("1234598411", "new position", 2000000.0, LocalDate.of(2023, 2, 14)));
        assertEquals("Cannot modify inactive employees", exception.getMessage());
    }

    @Test
    @DisplayName("Update employee inactive")
    void test3UpdateEmployee() {
        Employee employee = new Employee(
                new EmployeeId("12345678"),
                new EmployeeName("Johan"),
                new EmployeeContractStart(LocalDate.of(2015,2,2)),
                new EmployeePosition("software developer"),
                new EmployeeState(true),
                new EmployeeCurrentSalary(2000000d),
                new EmployeeLastSalaryUpdated(LocalDate.of(2015,2,2))
        );
        when(iFindEmployeeByIdGateway.findById("1234598411"))
                .thenReturn(employee);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> updateEmployeeUseCase.updateEmployee("1234598411", "new position", 1500000d, LocalDate.of(2023, 2, 14)));
        assertEquals("the salary provided must be higher than the previous one: " + "2000000.0", exception.getMessage());
    }

    @Test
    @DisplayName("Update employee inactive")
    void test4UpdateEmployee() {
        Employee employee = new Employee(
                new EmployeeId("12345678"),
                new EmployeeName("Johan"),
                new EmployeeContractStart(LocalDate.of(2015,2,2)),
                new EmployeePosition("software developer"),
                new EmployeeState(true),
                new EmployeeCurrentSalary(1500000d),
                new EmployeeLastSalaryUpdated(LocalDate.of(2019,2,2))
        );
        when(iFindEmployeeByIdGateway.findById("1234598411"))
                .thenReturn(employee);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> updateEmployeeUseCase.updateEmployee("1234598411", "new position", 2500000d, LocalDate.of(2016, 2, 14)));
        assertEquals("the date provided must be higher than the previous one: " +
                employee.getEmployeeLastSalaryUpdated().getValue().format(GlobalConstants.DATE_FORMAT),
                exception.getMessage());
    }
}