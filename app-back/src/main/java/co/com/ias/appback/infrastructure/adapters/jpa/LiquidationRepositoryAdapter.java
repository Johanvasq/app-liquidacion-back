package co.com.ias.appback.infrastructure.adapters.jpa;

import co.com.ias.appback.domain.model.gateway.liquidation.IFindAllLiquidationGateway;
import co.com.ias.appback.domain.model.gateway.liquidation.IFindLiquidationByIdGateway;
import co.com.ias.appback.domain.model.gateway.liquidation.ISaveLiquidationGateway;
import co.com.ias.appback.domain.model.liquidation_payment_response.LiquidationPaymentResponse;
import co.com.ias.appback.infrastructure.adapters.jpa.entity.LiquidationPaymentResponseDBO;
import co.com.ias.appback.infrastructure.adapters.jpa.repository.ILiquidationRepositoryAdapter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class LiquidationRepositoryAdapter implements ISaveLiquidationGateway, IFindLiquidationByIdGateway, IFindAllLiquidationGateway {

    private final ILiquidationRepositoryAdapter repository;


    @Override
    public Page<LiquidationPaymentResponse> findLiquidationByDateRange(LocalDate minDate, LocalDate maxDate, Pageable pageable) {
        return repository.findByEmployeeContractEndRange(minDate, maxDate, pageable)
                .map(LiquidationPaymentResponseDBO::toDomain);
    }



    @Override
    public LiquidationPaymentResponse saveLiquidation(LiquidationPaymentResponse liquidationPaymentResponse) throws IllegalArgumentException {
        return repository.save(new LiquidationPaymentResponseDBO().fromDomain(liquidationPaymentResponse)).toDomain();
    }

    @Override
    public LiquidationPaymentResponse findLiquidationByEmployeeId(String id) throws NullPointerException {
        Optional<LiquidationPaymentResponseDBO> obj = Optional.ofNullable(repository.findByEmployeeDBO_Id(id));
        return obj.map(LiquidationPaymentResponseDBO::toDomain).orElse(null);
    }
}
