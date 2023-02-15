package co.com.ias.appback.domain.usecase.employee;

import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.employee.attributes.*;
import co.com.ias.appback.domain.model.employee_page_response.EmployeePageResponse;
import co.com.ias.appback.domain.model.gateway.employee.IPaginationEmployeeGateway;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class PaginationEmployeeUseCaseTest {

    @InjectMocks
    private PaginationEmployeeUseCase paginationEmployeeUseCase;
    @Mock
    private IPaginationEmployeeGateway iPaginationEmployeeGateway;

    @Test
    void findEmployeesBySalaryRange() {
        Employee employee = new Employee(
                new EmployeeId("12345678"),
                new EmployeeName("Johan"),
                new EmployeeContractStart(LocalDate.of(2015,2,2)),
                new EmployeePosition("software developer"),
                new EmployeeState(true),
                new EmployeeCurrentSalary(1500000.0),
                new EmployeeLastSalaryUpdated(LocalDate.of(2015,2,2))
        );
        Pageable pageable = PageRequest.of(1, 6);


        when(iPaginationEmployeeGateway.findEmployeesBySalaryRange(any(Double.class), any(Double.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(employee)));

        EmployeePageResponse result = paginationEmployeeUseCase.findEmployeesBySalaryRange(1000000.0, 2000000.0, 10, 1);

        assertEquals(1, result.getPageResponseResults().value());
        assertEquals(1, result.getPageResponseRemainingResults().value());
        assertEquals(1, result.getPageResponsePaging().value());
        assertEquals(1, result.getEmployee().size());
        assertEquals("12345678", result.getEmployee().get(0).getEmployeeId().getValue());
        assertEquals("Johan", result.getEmployee().get(0).getEmployeeName().getValue());
        assertEquals(1500000.0, result.getEmployee().get(0).getEmployeeCurrentSalary().getValue());

    }

    @Test
    void testFindEmployeesBySalaryRange() {
        Employee employee = new Employee(
                new EmployeeId("12345678"),
                new EmployeeName("Johan"),
                new EmployeeContractStart(LocalDate.of(2015,2,2)),
                new EmployeePosition("software developer"),
                new EmployeeState(true),
                new EmployeeCurrentSalary(1500000.0),
                new EmployeeLastSalaryUpdated(LocalDate.of(2015,2,2))
        );
        Pageable pageable = PageRequest.of(1, 6);


        when(iPaginationEmployeeGateway.findEmployeesBySalaryRange(any(Double.class), any(Double.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(employee)));

        IllegalArgumentException result = assertThrows(IllegalArgumentException.class, () ->{
            paginationEmployeeUseCase.findEmployeesBySalaryRange(3000000.0, 2000000.0, 10, 1);
        });
        assertEquals("please provide correct salary range", result.getMessage());
    }
}