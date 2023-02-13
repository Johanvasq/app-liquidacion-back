package co.com.ias.appback.infrastructure.entrypoint.dto.page_response;

import co.com.ias.appback.domain.model.page_response.PageResponse;
import co.com.ias.appback.domain.model.page_response.attributes.PageResponsePaging;
import co.com.ias.appback.domain.model.page_response.attributes.PageResponseRemainingResults;
import co.com.ias.appback.domain.model.page_response.attributes.PageResponseResults;
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
public class PageResponseDTO {

    private Integer totalPages;
    private Integer results;

    private Integer remainingResults;
    private List<EmployeeDTO> employees;


    public PageResponse toDomain(){
        return new PageResponse(
                new PageResponsePaging(this.totalPages),
                new PageResponseResults(this.results),
                new PageResponseRemainingResults(this.remainingResults),
                this.employees
                        .stream()
                        .map(EmployeeDTO::toDomain)
                        .toList()
        );
    }

    public PageResponseDTO fromDomain(PageResponse pageResponse){
        return new PageResponseDTO(
                pageResponse.getPageResponsePaging().value(),
                pageResponse.getPageResponseResults().value(),
                pageResponse.getPageResponseRemainingResults().value(),
                pageResponse.getEmployee()
                        .stream()
                        .map(employee -> new EmployeeDTO().fromDomain(employee))
                        .toList()
        );
    }


}
