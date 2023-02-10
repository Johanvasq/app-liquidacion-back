package co.com.ias.appback.infrastructure.adapters;

import co.com.ias.appback.domain.model.gateway.salary.IFindAllSalaryHistoryGateway;
import co.com.ias.appback.domain.model.gateway.salary.ISaveSalaryHistoryGateway;
import co.com.ias.appback.domain.model.salaryhistory.SalaryHistory;
import co.com.ias.appback.infrastructure.adapters.jpa.ISalaryHistoryRepositoryAdapter;
import co.com.ias.appback.infrastructure.adapters.jpa.entity.SalaryHistoryDBO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class SalaryHistoryRepositoryAdapter implements ISaveSalaryHistoryGateway, IFindAllSalaryHistoryGateway {

    private final ISalaryHistoryRepositoryAdapter repository;

    @Override
    public List<SalaryHistory> findAllSalaryHistory() {
        return repository.findAll().stream()
                .map(SalaryHistoryDBO::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public SalaryHistory saveSalaryHistory(SalaryHistory salaryHistory) throws IllegalArgumentException{
        return repository.save(new SalaryHistoryDBO().fromDomain(salaryHistory)).toDomain();
    }
}
