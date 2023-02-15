package co.com.ias.appback.domain.model.gateway.salary;

import co.com.ias.appback.domain.model.salary_history.SalaryHistory;

import java.util.List;

public interface IFindAllSalaryHistoryGateway {

    List<SalaryHistory> findAllSalaryHistoryByEmployeeId(String id);
}
