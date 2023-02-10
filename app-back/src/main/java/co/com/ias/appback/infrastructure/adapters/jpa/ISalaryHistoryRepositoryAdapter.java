package co.com.ias.appback.infrastructure.adapters.jpa;

import co.com.ias.appback.infrastructure.adapters.jpa.entity.SalaryHistoryDBO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISalaryHistoryRepositoryAdapter extends JpaRepository<SalaryHistoryDBO, Integer> {
}
