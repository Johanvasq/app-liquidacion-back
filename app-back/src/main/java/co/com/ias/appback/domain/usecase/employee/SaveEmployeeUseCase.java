package co.com.ias.appback.domain.usecase.employee;

import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.gateway.employee.IFindEmployeeByIdGateway;
import co.com.ias.appback.domain.model.gateway.employee.ISaveEmployeeGateway;
import co.com.ias.appback.domain.model.salaryhistory.SalaryHistory;
import co.com.ias.appback.domain.model.salaryhistory.attributes.SalaryHistoryModificationDate;
import co.com.ias.appback.domain.model.salaryhistory.attributes.SalaryHistoryUpdatedSalary;
import co.com.ias.appback.domain.usecase.salary.SaveSalaryHistoryUseCase;
import jakarta.persistence.EntityExistsException;

import java.util.Optional;

public class SaveEmployeeUseCase {

    private final ISaveEmployeeGateway saveEmployeeGateway;
    private final IFindEmployeeByIdGateway findEmployeeByIdGateway;

    private final SaveSalaryHistoryUseCase saveSalaryHistoryUseCase;

    public SaveEmployeeUseCase(
            ISaveEmployeeGateway saveEmployee,
            IFindEmployeeByIdGateway findEmployeeById,
            SaveSalaryHistoryUseCase saveSalaryHistoryUseCase) {
        this.saveEmployeeGateway = saveEmployee;
        this.findEmployeeByIdGateway = findEmployeeById;
        this.saveSalaryHistoryUseCase = saveSalaryHistoryUseCase;
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
        Employee rta = saveEmployeeGateway.saveEmployee(employee);
        saveSalaryHistoryUseCase.saveSalary(
                new SalaryHistory(
                        rta,
                        new SalaryHistoryUpdatedSalary(employee.getEmployeeCurrentSalary().getValue()),
                        new SalaryHistoryModificationDate(employee.getEmployeeContractStart().getValue())
                )
        );
        return rta;
    }
}
