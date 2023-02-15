package co.com.ias.appback.domain.usecase.liquidation;

import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.gateway.employee.IFindEmployeeByIdGateway;
import co.com.ias.appback.domain.model.gateway.liquidation.IFindLiquidationByIdGateway;
import co.com.ias.appback.domain.model.liquidation_payment_response.LiquidationPaymentResponse;
import jakarta.persistence.EntityExistsException;

import java.util.Optional;

public class FindLiquidationByIdUseCase {

    private final IFindLiquidationByIdGateway iFindLiquidationByIdGateway;
    private final IFindEmployeeByIdGateway iFindEmployeeByIdGateway;

    public FindLiquidationByIdUseCase(IFindLiquidationByIdGateway iFindLiquidationByIdGateway, IFindEmployeeByIdGateway iFindEmployeeByIdGateway) {
        this.iFindLiquidationByIdGateway = iFindLiquidationByIdGateway;
        this.iFindEmployeeByIdGateway = iFindEmployeeByIdGateway;
    }

    public LiquidationPaymentResponse findLiquidationById(String id) {
        Optional<Employee> employee = Optional.ofNullable(iFindEmployeeByIdGateway.findById(id));
        if (employee.isEmpty()) {
            throw new EntityExistsException(
                    "ID doesn't exist, please try to provide an existing employee ID or check the provided ID");
        }
        Optional<LiquidationPaymentResponse> rta = Optional.ofNullable(iFindLiquidationByIdGateway.findLiquidationByEmployeeId(id));
        if (rta.isPresent()) {
            return rta.get();
        }else {
            throw new IllegalStateException("The employee has not been liquidated");
        }
    }
}
