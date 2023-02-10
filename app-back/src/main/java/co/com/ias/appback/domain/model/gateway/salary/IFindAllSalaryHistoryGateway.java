package co.com.ias.appback.domain.model.gateway.salary;

import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.salaryhistory.SalaryHistory;

import java.util.List;

public interface IFindAllSalaryHistoryGateway {

    List<SalaryHistory> findAllSalaryHistorybyEmployee(Employee employee);
}
