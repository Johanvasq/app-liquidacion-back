package co.com.ias.appback.infrastructure.entrypoint.dto.liquidation_response;

import co.com.ias.appback.domain.model.constants.GlobalConstants;
import co.com.ias.appback.domain.model.liquidation_payment_response.LiquidationPaymentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LiquidationPaymentResponseDTO {
    private String  id;
    private String employeeName;
    private String employeeId;
    private String employeePosition;
    private Boolean employeeState;
    private Double lastSalary;
    private Double transportationAllowance;
    private String employeeContractStart;
    private String employeeContractEnd;
    private  String withdrawalReason;
    private Integer totalDaysWorked;
    private Integer daysWorkedCurrentYear;
    private Double vacationDaysToBeTaken;
    private Integer daysWorkedLastSixMonths;
    private Double baseSettlementSalary;
    private Double severancePay;
    private Double vacationPay;
    private Double interestSeverancePay;
    private Double serviceBonus;
    private Double payrollPayable;
    private Double bonusUnjustifiedDismissal;
    private Double totalSettlement;

    public LiquidationPaymentResponseDTO fromDomain(LiquidationPaymentResponse liquidation){
        return new LiquidationPaymentResponseDTO(
                liquidation.getId().getValue(),
                 liquidation.getEmployee().getEmployeeName().getValue(),
                liquidation.getEmployee().getEmployeeId().getValue(),
                liquidation.getEmployee().getEmployeePosition().getValue(),
                liquidation.getEmployee().getEmployeeState().getValue(),
                liquidation.getLprLastSalary().getValue(),
                liquidation.getTransportationAllowance().getValue(),
                liquidation.getEmployeeContractStart().getValue().format(GlobalConstants.DATE_FORMAT),
                liquidation.getEmployeeContractEnd().getValue().format(GlobalConstants.DATE_FORMAT),
                liquidation.getWithdrawalReason().getValue(),
                liquidation.getTotalDaysWorked().getValue(),
                liquidation.getDaysWorkedCurrentYear().getValue(),
                liquidation.getVacationDaysToBeTaken().getValue(),
                liquidation.getDaysWorkedLastSixMonths().getValue(),
                liquidation.getBaseSettlementSalary().getValue(),
                liquidation.getSeverancePay().getValue(),
                liquidation.getVacationPay().getValue(),
                liquidation.getInterestSeverancePay().getValue(),
                liquidation.getServiceBonus().getValue(),
                liquidation.getPayrollPayable().getValue(),
                liquidation.getBonusUnjustifiedDismissal().getValue(),
                liquidation.getTotalSettlement().getValue()
        );
    }

}
