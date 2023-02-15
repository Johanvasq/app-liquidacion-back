package co.com.ias.appback.domain.usecase.salary;

import co.com.ias.appback.domain.model.gateway.salary.ISaveSalaryHistoryGateway;
import co.com.ias.appback.domain.model.salary_history.SalaryHistory;

public class SaveSalaryHistoryUseCase {

    private final ISaveSalaryHistoryGateway repository;

    public SaveSalaryHistoryUseCase(ISaveSalaryHistoryGateway repository) {
        this.repository = repository;
    }

    public SalaryHistory saveSalary(SalaryHistory salaryHistory){
        return repository.saveSalaryHistory(salaryHistory);
    }
}
