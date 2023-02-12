package co.com.ias.appback.domain.usecase.employee;


import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.gateway.employee.IPaginationEmployeeGateway;
import co.com.ias.appback.domain.model.pageresponse.PageResponse;
import co.com.ias.appback.domain.model.pageresponse.attributes.PageResponsePaging;
import co.com.ias.appback.domain.model.pageresponse.attributes.PageResponseRemainingResults;
import co.com.ias.appback.domain.model.pageresponse.attributes.PageResponseResults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PaginationEmployeeUseCase {

    private final IPaginationEmployeeGateway iPaginationEmployeeGateway;

    public PaginationEmployeeUseCase(IPaginationEmployeeGateway iPaginationEmployeeGateway) {
        this.iPaginationEmployeeGateway = iPaginationEmployeeGateway;
    }

    public PageResponse findEmployeesBySalaryRange(Double minSalary,
                                                   Double maxSalary,
                                                   Integer recordsPerPage,
                                                   Integer page){

        Double selectedMinSalary = minSalary != null ? minSalary : 1160000;
        Double selectedMaxSalary = maxSalary != null ? maxSalary : 7000000;
        if(selectedMinSalary > selectedMaxSalary){
            throw new IllegalArgumentException("please provide correct salary range");
        }
        int selectedRecordsPerPage = recordsPerPage != null ? recordsPerPage : 5;
        Pageable pageable = PageRequest.of(page, selectedRecordsPerPage);

        Page<Employee> rta = iPaginationEmployeeGateway
                .findEmployeesBySalaryRange(selectedMinSalary, selectedMaxSalary, pageable);

        return new PageResponse(
                new PageResponsePaging(rta.getTotalPages()),
                new PageResponseResults((int) rta.getTotalElements()),
                new PageResponseRemainingResults((int) rta.getTotalElements() - (rta.getNumber() * rta.getSize())),
                rta.getContent()
        );
    }


}
