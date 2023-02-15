package co.com.ias.appback.domain.usecase.liquidation;

import co.com.ias.appback.domain.model.constants.GlobalConstants;
import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.employee.attributes.*;
import co.com.ias.appback.domain.model.gateway.employee.IFindEmployeeByIdGateway;
import co.com.ias.appback.domain.model.gateway.employee.IUpdateEmployeeGateway;
import co.com.ias.appback.domain.model.gateway.liquidation.ISaveLiquidationGateway;
import co.com.ias.appback.domain.model.gateway.salary.IFindAllSalaryHistoryGateway;
import co.com.ias.appback.domain.model.liquidation_payment_request.LiquidationRequest;
import co.com.ias.appback.domain.model.liquidation_payment_request.attributes.EmployeeContractEnd;
import co.com.ias.appback.domain.model.liquidation_payment_request.attributes.LRWithdrawalReason;
import co.com.ias.appback.domain.model.liquidation_payment_response.LiquidationPaymentResponse;
import co.com.ias.appback.domain.model.liquidation_payment_response.attributes.*;
import co.com.ias.appback.infrastructure.entrypoint.dto.liquidation_request.LiquidationRequestDTO;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class SaveLiquidationUseCaseTest {
    @InjectMocks
    private SaveLiquidationUseCase saveLiquidationUseCase;

    @Mock
    private ISaveLiquidationGateway iSaveLiquidationGateway;
    @Mock
    private IFindEmployeeByIdGateway iFindEmployeeByIdGateway;
    @Mock
    private IFindAllSalaryHistoryGateway iFindAllSalaryHistoryGateway;
    @Mock
    private IUpdateEmployeeGateway iUpdateEmployeeGateway;

    @Test
    @DisplayName("Save liquidation ok")
    void saveEmployeeLiquidation() {
        Employee employee = new Employee(
                new EmployeeId("123456789"),
                new EmployeeName("Johan"),
                new EmployeeContractStart(LocalDate.of(2015,2,2)),
                new EmployeePosition("software developer"),
                new EmployeeState(true),
                new EmployeeCurrentSalary(1500000.0),
                new EmployeeLastSalaryUpdated(LocalDate.of(2015,2,2))

        );
        LiquidationPaymentResponse liquidationPaymentResponse = new LiquidationPaymentResponse(
                new LiquidationPaymentResponseId("1"),
                employee,
                new EmployeeCurrentSalary(1500000.0),
                new TransportationAllowance(102854.0),
                new EmployeeContractStart(LocalDate.of(2016,1,1)),
                new EmployeeContractEnd(LocalDate.of(2017,10,1)),
                new LRWithdrawalReason("RETIRO VOLUNTARIO"),
                new TotalDaysWorked(396),
                new DaysWorkedCurrentYear(201),
                new VacationDaysToBeTaken(16.5),
                new DaysWorkedLastSixMonths(20),
                new BaseSettlementSalary(2000000.0),
                new SeverancePay(1116666.6666666667),
                new VacationPay(1156569.7),
                new InterestSeverancePay(74816.66666666667),
                new ServiceBonus(111111.11111111111),
                new PayrollPayable(420570.8),
                new BonusUnjustifiedDismissal(0d),
                new TotalSettlement(2879734.944444444)
        );
        LiquidationRequestDTO liquidationRequestDTO = new LiquidationRequestDTO(
                "123456789",
                "RETIRO VOLUNTARIO",
                "2017/21/07"
        );
        LiquidationRequest liquidationRequest = liquidationRequestDTO.toDomain();

        when(iFindEmployeeByIdGateway.findById(any(String.class)))
                .thenReturn(employee);

        when(iUpdateEmployeeGateway.updateEmployee(any(Employee.class))).thenReturn(employee);

        when(iSaveLiquidationGateway.saveLiquidation(any(LiquidationPaymentResponse.class)))
                .thenReturn(liquidationPaymentResponse);

        LiquidationPaymentResponse rta = saveLiquidationUseCase.saveEmployeeLiquidation(liquidationRequest);

        assertEquals(1500000.0, rta.getLprLastSalary().getValue());
        assertEquals(102854.0, rta.getTransportationAllowance().getValue());
        assertEquals("RETIRO VOLUNTARIO", rta.getWithdrawalReason().getValue());

    }

    @Test
    @DisplayName("Save liquidation ok")
    void save2EmployeeLiquidation() {
        Employee employee = new Employee(
                new EmployeeId("123456789"),
                new EmployeeName("Johan"),
                new EmployeeContractStart(LocalDate.of(2015,2,2)),
                new EmployeePosition("software developer"),
                new EmployeeState(true),
                new EmployeeCurrentSalary(1500000.0),
                new EmployeeLastSalaryUpdated(LocalDate.of(2015,2,2))

        );
        LiquidationPaymentResponse liquidationPaymentResponse = new LiquidationPaymentResponse(
                new LiquidationPaymentResponseId("1"),
                employee,
                new EmployeeCurrentSalary(1500000.0),
                new TransportationAllowance(102854.0),
                new EmployeeContractStart(LocalDate.of(2016,1,1)),
                new EmployeeContractEnd(LocalDate.of(2017,10,1)),
                new LRWithdrawalReason("RETIRO INJUSTIFICADO"),
                new TotalDaysWorked(396),
                new DaysWorkedCurrentYear(201),
                new VacationDaysToBeTaken(16.5),
                new DaysWorkedLastSixMonths(20),
                new BaseSettlementSalary(2000000.0),
                new SeverancePay(1116666.6666666667),
                new VacationPay(1156569.7),
                new InterestSeverancePay(74816.66666666667),
                new ServiceBonus(111111.11111111111),
                new PayrollPayable(420570.8),
                new BonusUnjustifiedDismissal(0d),
                new TotalSettlement(2879734.944444444)
        );
        LiquidationRequestDTO liquidationRequestDTO = new LiquidationRequestDTO(
                "123456789",
                "RETIRO INJUSTIFICADO",
                "2017/21/07"
        );
        LiquidationRequest liquidationRequest = liquidationRequestDTO.toDomain();

        when(iFindEmployeeByIdGateway.findById(any(String.class)))
                .thenReturn(employee);

        when(iUpdateEmployeeGateway.updateEmployee(any(Employee.class))).thenReturn(employee);

        when(iSaveLiquidationGateway.saveLiquidation(any(LiquidationPaymentResponse.class)))
                .thenReturn(liquidationPaymentResponse);

        LiquidationPaymentResponse rta = saveLiquidationUseCase.saveEmployeeLiquidation(liquidationRequest);

        assertEquals(1500000.0, rta.getLprLastSalary().getValue());
        assertEquals(102854.0, rta.getTransportationAllowance().getValue());
        assertEquals("RETIRO INJUSTIFICADO", rta.getWithdrawalReason().getValue());

    }

    @Test
    @DisplayName("save liquidation Not found employee")
    void testSaveEmployeeLiquidation() {
        Employee employee = new Employee(
                new EmployeeId("123456789"),
                new EmployeeName("Johan"),
                new EmployeeContractStart(LocalDate.of(2015,2,2)),
                new EmployeePosition("software developer"),
                new EmployeeState(true),
                new EmployeeCurrentSalary(1500000.0),
                new EmployeeLastSalaryUpdated(LocalDate.of(2015,2,2))
        );
        LiquidationPaymentResponse liquidationPaymentResponse = new LiquidationPaymentResponse(
                new LiquidationPaymentResponseId("1"),
                employee,
                new EmployeeCurrentSalary(1500000.0),
                new TransportationAllowance(102854.0),
                new EmployeeContractStart(LocalDate.of(2016,1,1)),
                new EmployeeContractEnd(LocalDate.of(2017,10,1)),
                new LRWithdrawalReason("RETIRO VOLUNTARIO"),
                new TotalDaysWorked(396),
                new DaysWorkedCurrentYear(201),
                new VacationDaysToBeTaken(16.5),
                new DaysWorkedLastSixMonths(20),
                new BaseSettlementSalary(2000000.0),
                new SeverancePay(1116666.6666666667),
                new VacationPay(1156569.7),
                new InterestSeverancePay(74816.66666666667),
                new ServiceBonus(111111.11111111111),
                new PayrollPayable(420570.8),
                new BonusUnjustifiedDismissal(0d),
                new TotalSettlement(2879734.944444444)
        );
        LiquidationRequestDTO liquidationRequestDTO = new LiquidationRequestDTO(
                "123456789",
                "RETIRO VOLUNTARIO",
                "2017/21/07"
        );
        LiquidationRequest liquidationRequest = liquidationRequestDTO.toDomain();

        when(iFindEmployeeByIdGateway.findById(any(String.class)))
                .thenReturn(null);

        when(iUpdateEmployeeGateway.updateEmployee(any(Employee.class))).thenReturn(employee);

        when(iSaveLiquidationGateway.saveLiquidation(any(LiquidationPaymentResponse.class)))
                .thenReturn(liquidationPaymentResponse);

        EntityExistsException rta = assertThrows(EntityExistsException.class, () -> {
            saveLiquidationUseCase.saveEmployeeLiquidation(liquidationRequest);
        });

        assertEquals("ID doesn't exist, please try to provide an existing employee ID or check the provided ID", rta.getMessage());

    }

    @Test
    @DisplayName("save liquidation inactive employee")
    void test2SaveEmployeeLiquidation() {
        Employee employee = new Employee(
                new EmployeeId("123456789"),
                new EmployeeName("Johan"),
                new EmployeeContractStart(LocalDate.of(2015,2,2)),
                new EmployeePosition("software developer"),
                new EmployeeState(false),
                new EmployeeCurrentSalary(1500000.0),
                new EmployeeLastSalaryUpdated(LocalDate.of(2015,2,2))
        );
        LiquidationPaymentResponse liquidationPaymentResponse = new LiquidationPaymentResponse(
                new LiquidationPaymentResponseId("1"),
                employee,
                new EmployeeCurrentSalary(1500000.0),
                new TransportationAllowance(102854.0),
                new EmployeeContractStart(LocalDate.of(2016,1,1)),
                new EmployeeContractEnd(LocalDate.of(2017,10,1)),
                new LRWithdrawalReason("RETIRO VOLUNTARIO"),
                new TotalDaysWorked(396),
                new DaysWorkedCurrentYear(201),
                new VacationDaysToBeTaken(16.5),
                new DaysWorkedLastSixMonths(20),
                new BaseSettlementSalary(2000000.0),
                new SeverancePay(1116666.6666666667),
                new VacationPay(1156569.7),
                new InterestSeverancePay(74816.66666666667),
                new ServiceBonus(111111.11111111111),
                new PayrollPayable(420570.8),
                new BonusUnjustifiedDismissal(0d),
                new TotalSettlement(2879734.944444444)
        );
        LiquidationRequestDTO liquidationRequestDTO = new LiquidationRequestDTO(
                "123456789",
                "RETIRO VOLUNTARIO",
                "2017/21/07"
        );
        LiquidationRequest liquidationRequest = liquidationRequestDTO.toDomain();

        when(iFindEmployeeByIdGateway.findById(any(String.class)))
                .thenReturn(employee);

        when(iUpdateEmployeeGateway.updateEmployee(any(Employee.class))).thenReturn(employee);

        when(iSaveLiquidationGateway.saveLiquidation(any(LiquidationPaymentResponse.class)))
                .thenReturn(liquidationPaymentResponse);

        IllegalArgumentException rta = assertThrows(IllegalArgumentException.class, () -> {
            saveLiquidationUseCase.saveEmployeeLiquidation(liquidationRequest);
        });

        assertEquals("Inactive employees cannot be terminated", rta.getMessage());

    }

    @Test
    @DisplayName("save liquidation incorrect termination date")
    void test3SaveEmployeeLiquidation() {
        Employee employee = new Employee(
                new EmployeeId("123456789"),
                new EmployeeName("Johan"),
                new EmployeeContractStart(LocalDate.of(2015,2,2)),
                new EmployeePosition("software developer"),
                new EmployeeState(true),
                new EmployeeCurrentSalary(1500000.0),
                new EmployeeLastSalaryUpdated(LocalDate.of(2020,2,2))
        );
        LiquidationPaymentResponse liquidationPaymentResponse = new LiquidationPaymentResponse(
                new LiquidationPaymentResponseId("1"),
                employee,
                new EmployeeCurrentSalary(1500000.0),
                new TransportationAllowance(102854.0),
                new EmployeeContractStart(LocalDate.of(2016,1,1)),
                new EmployeeContractEnd(LocalDate.of(2017,10,1)),
                new LRWithdrawalReason("RETIRO VOLUNTARIO"),
                new TotalDaysWorked(396),
                new DaysWorkedCurrentYear(201),
                new VacationDaysToBeTaken(16.5),
                new DaysWorkedLastSixMonths(20),
                new BaseSettlementSalary(2000000.0),
                new SeverancePay(1116666.6666666667),
                new VacationPay(1156569.7),
                new InterestSeverancePay(74816.66666666667),
                new ServiceBonus(111111.11111111111),
                new PayrollPayable(420570.8),
                new BonusUnjustifiedDismissal(0d),
                new TotalSettlement(2879734.944444444)
        );
        LiquidationRequestDTO liquidationRequestDTO = new LiquidationRequestDTO(
                "123456789",
                "RETIRO VOLUNTARIO",
                "2017/21/07"
        );
        LiquidationRequest liquidationRequest = liquidationRequestDTO.toDomain();

        when(iFindEmployeeByIdGateway.findById(any(String.class)))
                .thenReturn(employee);

        when(iUpdateEmployeeGateway.updateEmployee(any(Employee.class))).thenReturn(employee);

        when(iSaveLiquidationGateway.saveLiquidation(any(LiquidationPaymentResponse.class)))
                .thenReturn(liquidationPaymentResponse);

        IllegalArgumentException rta = assertThrows(IllegalArgumentException.class, () -> {
            saveLiquidationUseCase.saveEmployeeLiquidation(liquidationRequest);
        });

        assertEquals("the date provided must be higher than the previous one: "
                + employee.getEmployeeLastSalaryUpdated().getValue().format(GlobalConstants.DATE_FORMAT),
                rta.getMessage());

    }
}