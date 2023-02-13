package co.com.ias.appback.domain.usecase.salary;

import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.gateway.employee.IFindEmployeeByIdGateway;
import co.com.ias.appback.domain.model.gateway.salary.IFindAllSalaryHistoryGateway;
import co.com.ias.appback.domain.model.salary_history.SalaryHistory;
import jakarta.persistence.EntityExistsException;

import java.util.List;
import java.util.Optional;

public class FindSalaryEmployeeUseCase {

    private final IFindAllSalaryHistoryGateway iFindAllSalaryHistoryGateway;
    private final IFindEmployeeByIdGateway iFindEmployeeByIdGateway;

    public FindSalaryEmployeeUseCase(IFindAllSalaryHistoryGateway iFindAllSalaryHistoryGateway, IFindEmployeeByIdGateway iFindEmployeeByIdGateway) {
        this.iFindAllSalaryHistoryGateway = iFindAllSalaryHistoryGateway;
        this.iFindEmployeeByIdGateway = iFindEmployeeByIdGateway;
    }

    public List<SalaryHistory> findSalaryByEmployeeId(String id){
        Optional<Employee> employee = Optional.ofNullable(iFindEmployeeByIdGateway.findById(id));
        if(employee.isPresent()){
            return iFindAllSalaryHistoryGateway.findAllSalaryHistoryByEmployeeId(id);
        }else {
            throw new EntityExistsException(
                    "ID doesn't exist, please try to provide an existing employee ID or check the provided ID"
            );
        }

    }
}
