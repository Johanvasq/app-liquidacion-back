package co.com.ias.appback.domain.model.gateway.salary;

import co.com.ias.appback.domain.model.salary_history.SalaryHistory;

public interface ISaveSalaryHistoryGateway {

    SalaryHistory saveSalaryHistory(SalaryHistory salaryHistory);
}
