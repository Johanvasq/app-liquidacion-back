package co.com.ias.appback.infrastructure.adapters;

import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.employee.attributes.*;
import co.com.ias.appback.infrastructure.adapters.jpa.EmployeeRepositoryAdapter;
import co.com.ias.appback.infrastructure.adapters.jpa.repository.IEmployeeRepositoryAdapter;
import co.com.ias.appback.infrastructure.adapters.jpa.entity.EmployeeDBO;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class EmployeeRepositoryAdapterTest {

    @InjectMocks
    private EmployeeRepositoryAdapter employeeRepositoryAdapter;

    @Autowired
    IEmployeeRepositoryAdapter repository;

    @BeforeAll
    void init(){ employeeRepositoryAdapter = new EmployeeRepositoryAdapter(repository); }

    @Test
    @DisplayName("find employee by id ok")
    void findById() {
        Employee employee = new Employee(
                new EmployeeId("12345678"),
                new EmployeeName("Johan"),
                new EmployeeContractStart(LocalDate.of(2015,2,2)),
                new EmployeePosition("software developer"),
                new EmployeeState(true),
                new EmployeeCurrentSalary(1500000.0)
        );
        EmployeeDBO employeeDBO = new EmployeeDBO().fromDomain(employee);

        repository.save(employeeDBO);

        Employee rta = employeeRepositoryAdapter.findById("12345678");
        assertEquals(rta.getClass(), employee.getClass());
        assertEquals(rta.getEmployeeName().getValue(), employee.getEmployeeName().getValue());
        assertEquals(rta.getEmployeeName().getValue(), employeeDBO.getName());
        assertEquals(rta.getEmployeeState().getValue(), employee.getEmployeeState().getValue());
        assertEquals(rta.getEmployeeState().getValue(), employeeDBO.getState());
        assertEquals(rta.getEmployeeContractStart().getValue(), employee.getEmployeeContractStart().getValue());
        assertEquals(rta.getEmployeeContractStart().getValue(), employeeDBO.getContractStart());
    }
    @Test
    @DisplayName("find employee by id return null")
    void testFindById() {
        Employee employee = new Employee(
                new EmployeeId("12345678"),
                new EmployeeName("Johan"),
                new EmployeeContractStart(LocalDate.of(2015,2,2)),
                new EmployeePosition("software developer"),
                new EmployeeState(true),
                new EmployeeCurrentSalary(1500000.0)
        );
        EmployeeDBO employeeDBO = new EmployeeDBO().fromDomain(employee);

        Employee rta = employeeRepositoryAdapter.findById("12345678");
        assertNull(rta);


    }

    @Test
    @DisplayName("save employee ok")
    void saveEmployee() {
        Employee employee = new Employee(
                new EmployeeId("12345678"),
                new EmployeeName("Johan"),
                new EmployeeContractStart(LocalDate.of(2015,2,2)),
                new EmployeePosition("software developer"),
                new EmployeeState(true),
                new EmployeeCurrentSalary(1500000.0)
        );
        EmployeeDBO employeeDBO = new EmployeeDBO().fromDomain(employee);

        repository.save(employeeDBO);

        Employee rta = employeeRepositoryAdapter.saveEmployee(employee);
        assertEquals(rta.getEmployeeName().getValue(), employee.getEmployeeName().getValue());
        assertEquals(rta.getEmployeeState().getValue(), employee.getEmployeeState().getValue());
        assertEquals(rta.getEmployeeContractStart().getValue(), employee.getEmployeeContractStart().getValue());

    }
}