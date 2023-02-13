package co.com.ias.appback.domain.usecase.liquidation;

import co.com.ias.appback.domain.model.constants.GlobalConstants;
import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.gateway.employee.IFindEmployeeByIdGateway;
import co.com.ias.appback.domain.model.gateway.liquidation.ISaveLiquidationGateway;
import co.com.ias.appback.domain.model.gateway.salary.IFindAllSalaryHistoryGateway;
import co.com.ias.appback.domain.model.liquidation_payment_request.LiquidationRequest;
import co.com.ias.appback.domain.model.liquidation_payment_response.LiquidationPaymentResponse;
import co.com.ias.appback.domain.model.liquidation_payment_response.attributes.*;
import co.com.ias.appback.domain.model.salary_history.SalaryHistory;
import jakarta.persistence.EntityExistsException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.time.Period.*;

public class SaveLiquidationUseCase {

    private final ISaveLiquidationGateway iSaveLiquidationGateway;
    private final IFindEmployeeByIdGateway iFindEmployeeByIdGateway;
    private final IFindAllSalaryHistoryGateway iFindAllSalaryHistoryGateway;

    public SaveLiquidationUseCase(ISaveLiquidationGateway iSaveLiquidationGateway, IFindEmployeeByIdGateway iFindEmployeeByIdGateway, IFindAllSalaryHistoryGateway iFindAllSalaryHistoryGateway) {
        this.iSaveLiquidationGateway = iSaveLiquidationGateway;
        this.iFindEmployeeByIdGateway = iFindEmployeeByIdGateway;
        this.iFindAllSalaryHistoryGateway = iFindAllSalaryHistoryGateway;
    }

    public LiquidationPaymentResponse saveEmployeeLiquidation(LiquidationRequest liquidationRequest){

        Optional<Employee> employee = Optional.ofNullable(iFindEmployeeByIdGateway.findById(liquidationRequest.getEmployeeId().getValue()));

        if (employee.isEmpty()){
            throw new EntityExistsException(
                    "ID doesn't exist, please try to provide an existing employee ID or check the provided ID");
        }

        if (Boolean.FALSE.equals(employee.get().getEmployeeState().getValue())){
            throw new IllegalArgumentException("Inactive employees cannot be terminated");
        }

        LocalDate date = employee.get().getEmployeeLastSalaryUpdated().getValue();

        if (date.isAfter(liquidationRequest.getEmployeeContractEnd().getValue())) {
            throw new IllegalArgumentException(
                    "the date provided must be higher than the previous one: "
                            + date.format(GlobalConstants.DATE_FORMAT));
        }
        List<SalaryHistory> salaryHistory = iFindAllSalaryHistoryGateway
                .findAllSalaryHistoryByEmployeeId(liquidationRequest.getEmployeeId().getValue());

        TransportationAllowance transportationAllowance = transportationAllowance(employee.get().getEmployeeCurrentSalary().getValue());

        TotalDaysWorked totalDaysWorked = totalDaysWorked(
                employee.get().getEmployeeContractStart().getValue(), liquidationRequest.getEmployeeContractEnd().getValue());

        DaysWorkedCurrentYear daysWorkedCurrentYear = daysWorkedCurrentYear(liquidationRequest.getEmployeeContractEnd().getValue());

        VacationDaysToBeTaken vacationDaysToBeTaken = vacationDaysToBeTaken(totalDaysWorked.getValue());

        DaysWorkedLastSixMonths daysWorkedLastSixMonths = daysWorkedLastSixMonths(
                employee.get().getEmployeeContractStart().getValue(), liquidationRequest.getEmployeeContractEnd().getValue());

        BaseSettlementSalary baseSettlementSalary = baseSettlementSalary(
                salaryHistory, liquidationRequest.getEmployeeContractEnd().getValue().getYear(),
                employee.get().getEmployeeCurrentSalary().getValue());

        SeverancePay severancePay = severancePay(baseSettlementSalary.getValue(), daysWorkedCurrentYear.getValue());

        VacationPay vacationPay = vacationPay(
                employee.get().getEmployeeCurrentSalary().getValue() + transportationAllowance.getValue(),
                totalDaysWorked.getValue());

        InterestSeverancePay interestSeverancePay = interestSeverancePay(severancePay.getValue(), daysWorkedCurrentYear.getValue());

        ServiceBonus serviceBonus = serviceBonus(baseSettlementSalary.getValue(), daysWorkedLastSixMonths.getValue());

        PayrollPayable payrollPayable = payrollPayable(
                employee.get().getEmployeeCurrentSalary().getValue(),
                transportationAllowance.getValue(),
                employee.get().getEmployeeContractStart().getValue(),
                liquidationRequest.getEmployeeContractEnd().getValue());

        BonusUnjustifiedDismissal bonusUnjustifiedDismissal = bonusUnjustifiedDismissal(liquidationRequest.getLrWithdrawalReason().getValue(),
                baseSettlementSalary.getValue(),totalDaysWorked.getValue());

        TotalSettlement totalSettlement = totalSettlement(
                severancePay.getValue(),
                vacationPay.getValue(),
                interestSeverancePay.getValue(),
                serviceBonus.getValue(),
                payrollPayable.getValue(),
                bonusUnjustifiedDismissal.getValue());

        return iSaveLiquidationGateway.saveLiquidation(new LiquidationPaymentResponse(
                new LiquidationPaymentResponseId(null),
                employee.get(),
                employee.get().getEmployeeCurrentSalary(),
                transportationAllowance,
                employee.get().getEmployeeContractStart(),
                liquidationRequest.getEmployeeContractEnd(),
                liquidationRequest.getLrWithdrawalReason(),
                totalDaysWorked,
                daysWorkedCurrentYear,
                vacationDaysToBeTaken,
                daysWorkedLastSixMonths,
                baseSettlementSalary,
                severancePay,
                vacationPay,
                interestSeverancePay,
                serviceBonus,
                payrollPayable,
                bonusUnjustifiedDismissal,
                totalSettlement
        ));

    }

    private TransportationAllowance transportationAllowance(Double currentSalary){
        return new TransportationAllowance(
                currentSalary <= 2320000 ? 102854D : 0);
    }

    private TotalDaysWorked totalDaysWorked(LocalDate contractStart, LocalDate contractEnd){
        return new TotalDaysWorked(
                between(contractStart,contractEnd).getDays()
        );
    }

    private DaysWorkedCurrentYear daysWorkedCurrentYear(LocalDate contractEnd){
        return new DaysWorkedCurrentYear(
                between(LocalDate.of(contractEnd.getYear(), 1,1),contractEnd).getDays()
        );
    }

    private VacationDaysToBeTaken vacationDaysToBeTaken(Integer totalDaysWorked){
        return new VacationDaysToBeTaken(
                GlobalConstants.VACATIONS_PER_LABORAL_DAY * totalDaysWorked
        );
    }

    private DaysWorkedLastSixMonths daysWorkedLastSixMonths(LocalDate contractStart, LocalDate contractEnd){
        int days;
        LocalDate firstHalfStart = LocalDate.of(contractEnd.getYear(), 1 , 1);
        LocalDate firstHalfEnd = LocalDate.of(contractEnd.getYear(), 6 , 30);
        LocalDate secondHalfStart = LocalDate.of(contractEnd.getYear(), 7 , 1);
        if (contractStart.getYear() == contractEnd.getYear()){
            if (contractStart.isBefore(secondHalfStart) && contractEnd.isBefore(secondHalfStart)){
                days = between(contractStart, contractEnd).getDays();
            }else if (contractStart.isAfter(firstHalfEnd)&& contractEnd.isAfter(firstHalfEnd)){
                days = between(contractStart, contractEnd).getDays();
            }else {
                days = between(secondHalfStart, contractEnd).getDays();
            }
        } else {
            if (contractEnd.isBefore(secondHalfStart)){
                days = between(firstHalfStart, contractStart).getDays();
            } else {
                days = between(secondHalfStart, contractEnd).getDays();
            }
        }
        return new DaysWorkedLastSixMonths(days);
    }

    private BaseSettlementSalary baseSettlementSalary(List<SalaryHistory> salaryHistories, Integer currentYear, Double lastSalary){
        int updatedAcumulation = 0;
        Double result = 0.0;
        for (SalaryHistory salary : salaryHistories) {
            if (salary.getShModificationDate().getValue().getYear() == currentYear){
                if (salary.getShUpdatedSalary().getValue() <= 2320000){
                    result += salary.getShUpdatedSalary().getValue() + 102854;
                }else {
                    result += salary.getShUpdatedSalary().getValue();
                }
                updatedAcumulation += 1;
            }
        }
        if (updatedAcumulation == 0){
            return new BaseSettlementSalary(lastSalary);
        } else {
            return new BaseSettlementSalary(result/updatedAcumulation);
        }
    }

    private SeverancePay severancePay(Double baseSettlementSalary, Integer daysWorkedCurrentYear){
        return new SeverancePay((baseSettlementSalary * daysWorkedCurrentYear)/360);
    }

    private VacationPay vacationPay(Double baseSalary, Integer totalDays){
        return new VacationPay((baseSalary * totalDays)/720);
    }

    private InterestSeverancePay interestSeverancePay(Double severancePay, Integer totalWorkingDaysCurrentYear){
        return new InterestSeverancePay((severancePay * totalWorkingDaysCurrentYear * 0.12)/360);
    }

    private ServiceBonus serviceBonus(Double baseSettlementSalary, Integer daysWorkedLastSixMonths){
        return new ServiceBonus((baseSettlementSalary * daysWorkedLastSixMonths) / 360);
    }

    private PayrollPayable payrollPayable(double currentSalary, Double transportationAllowance, LocalDate contractStart, LocalDate contractEnd){
        int daysPayable = daysWorkedLastMont(contractStart, contractEnd);
        Double salary = (currentSalary/30) * daysPayable;
        Double transportation = (transportationAllowance/30) * daysPayable;
        return new PayrollPayable(salary + transportation);
    }

    private Integer daysWorkedLastMont(LocalDate contractStart, LocalDate contractEnd){
        int days;
        LocalDate firstHalfStart = LocalDate.of(contractEnd.getYear(), contractEnd.getMonth() , 1);
        LocalDate secondHalfStart = LocalDate.of(contractEnd.getYear(), contractEnd.getMonth(), 15);
        if (contractStart.getYear() == contractEnd.getYear() && contractStart.getMonth() == contractEnd.getMonth()){
            if (contractStart.isBefore(secondHalfStart) && contractEnd.isBefore(secondHalfStart)){
                days = between(contractStart, contractEnd).getDays();
            }else if (contractStart.isAfter(secondHalfStart)&& contractEnd.isAfter(secondHalfStart)){
                days = between(contractStart, contractEnd).getDays();
            }else {
                days = between(secondHalfStart, contractEnd).getDays();
            }
        } else {
            if (contractEnd.isBefore(secondHalfStart)){
                days = between(firstHalfStart, contractStart).getDays();
            } else {
                days = between(secondHalfStart, contractEnd).getDays();
            }
        }
        return days;
    }

    private BonusUnjustifiedDismissal bonusUnjustifiedDismissal(String withdrawalReason,Double baseSettlementSalary, Integer totalDaysWorked){
        if (withdrawalReason.equalsIgnoreCase("RETIRO VOLUNTARIO") || withdrawalReason.equalsIgnoreCase("RETIRO JUSTIFICADO")){
            return new BonusUnjustifiedDismissal(0d);
        } else if ( withdrawalReason.equalsIgnoreCase("RETIRO INJUSTIFICADO")) {
            double bonusFirstYear = ((30d / 360d) * totalDaysWorked) * ((baseSettlementSalary * 12) / 360);
            double bonus = totalDaysWorked <= 365 ? bonusFirstYear :
                    bonusFirstYear + ((20d/360d) * totalDaysWorked-365) * ((baseSettlementSalary*12)/360);
            return new BonusUnjustifiedDismissal(bonus);
        }
        return new BonusUnjustifiedDismissal(0d);
    }

    private TotalSettlement totalSettlement(
            Double severancePay,
            Double vacationPay,
            Double interestSeverancePay,
            Double serviceBonus,
            Double payrollPayable,
            Double bonusUnjustifiedDismissal
    ){
        return new TotalSettlement(
                severancePay + vacationPay + interestSeverancePay + serviceBonus + payrollPayable + bonusUnjustifiedDismissal);
    }

}
