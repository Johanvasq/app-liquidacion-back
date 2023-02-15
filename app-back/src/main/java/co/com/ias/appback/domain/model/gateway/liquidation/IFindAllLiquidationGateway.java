package co.com.ias.appback.domain.model.gateway.liquidation;

import co.com.ias.appback.domain.model.liquidation_payment_response.LiquidationPaymentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface IFindAllLiquidationGateway {

    Page<LiquidationPaymentResponse> findLiquidationByDateRange(LocalDate minDate, LocalDate maxDate, Pageable pageable);
}
