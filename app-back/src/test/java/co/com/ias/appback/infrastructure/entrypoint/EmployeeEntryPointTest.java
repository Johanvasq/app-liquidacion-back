package co.com.ias.appback.infrastructure.entrypoint;

import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.employee.attributes.*;
import co.com.ias.appback.domain.usecase.employee.SaveEmployeeUseCase;
import co.com.ias.appback.infrastructure.entrypoint.dto.employee.EmployeeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeEntryPoint.class)
class EmployeeEntryPointTest {

    @MockBean
    private SaveEmployeeUseCase saveEmployeeUseCase;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Save employee")
    void saveEmployee() throws Exception {
        Employee employee = new Employee(
                new EmployeeId("12345678"),
                new EmployeeName("Johan"),
                new EmployeeContractStart(LocalDate.of(2015,2,2)),
                new EmployeePosition("software developer"),
                new EmployeeState(true),
                new EmployeeCurrentSalary(1500000.0)
        );

        EmployeeDTO employeeDTO = new EmployeeDTO().fromDomain(employee);

        when(saveEmployeeUseCase.saveEmployee(any(Employee.class)))
                .thenReturn(employee);

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.post("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .string(containsString(employeeDTO.getName())))
                .andExpect(content().string(containsString(employeeDTO.getId())))
                .andExpect(content().string(containsString(employeeDTO.getPosition())));

    }

    @Test
    @DisplayName("Save incorrect employee")
    void testSaveEmployee() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO(
                "12345678",
                "Johan?",
                LocalDate.of(2015,2,2).format(DateTimeFormatter.ofPattern("yyyy/dd/MM")),
                "software developer",
                1500000.0,
                true
        );

        when(saveEmployeeUseCase.saveEmployee(any(Employee.class)))
                .thenThrow(IllegalArgumentException.class);

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(employeeDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(status().isBadRequest());

    }
}