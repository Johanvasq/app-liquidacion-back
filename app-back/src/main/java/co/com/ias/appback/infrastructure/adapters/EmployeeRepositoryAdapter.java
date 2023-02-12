package co.com.ias.appback.infrastructure.adapters;

import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.gateway.employee.IFindEmployeeByIdGateway;
import co.com.ias.appback.domain.model.gateway.employee.IPaginationEmployeeGateway;
import co.com.ias.appback.domain.model.gateway.employee.ISaveEmployeeGateway;
import co.com.ias.appback.domain.model.gateway.employee.IUpdateEmployeeGateway;
import co.com.ias.appback.infrastructure.adapters.jpa.IEmployeeRepositoryAdapter;
import co.com.ias.appback.infrastructure.adapters.jpa.entity.EmployeeDBO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class EmployeeRepositoryAdapter
        implements
        ISaveEmployeeGateway,
        IFindEmployeeByIdGateway,
        IUpdateEmployeeGateway,
        IPaginationEmployeeGateway {

    private final IEmployeeRepositoryAdapter repository;


    /**
     * find an employee by id in the repository
     * @param id the id of the employee
     * @return an instance of employee
     * @throws NullPointerException if is null
     */
    @Override
    public Employee findById(String id) throws NullPointerException {
        Optional<EmployeeDBO> obj = repository.findById(id);
        return obj.map(EmployeeDBO::toDomain).orElse(null);
    }

    /**
     * Save the employee into the database
     * @param employee all the data of the employee
     * @return the employee saved into the database
     * @throws IllegalArgumentException if the values provided are not valid
     */
    @Override
    public Employee saveEmployee(Employee employee) throws IllegalArgumentException {
            return repository.save(new EmployeeDBO().fromDomain(employee)).toDomain();

    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return repository.save(new EmployeeDBO().fromDomain(employee)).toDomain();
    }

    @Override
    public Page<Employee> findEmployeesBySalaryRange(Double minSalary, Double maxSalary, Pageable pageable) {
        return repository.findBySalaryRange(minSalary, maxSalary, pageable)
                .map(EmployeeDBO::toDomain);
    }
}
