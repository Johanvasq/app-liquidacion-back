package co.com.ias.appback.domain.usecase.liquidation;

import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.employee.attributes.*;
import co.com.ias.appback.domain.model.gateway.employee.IFindEmployeeByIdGateway;
import co.com.ias.appback.domain.model.gateway.liquidation.IFindLiquidationByIdGateway;
import co.com.ias.appback.domain.model.liquidation_payment_request.attributes.EmployeeContractEnd;
import co.com.ias.appback.domain.model.liquidation_payment_request.attributes.LRWithdrawalReason;
import co.com.ias.appback.domain.model.liquidation_payment_response.LiquidationPaymentResponse;
import co.com.ias.appback.domain.model.liquidation_payment_response.attributes.*;
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
class FindLiquidationByIdUseCaseTest {

    @InjectMocks
    private FindLiquidationByIdUseCase findLiquidationByIdUseCase;
    @Mock
    private IFindLiquidationByIdGateway iFindLiquidationByIdGateway;
    @Mock
    private IFindEmployeeByIdGateway iFindEmployeeByIdGateway;

    @Test
    @DisplayName("find liquidation without employee")
    void findLiquidationById() {
        when(iFindEmployeeByIdGateway.findById(any(String.class))).thenReturn(null);

        EntityExistsException exception = assertThrows(EntityExistsException.class, () -> {
            findLiquidationByIdUseCase.findLiquidationById("12354565498");
        });

        assertEquals("ID doesn't exist, please try to provide an existing employee ID or check the provided ID",
                exception.getMessage());
    }

    @Test
    @DisplayName("find liquidation with employee")
    void testFindLiquidationById() {
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
        when(iFindEmployeeByIdGateway.findById(any(String.class))).thenReturn(employee);

        when(iFindLiquidationByIdGateway.findLiquidationByEmployeeId(any(String.class)))
                .thenReturn(liquidationPaymentResponse);

        LiquidationPaymentResponse rta = findLiquidationByIdUseCase.findLiquidationById("123456789");
        assertEquals(1500000.0, rta.getLprLastSalary().getValue());
        assertEquals(102854.0, rta.getTransportationAllowance().getValue());
        assertEquals("RETIRO INJUSTIFICADO", rta.getWithdrawalReason().getValue());
    }

    @Test
    @DisplayName("find liquidation with employee")
    void test2FindLiquidationById() {
        Employee employee = new Employee(
                new EmployeeId("123456789"),
                new EmployeeName("Johan"),
                new EmployeeContractStart(LocalDate.of(2015,2,2)),
                new EmployeePosition("software developer"),
                new EmployeeState(true),
                new EmployeeCurrentSalary(1500000.0),
                new EmployeeLastSalaryUpdated(LocalDate.of(2015,2,2))

        );
        when(iFindEmployeeByIdGateway.findById(any(String.class))).thenReturn(employee);

        when(iFindLiquidationByIdGateway.findLiquidationByEmployeeId(any(String.class)))
                .thenReturn(null);

        IllegalStateException rta = assertThrows(IllegalStateException.class, () -> {
            findLiquidationByIdUseCase.findLiquidationById("123456789");
        } );

        assertEquals("The employee has not been liquidated", rta.getMessage());


    }
}