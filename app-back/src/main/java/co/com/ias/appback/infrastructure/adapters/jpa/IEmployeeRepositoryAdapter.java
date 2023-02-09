package co.com.ias.appback.infrastructure.adapters.jpa;

import co.com.ias.appback.infrastructure.adapters.jpa.entity.EmployeeDBO;
/*import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;*/
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployeeRepositoryAdapter extends JpaRepository<EmployeeDBO, String> {

    /*Page<EmployeeDBO> findAllBy(Pageable pageable);*/
}
