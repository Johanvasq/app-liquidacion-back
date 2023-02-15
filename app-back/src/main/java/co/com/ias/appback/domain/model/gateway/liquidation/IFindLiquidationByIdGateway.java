package co.com.ias.appback.domain.model.gateway.liquidation;

import co.com.ias.appback.domain.model.liquidation_payment_response.LiquidationPaymentResponse;

public interface IFindLiquidationByIdGateway {

    LiquidationPaymentResponse findLiquidationByEmployeeId(String id);
}
