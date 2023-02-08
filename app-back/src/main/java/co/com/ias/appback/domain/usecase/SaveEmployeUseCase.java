package co.com.ias.appback.domain.usecase;

import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.gateway.FindEmployeeByIdGateway;
import co.com.ias.appback.domain.model.gateway.SaveEmployeeGateway;
import jakarta.persistence.EntityExistsException;

import java.util.Optional;

public class SaveEmployeUseCase {

    private final SaveEmployeeGateway saveEmployeeGateway;
    private final FindEmployeeByIdGateway findEmployeeByIdGateway;

    public SaveEmployeUseCase(
            SaveEmployeeGateway saveEmployeeGateway,
            FindEmployeeByIdGateway findEmployeeByIdGateway) {
        this.saveEmployeeGateway = saveEmployeeGateway;
        this.findEmployeeByIdGateway = findEmployeeByIdGateway;
    }

    /**
     * validate an existing employee and save it only if it doesn't exist
     * @param employee value object
     * @return
     */
    public Employee saveEmployee(Employee employee){
        Optional<Employee> opt = Optional.ofNullable(findEmployeeByIdGateway.findById(employee.getEmployeeId().getValue()));
        if (opt.isPresent()){
            throw EntityExistsException()

        }
        return saveEmployeeGateway.saveEmployee(employee);
    }
}
