package co.com.ias.appback.infrastructure.entrypoint.dto.employee_page_response;

import co.com.ias.appback.domain.model.employee_page_response.EmployeePageResponse;
import co.com.ias.appback.infrastructure.entrypoint.dto.employee.EmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePageResponseDTO {

    private Integer totalPages;
    private Integer results;
    private Integer remainingResults;
    private List<EmployeeDTO> employees;

    public EmployeePageResponseDTO fromDomain(EmployeePageResponse employeePageResponse){
        return new EmployeePageResponseDTO(
                employeePageResponse.getPageResponsePaging().value(),
                employeePageResponse.getPageResponseResults().value(),
                employeePageResponse.getPageResponseRemainingResults().value(),
                employeePageResponse.getEmployee()
                        .stream()
                        .map(employee -> new EmployeeDTO().fromDomain(employee))
                        .toList()
        );
    }


}
