package co.com.ias.appback.infrastructure.adapters.jpa;

import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.employee.attributes.*;
import co.com.ias.appback.domain.model.salary_history.SalaryHistory;
import co.com.ias.appback.infrastructure.adapters.jpa.entity.EmployeeDBO;
import co.com.ias.appback.infrastructure.adapters.jpa.entity.SalaryHistoryDBO;
import co.com.ias.appback.infrastructure.adapters.jpa.repository.IEmployeeRepositoryAdapter;
import co.com.ias.appback.infrastructure.adapters.jpa.repository.ISalaryHistoryRepositoryAdapter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class SalaryHistoryRepositoryAdapterTest {
    @InjectMocks
    private SalaryHistoryRepositoryAdapter salaryHistoryRepositoryAdapter;

    @Autowired
    private ISalaryHistoryRepositoryAdapter repository;
    @Autowired
    IEmployeeRepositoryAdapter repositoryEmployee;

    @BeforeAll
    void init(){ salaryHistoryRepositoryAdapter = new SalaryHistoryRepositoryAdapter(repository); }


    @Test
    @DisplayName("Save salary")
    void saveSalaryHistory() {
        Employee employee = new Employee(
                new EmployeeId("123456789"),
                new EmployeeName("Johan"),
                new EmployeeContractStart(LocalDate.of(2015,2,2)),
                new EmployeePosition("software developer"),
                new EmployeeState(true),
                new EmployeeCurrentSalary(1500000.0),
                new EmployeeLastSalaryUpdated(LocalDate.of(2015,2,2))

        );
        EmployeeDBO employeeDBO = new EmployeeDBO().fromDomain(employee);
        SalaryHistoryDBO salaryHistoryDBO = new SalaryHistoryDBO(
                employeeDBO,
                2000000d,
                LocalDate.of(2017,2,2)
        );
        SalaryHistory salaryHistory = salaryHistoryDBO.toDomain();

        repository.save(salaryHistoryDBO);

        SalaryHistory rta = salaryHistoryRepositoryAdapter.saveSalaryHistory(salaryHistory);

        assertEquals(rta.getClass(), salaryHistory.getClass());
        assertEquals(rta.getShUpdatedSalary().getValue(), salaryHistory.getShUpdatedSalary().getValue());
        assertEquals(rta.getShUpdatedSalary().getValue(), salaryHistoryDBO.getUpdatedSalary());
        assertEquals(rta.getShModificationDate().getValue(), salaryHistory.getShModificationDate().getValue());
        assertEquals(rta.getShModificationDate().getValue(), salaryHistoryDBO.getModificationDate());

    }

    @Test
    @DisplayName("find salaries")
    void findAllSalaryHistoryByEmployeeId() {
        Employee employee = new Employee(
                new EmployeeId("123456789"),
                new EmployeeName("Johan"),
                new EmployeeContractStart(LocalDate.of(2015,2,2)),
                new EmployeePosition("software developer"),
                new EmployeeState(true),
                new EmployeeCurrentSalary(1500000.0),
                new EmployeeLastSalaryUpdated(LocalDate.of(2015,2,2))

        );
        EmployeeDBO employeeDBO = new EmployeeDBO().fromDomain(employee);
        SalaryHistoryDBO salaryHistoryDBO = new SalaryHistoryDBO(
                employeeDBO,
                2000000d,
                LocalDate.of(2017,2,2)
        );
        SalaryHistory salaryHistory = salaryHistoryDBO.toDomain();

        repositoryEmployee.save(employeeDBO);
        repository.save(salaryHistoryDBO);

        List<SalaryHistory> rta = salaryHistoryRepositoryAdapter.findAllSalaryHistoryByEmployeeId("123456789");

        assertEquals(rta.get(0).getClass(), salaryHistory.getClass());
        assertEquals(rta.get(0).getShUpdatedSalary().getValue(), salaryHistory.getShUpdatedSalary().getValue());
        assertEquals(rta.get(0).getShUpdatedSalary().getValue(), salaryHistoryDBO.getUpdatedSalary());
        assertEquals(rta.get(0).getShModificationDate().getValue(), salaryHistory.getShModificationDate().getValue());
        assertEquals(rta.get(0).getShModificationDate().getValue(), salaryHistoryDBO.getModificationDate());
    }
}