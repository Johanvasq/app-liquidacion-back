package co.com.ias.appback.domain.usecase;

import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.gateway.IFindEmployeeByIdGateway;
import co.com.ias.appback.domain.model.gateway.ISaveEmployeeGateway;
import jakarta.persistence.EntityExistsException;

import java.util.Optional;

public class SaveEmployeeUseCase {

    private final ISaveEmployeeGateway saveEmployeeGateway;
    private final IFindEmployeeByIdGateway findEmployeeByIdGateway;

    public SaveEmployeeUseCase(
            ISaveEmployeeGateway saveEmployee,
            IFindEmployeeByIdGateway findEmployeeById) {
        this.saveEmployeeGateway = saveEmployee;
        this.findEmployeeByIdGateway = findEmployeeById;
    }


    /**
     * validate an existing employee and save it only if it doesn't exist
     * @param employee value object
     * @return Employee saved
     * @throws EntityExistsException if employee already exists
     */
    public Employee saveEmployee(Employee employee) throws EntityExistsException{
        Optional<Employee> opt = Optional.ofNullable(findEmployeeByIdGateway.findById(employee.getEmployeeId().getValue()));
        if (opt.isPresent()){
            throw new EntityExistsException(
                    "The id already exists, please try to modify the existing employee or check the provided id"
            );
        }
        return saveEmployeeGateway.saveEmployee(employee);
    }
}
