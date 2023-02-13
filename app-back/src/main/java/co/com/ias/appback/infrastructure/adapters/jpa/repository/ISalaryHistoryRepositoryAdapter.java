package co.com.ias.appback.infrastructure.adapters.jpa.repository;

import co.com.ias.appback.infrastructure.adapters.jpa.entity.SalaryHistoryDBO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ISalaryHistoryRepositoryAdapter extends JpaRepository<SalaryHistoryDBO, Integer> {

    List<SalaryHistoryDBO> findAllByEmployeeDBO_Id(String id);
}
