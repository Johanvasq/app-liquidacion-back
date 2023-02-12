package co.com.ias.appback.domain.usecase.employee;

import co.com.ias.appback.domain.model.constants.GlobalConstants;
import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.employee.attributes.EmployeeCurrentSalary;
import co.com.ias.appback.domain.model.employee.attributes.EmployeeLastSalaryUpdated;
import co.com.ias.appback.domain.model.employee.attributes.EmployeePosition;
import co.com.ias.appback.domain.model.employee.attributes.EmployeeState;
import co.com.ias.appback.domain.model.gateway.employee.IFindEmployeeByIdGateway;
import co.com.ias.appback.domain.model.gateway.salary.ISaveSalaryHistoryGateway;
import co.com.ias.appback.domain.model.gateway.employee.IUpdateEmployeeGateway;
import co.com.ias.appback.domain.model.salaryhistory.SalaryHistory;
import co.com.ias.appback.domain.model.salaryhistory.attributes.SalaryHistoryModificationDate;
import co.com.ias.appback.domain.model.salaryhistory.attributes.SalaryHistoryUpdatedSalary;
import jakarta.persistence.EntityExistsException;

import java.time.LocalDate;
import java.util.Optional;

public class UpdateEmployeeUseCase {

    private final IUpdateEmployeeGateway iupdateEmployeeGateway;
    private final IFindEmployeeByIdGateway iFindEmployeeByIdGateway;
    private final ISaveSalaryHistoryGateway iSaveSalaryHistoryGateway;

    public UpdateEmployeeUseCase(IUpdateEmployeeGateway iupdateEmployeeGateway, IFindEmployeeByIdGateway iFindEmployeeByIdGateway, ISaveSalaryHistoryGateway iSaveSalaryHistoryGateway) {
        this.iupdateEmployeeGateway = iupdateEmployeeGateway;
        this.iFindEmployeeByIdGateway = iFindEmployeeByIdGateway;
        this.iSaveSalaryHistoryGateway = iSaveSalaryHistoryGateway;
    }


    public Employee updateEmployee(String id,
                                   String position,
                                   Double updateSalary,
                                   LocalDate modificationDate) throws IllegalArgumentException {
        Optional<Employee> employee = Optional.ofNullable(iFindEmployeeByIdGateway.findById(id));
        Double newSalary;
        LocalDate newDate;
        if (employee.isPresent()) {
            if (Boolean.FALSE.equals(employee.get().getEmployeeState().getValue())){
                throw new IllegalArgumentException("Cannot modify inactive employees");
            }
            Double salary = employee.get().getEmployeeCurrentSalary().getValue();
            LocalDate date = employee.get().getEmployeeContractStart().getValue();
            if (salary < updateSalary) {
                newSalary = updateSalary;
            } else {
                throw new IllegalArgumentException(
                        "the salary provided must be higher than the previous one: " + salary);
            }
            if (date.isBefore(modificationDate)) {
                newDate = modificationDate;
            } else {
                throw new IllegalArgumentException(
                        "the date provided must be higher than the previous one: "
                                + date.format(GlobalConstants.DATE_FORMAT));
            }
            iSaveSalaryHistoryGateway.saveSalaryHistory(new SalaryHistory(
                    employee.get(),
                    new SalaryHistoryUpdatedSalary(newSalary),
                    new SalaryHistoryModificationDate(newDate)
            ));
            return iupdateEmployeeGateway.updateEmployee(new Employee(
                    employee.get().getEmployeeId(),
                    employee.get().getEmployeeName(),
                    employee.get().getEmployeeContractStart(),
                    new EmployeePosition(position),
                    new EmployeeState(true),
                    new EmployeeCurrentSalary(newSalary),
                    new EmployeeLastSalaryUpdated(newDate)
            ));
        }
        throw new EntityExistsException(
                "ID doesn't exist, please try to provide an existing employee ID or check the provided ID"
        );
    }
}
