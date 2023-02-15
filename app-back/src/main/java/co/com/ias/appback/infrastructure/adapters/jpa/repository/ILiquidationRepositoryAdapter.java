package co.com.ias.appback.infrastructure.adapters.jpa.repository;

import co.com.ias.appback.infrastructure.adapters.jpa.entity.LiquidationPaymentResponseDBO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ILiquidationRepositoryAdapter extends JpaRepository<LiquidationPaymentResponseDBO, String> {

    LiquidationPaymentResponseDBO findByEmployeeDBO_Id(String id);

    @Query("SELECT e FROM LiquidationPaymentResponseDBO e WHERE e.employeeContractEnd BETWEEN :minDate AND :maxDate")
    Page<LiquidationPaymentResponseDBO> findByEmployeeContractEndRange(@Param("minDate") LocalDate minDate,
                                        @Param("maxDate") LocalDate maxDate,
                                        Pageable pageable);
}
