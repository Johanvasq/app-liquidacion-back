package co.com.ias.appback.infrastructure.adapters;

import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.gateway.FindEmployeeByIdGateway;
import co.com.ias.appback.domain.model.gateway.SaveEmployeeGateway;
import co.com.ias.appback.infrastructure.adapters.jpa.IEmployeeRepositoryAdapter;
import co.com.ias.appback.infrastructure.adapters.jpa.entity.EmployeeDBO;

import java.util.Optional;

public class EmployeeRepositoryAdapter implements SaveEmployeeGateway, FindEmployeeByIdGateway {

    private final IEmployeeRepositoryAdapter repository;

    public EmployeeRepositoryAdapter(IEmployeeRepositoryAdapter repository) {
        this.repository = repository;
    }

    /**
     * find an employee by id in the repository
     * @param id the id of the employee
     * @return an instance of employee
     * @throws NullPointerException if is null
     */
    @Override
    public Employee findById(String id) throws NullPointerException {
        Optional<EmployeeDBO> obj = repository.findById(id);
        if (obj.isPresent()) {
            return obj.get().toDomain();
        }else {
            throw new NullPointerException("No such employee");
        }
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
}
