package co.com.ias.appback.domain.model.liquidation_payment_request;

import co.com.ias.appback.domain.model.employee.attributes.EmployeeId;
import co.com.ias.appback.domain.model.liquidation_payment_request.attributes.EmployeeContractEnd;
import co.com.ias.appback.domain.model.liquidation_payment_request.attributes.LRWithdrawalReason;

public class LiquidationRequest {
    private final EmployeeId employeeId;

    private final LRWithdrawalReason lrWithdrawalReason;
    private final EmployeeContractEnd employeeContractEnd;

    public LiquidationRequest(EmployeeId employeeId, LRWithdrawalReason lrWithdrawalReason, EmployeeContractEnd employeeContractEnd) {
        this.employeeId = employeeId;
        this.lrWithdrawalReason = lrWithdrawalReason;
        this.employeeContractEnd = employeeContractEnd;
    }

    public EmployeeId getEmployeeId() {
        return employeeId;
    }

    public LRWithdrawalReason getLrWithdrawalReason() {
        return lrWithdrawalReason;
    }

    public EmployeeContractEnd getEmployeeContractEnd() {
        return employeeContractEnd;
    }
}
