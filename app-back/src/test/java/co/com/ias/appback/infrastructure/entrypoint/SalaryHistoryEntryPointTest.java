package co.com.ias.appback.infrastructure.entrypoint;

import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.employee.attributes.*;
import co.com.ias.appback.domain.model.salary_history.SalaryHistory;
import co.com.ias.appback.domain.model.salary_history.attributes.SalaryHistoryModificationDate;
import co.com.ias.appback.domain.model.salary_history.attributes.SalaryHistoryUpdatedSalary;
import co.com.ias.appback.domain.usecase.salary.FindSalaryEmployeeUseCase;
import co.com.ias.appback.infrastructure.entrypoint.dto.salary.SalaryHistoryDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(SalaryHistoryEntryPoint.class)
class SalaryHistoryEntryPointTest {

    @MockBean
    private FindSalaryEmployeeUseCase findSalaryEmployeeUseCase;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("find salary of employees")
    void findSalaryHistoryByEmployeeId() throws Exception {
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
                new SalaryHistoryUpdatedSalary(2000000d),
                new SalaryHistoryModificationDate(LocalDate.of(2015,2,2))
        );

        List<SalaryHistory> salary = List.of(salaryHistory);

        List<SalaryHistoryDTO> salaryDTOs = salary.stream().map(s -> new SalaryHistoryDTO().fromDomain(s)).toList();

        when(findSalaryEmployeeUseCase.findSalaryByEmployeeId(any(String.class)))
                .thenReturn(salary);

        mockMvc.perform(MockMvcRequestBuilders.get("/salary/{id}","12345678"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(content()
                        .string(containsString(salaryDTOs.get(0).getUpdatedSalary().toString())))
                .andExpect(content().string(containsString(salaryDTOs.get(0).getModificationDate().toString())));
    }
}