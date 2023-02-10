package co.com.ias.appback.domain.model.gateway.salary;

import co.com.ias.appback.domain.model.salaryhistory.SalaryHistory;

import java.util.List;

public interface ISaveSalaryHistoryGateway {

    SalaryHistory saveSalaryHistory(SalaryHistory salaryHistory);
}
