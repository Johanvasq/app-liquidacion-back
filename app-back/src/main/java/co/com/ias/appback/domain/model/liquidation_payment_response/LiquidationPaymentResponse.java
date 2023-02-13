package co.com.ias.appback.domain.model.liquidation_payment_response;

import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.employee.attributes.EmployeeContractStart;
import co.com.ias.appback.domain.model.employee.attributes.EmployeeCurrentSalary;
import co.com.ias.appback.domain.model.liquidation_payment_request.attributes.EmployeeContractEnd;
import co.com.ias.appback.domain.model.liquidation_payment_request.attributes.LRWithdrawalReason;
import co.com.ias.appback.domain.model.liquidation_payment_response.attributes.*;

public class LiquidationPaymentResponse {
    private final LiquidationPaymentResponseId id;
    private final Employee employee;
    private final EmployeeCurrentSalary lprLastSalary;
    private final co.com.ias.appback.domain.model.liquidation_payment_response.attributes.TransportationAllowance TransportationAllowance;
    private final EmployeeContractStart employeeContractStart;
    private final EmployeeContractEnd employeeContractEnd;
    private  final LRWithdrawalReason withdrawalReason;
    private final TotalDaysWorked totalDaysWorked;
    private final DaysWorkedCurrentYear daysWorkedCurrentYear;
    private final VacationDaysToBeTaken vacationDaysToBeTaken;
    private final DaysWorkedLastSixMonths daysWorkedLastSixMonths;
    private final BaseSettlementSalary baseSettlementSalary;
    private final SeverancePay severancePay;
    private final VacationPay vacationPay;
    private final InterestSeverancePay interestSeverancePay;
    private final ServiceBonus serviceBonus;
    private final PayrollPayable payrollPayable;
    private final BonusUnjustifiedDismissal bonusUnjustifiedDismissal;
    private final TotalSettlement totalSettlement;

    public LiquidationPaymentResponse(LiquidationPaymentResponseId id, Employee employee, EmployeeCurrentSalary lprLastSalary, TransportationAllowance transportationAllowance, EmployeeContractStart employeeContractStart, EmployeeContractEnd employeeContractEnd, LRWithdrawalReason withdrawalReason, TotalDaysWorked totalDaysWorked, DaysWorkedCurrentYear daysWorkedCurrentYear, VacationDaysToBeTaken vacationDaysToBeTaken, DaysWorkedLastSixMonths daysWorkedLastSixMonths, BaseSettlementSalary baseSettlementSalary, SeverancePay severancePay, VacationPay vacationPay, InterestSeverancePay interestSeverancePay, ServiceBonus serviceBonus, PayrollPayable payrollPayable, BonusUnjustifiedDismissal bonusUnjustifiedDismissal, TotalSettlement totalSettlement) {
        this.id = id;
        this.employee = employee;
        this.lprLastSalary = lprLastSalary;
        this.TransportationAllowance = transportationAllowance;
        this.employeeContractStart = employeeContractStart;
        this.employeeContractEnd = employeeContractEnd;
        this.withdrawalReason = withdrawalReason;
        this.totalDaysWorked = totalDaysWorked;
        this.daysWorkedCurrentYear = daysWorkedCurrentYear;
        this.vacationDaysToBeTaken = vacationDaysToBeTaken;
        this.daysWorkedLastSixMonths = daysWorkedLastSixMonths;
        this.baseSettlementSalary = baseSettlementSalary;
        this.severancePay = severancePay;
        this.vacationPay = vacationPay;
        this.interestSeverancePay = interestSeverancePay;
        this.serviceBonus = serviceBonus;
        this.payrollPayable = payrollPayable;
        this.bonusUnjustifiedDismissal = bonusUnjustifiedDismissal;
        this.totalSettlement = totalSettlement;
    }

    public LiquidationPaymentResponseId getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public EmployeeCurrentSalary getLprLastSalary() {
        return lprLastSalary;
    }

    public TransportationAllowance getTransportationAllowance() {
        return TransportationAllowance;
    }

    public EmployeeContractStart getEmployeeContractStart() {
        return employeeContractStart;
    }

    public EmployeeContractEnd getEmployeeContractEnd() {
        return employeeContractEnd;
    }

    public LRWithdrawalReason getWithdrawalReason() {
        return withdrawalReason;
    }

    public TotalDaysWorked getTotalDaysWorked() {
        return totalDaysWorked;
    }

    public DaysWorkedCurrentYear getDaysWorkedCurrentYear() {
        return daysWorkedCurrentYear;
    }

    public VacationDaysToBeTaken getVacationDaysToBeTaken() {
        return vacationDaysToBeTaken;
    }

    public DaysWorkedLastSixMonths getDaysWorkedLastSixMonths() {
        return daysWorkedLastSixMonths;
    }

    public BaseSettlementSalary getBaseSettlementSalary() {
        return baseSettlementSalary;
    }

    public SeverancePay getSeverancePay() {
        return severancePay;
    }

    public VacationPay getVacationPay() {
        return vacationPay;
    }

    public InterestSeverancePay getInterestSeverancePay() {
        return interestSeverancePay;
    }

    public ServiceBonus getServiceBonus() {
        return serviceBonus;
    }

    public PayrollPayable getPayrollPayable() {
        return payrollPayable;
    }

    public BonusUnjustifiedDismissal getBonusUnjustifiedDismissal() {
        return bonusUnjustifiedDismissal;
    }

    public TotalSettlement getTotalSettlement() {
        return totalSettlement;
    }
}
