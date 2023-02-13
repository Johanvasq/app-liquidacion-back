package co.com.ias.appback.infrastructure.adapters.jpa.repository;

import co.com.ias.appback.infrastructure.adapters.jpa.entity.EmployeeDBO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IEmployeeRepositoryAdapter extends JpaRepository<EmployeeDBO, String> {

    @Query("SELECT e FROM EmployeeDBO e WHERE e.currentSalary BETWEEN :minSalary AND :maxSalary")
    Page<EmployeeDBO> findBySalaryRange(@Param("minSalary") Double minSalary,
                                        @Param("maxSalary") Double maxSalary,
                                        Pageable pageable);
}
