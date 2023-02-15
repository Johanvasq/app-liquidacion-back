package co.com.ias.appback.infrastructure.entrypoint;

import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.employee.attributes.*;
import co.com.ias.appback.domain.model.employee_page_response.EmployeePageResponse;
import co.com.ias.appback.domain.model.employee_page_response.attributes.PageResponsePaging;
import co.com.ias.appback.domain.model.employee_page_response.attributes.PageResponseRemainingResults;
import co.com.ias.appback.domain.model.employee_page_response.attributes.PageResponseResults;
import co.com.ias.appback.domain.usecase.employee.FindEmployeeByIdUseCase;
import co.com.ias.appback.domain.usecase.employee.PaginationEmployeeUseCase;
import co.com.ias.appback.domain.usecase.employee.SaveEmployeeUseCase;
import co.com.ias.appback.domain.usecase.employee.UpdateEmployeeUseCase;
import co.com.ias.appback.infrastructure.entrypoint.dto.employee.EmployeeDTO;
import co.com.ias.appback.infrastructure.entrypoint.dto.employee.PaginationEmployeeDTO;
import co.com.ias.appback.infrastructure.entrypoint.dto.employee.UpdateEmployeeDTO;
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
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeEntryPoint.class)
class EmployeeEntryPointTest {

    @MockBean
    private SaveEmployeeUseCase saveEmployeeUseCase;

    @MockBean
    private FindEmployeeByIdUseCase findEmployeeByIdUseCase;
    @MockBean
    private UpdateEmployeeUseCase updateEmployeeUseCase;
    @MockBean
    private PaginationEmployeeUseCase paginationEmployeeUseCase;

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
                new EmployeeCurrentSalary(1500000.0),
                new EmployeeLastSalaryUpdated(LocalDate.of(2015,2,2))

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
                true,
                LocalDate.of(2015,2,2).format(DateTimeFormatter.ofPattern("yyyy/dd/MM"))
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

    @Test
    @DisplayName("find incorrect employee")
    void findEmployee() throws Exception {

        when(findEmployeeByIdUseCase.findEmployeeById(any(String.class)))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/{id}","123456789"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("find correct employee")
    void testFindEmployee() throws Exception {
        Employee employee = new Employee(
                new EmployeeId("12345678"),
                new EmployeeName("Johan"),
                new EmployeeContractStart(LocalDate.of(2015,2,2)),
                new EmployeePosition("software developer"),
                new EmployeeState(true),
                new EmployeeCurrentSalary(1500000.0),
                new EmployeeLastSalaryUpdated(LocalDate.of(2015,2,2))

        );
        EmployeeDTO employeeDTO = new EmployeeDTO().fromDomain(employee);

        when(findEmployeeByIdUseCase.findEmployeeById(any(String.class)))
                .thenReturn(employee);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/{id}","123456789"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(content()
                        .string(containsString(employeeDTO.getName())))
                .andExpect(content().string(containsString(employeeDTO.getId())))
                .andExpect(content().string(containsString(employeeDTO.getPosition())));
    }

    @Test
    @DisplayName("update correct employee")
    void updateEmployee() throws Exception {
        Employee employee = new Employee(
                new EmployeeId("12345678"),
                new EmployeeName("Johan"),
                new EmployeeContractStart(LocalDate.of(2015,2,2)),
                new EmployeePosition("software developer"),
                new EmployeeState(true),
                new EmployeeCurrentSalary(1500000.0),
                new EmployeeLastSalaryUpdated(LocalDate.of(2015,2,2))

        );
        EmployeeDTO employeeDTO = new EmployeeDTO().fromDomain(employee);

        UpdateEmployeeDTO updateEmployeeDTO = new UpdateEmployeeDTO(
                "12345678",
                "software developer",
                1600000.0,
                "2020/26/05"
        );

        ObjectMapper mapper = new ObjectMapper();

        when(updateEmployeeUseCase.updateEmployee(any(String.class), any(String.class),any(Double.class), any(LocalDate.class)))
                .thenReturn(employee);
        mockMvc.perform(MockMvcRequestBuilders.put("/employee")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(updateEmployeeDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content()
                        .string(containsString(employeeDTO.getName())))
                .andExpect(content().string(containsString(employeeDTO.getId())))
                .andExpect(content().string(containsString(employeeDTO.getPosition())));
    }

    @Test
    @DisplayName("update incorrect employee")
    void testUpdateEmployee() throws Exception {
        UpdateEmployeeDTO updateEmployeeDTO = new UpdateEmployeeDTO(
                "12345678",
                "software developer",
                1600000.0,
                "2020/26/05"
        );

        ObjectMapper mapper = new ObjectMapper();

        when(updateEmployeeUseCase.updateEmployee(any(String.class), any(String.class),any(Double.class), any(LocalDate.class)))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateEmployeeDTO)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("pagination correct employee")
    void paginationEmployees() throws Exception {
        Employee employee = new Employee(
                new EmployeeId("12345678"),
                new EmployeeName("Johan"),
                new EmployeeContractStart(LocalDate.of(2015,2,2)),
                new EmployeePosition("software developer"),
                new EmployeeState(true),
                new EmployeeCurrentSalary(1500000.0),
                new EmployeeLastSalaryUpdated(LocalDate.of(2015,2,2))
        );
        EmployeeDTO employeeDTO = new EmployeeDTO().fromDomain(employee);
        ObjectMapper mapper = new ObjectMapper();


        EmployeePageResponse employeePageResponse = new EmployeePageResponse(
                new PageResponsePaging(1),
                new PageResponseResults(1),
                new PageResponseRemainingResults(1),
                List.of(employee)
        );

        PaginationEmployeeDTO paginationEmployeeDTO = new PaginationEmployeeDTO(
                1600000d,
                5000000d,
                6,
                1
        );

        when(paginationEmployeeUseCase.findEmployeesBySalaryRange(any(Double.class), any(Double.class), any(Integer.class), any(Integer.class)))
                .thenReturn(employeePageResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(paginationEmployeeDTO)))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(content()
                        .string(containsString(employeeDTO.getName())))
                .andExpect(content().string(containsString(employeeDTO.getId())))
                .andExpect(content().string(containsString(employeeDTO.getPosition())));
    }

    @Test
    @DisplayName("pagination incorrect employee")
    void testPaginationEmployees() throws Exception {

        Employee employee = new Employee(
                new EmployeeId("12345678"),
                new EmployeeName("Johan"),
                new EmployeeContractStart(LocalDate.of(2015,2,2)),
                new EmployeePosition("software developer"),
                new EmployeeState(true),
                new EmployeeCurrentSalary(1500000.0),
                new EmployeeLastSalaryUpdated(LocalDate.of(2015,2,2))
        );

        EmployeePageResponse employeePageResponse = new EmployeePageResponse(
                new PageResponsePaging(1),
                new PageResponseResults(1),
                new PageResponseRemainingResults(1),
                List.of(employee)
        );

        PaginationEmployeeDTO paginationEmployeeDTO = new PaginationEmployeeDTO(
                1600000d,
                5000000d,
                1,
                1
        );

        ObjectMapper mapper = new ObjectMapper();

        when(paginationEmployeeUseCase.findEmployeesBySalaryRange(any(Double.class), any(Double.class), any(Integer.class), any(Integer.class)))
                .thenReturn(employeePageResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(paginationEmployeeDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}