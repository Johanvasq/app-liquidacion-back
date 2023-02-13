package co.com.ias.appback.domain.model.salary_history;

import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.salary_history.attributes.SalaryHistoryModificationDate;
import co.com.ias.appback.domain.model.salary_history.attributes.SalaryHistoryUpdatedSalary;

public class SalaryHistory {
    private final Employee employee;
    private final SalaryHistoryUpdatedSalary shUpdatedSalary;
    private final SalaryHistoryModificationDate shModificationDate;

    public SalaryHistory(Employee employeeId, SalaryHistoryUpdatedSalary shUpdatedSalary, SalaryHistoryModificationDate shModificationDate) {
        this.employee = employeeId;
        this.shUpdatedSalary = shUpdatedSalary;
        this.shModificationDate = shModificationDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public SalaryHistoryUpdatedSalary getShUpdatedSalary() {
        return shUpdatedSalary;
    }

    public SalaryHistoryModificationDate getShModificationDate() {
        return shModificationDate;
    }
}
