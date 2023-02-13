package co.com.ias.appback.infrastructure.adapters.jpa.entity;

import co.com.ias.appback.domain.model.employee.attributes.EmployeeContractStart;
import co.com.ias.appback.domain.model.employee.attributes.EmployeeCurrentSalary;
import co.com.ias.appback.domain.model.liquidation_payment_request.attributes.EmployeeContractEnd;
import co.com.ias.appback.domain.model.liquidation_payment_request.attributes.LRWithdrawalReason;
import co.com.ias.appback.domain.model.liquidation_payment_response.LiquidationPaymentResponse;
import co.com.ias.appback.domain.model.liquidation_payment_response.attributes.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name ="liquidation")
public class LiquidationPaymentResponseDBO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String  id;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeDBO employeeDBO;
    private Double lprLastSalary;
    private Double transportationAllowance;
    private LocalDate employeeContractStart;
    private LocalDate employeeContractEnd;
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

    public LiquidationPaymentResponseDBO fromDomain(LiquidationPaymentResponse liquidation){
        return new LiquidationPaymentResponseDBO(
                liquidation.getId().getValue(),
                new EmployeeDBO().fromDomain(liquidation.getEmployee()),
                liquidation.getLprLastSalary().getValue(),
                liquidation.getTransportationAllowance().getValue(),
                liquidation.getEmployeeContractStart().getValue(),
                liquidation.getEmployeeContractEnd().getValue(),
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

    public LiquidationPaymentResponse toDomain(){
        return new LiquidationPaymentResponse(
                new LiquidationPaymentResponseId(this.id),
                this.employeeDBO.toDomain(),
                new EmployeeCurrentSalary(this.lprLastSalary),
                new TransportationAllowance(this.transportationAllowance),
                new EmployeeContractStart(employeeContractStart),
                new EmployeeContractEnd(this.employeeContractEnd),
                new LRWithdrawalReason(this.withdrawalReason),
                new TotalDaysWorked(this.totalDaysWorked),
                new DaysWorkedCurrentYear(this.daysWorkedCurrentYear),
                new VacationDaysToBeTaken(this.vacationDaysToBeTaken),
                new DaysWorkedLastSixMonths(this.daysWorkedLastSixMonths),
                new BaseSettlementSalary(this.baseSettlementSalary),
                new SeverancePay(this.severancePay),
                new VacationPay(this.vacationPay),
                new InterestSeverancePay(this.interestSeverancePay),
                new ServiceBonus(this.serviceBonus),
                new PayrollPayable(this.payrollPayable),
                new BonusUnjustifiedDismissal(this.bonusUnjustifiedDismissal),
                new TotalSettlement(this.totalSettlement)
        );
    }
}
