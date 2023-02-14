package co.com.ias.appback.domain.usecase.liquidation;

import co.com.ias.appback.domain.model.gateway.liquidation.IFindLiquidationByIdGateway;
import co.com.ias.appback.domain.model.liquidation_payment_response.LiquidationPaymentResponse;

import java.util.Optional;

public class FindLiquidationByIdUseCase {

    private final IFindLiquidationByIdGateway iFindLiquidationByIdGateway;

    public FindLiquidationByIdUseCase(IFindLiquidationByIdGateway iFindLiquidationByIdGateway) {
        this.iFindLiquidationByIdGateway = iFindLiquidationByIdGateway;
    }

    public LiquidationPaymentResponse findLiquidationById(String id) {
        Optional<LiquidationPaymentResponse> rta = Optional.ofNullable(iFindLiquidationByIdGateway.findLiquidationByEmployeeId(id));
        if (rta.isPresent()) {
            return rta.get();
        }else {
            throw new IllegalStateException("The employee has not been liquidated");
        }
    }
}
