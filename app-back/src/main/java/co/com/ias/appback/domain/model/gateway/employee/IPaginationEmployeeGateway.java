package co.com.ias.appback.domain.model.gateway.employee;

import co.com.ias.appback.domain.model.employee.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IPaginationEmployeeGateway {
    Page<Employee> findEmployeesBySalaryRange(Double minSalary, Double maxSalary, Pageable pageable);

}
