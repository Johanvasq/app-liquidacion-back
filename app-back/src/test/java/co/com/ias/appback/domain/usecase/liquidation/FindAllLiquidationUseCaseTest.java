package co.com.ias.appback.domain.usecase.liquidation;

import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.employee.attributes.*;
import co.com.ias.appback.domain.model.gateway.liquidation.IFindAllLiquidationGateway;
import co.com.ias.appback.domain.model.liquidation_page_response.LiquidationPageResponse;
import co.com.ias.appback.domain.model.liquidation_payment_request.attributes.EmployeeContractEnd;
import co.com.ias.appback.domain.model.liquidation_payment_request.attributes.LRWithdrawalReason;
import co.com.ias.appback.domain.model.liquidation_payment_response.LiquidationPaymentResponse;
import co.com.ias.appback.domain.model.liquidation_payment_response.attributes.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class FindAllLiquidationUseCaseTest {

    @InjectMocks
    private FindAllLiquidationUseCase findAllLiquidationUseCase;

    @Mock
    private IFindAllLiquidationGateway iFindAllLiquidationGateway;

    @Test
    @DisplayName("Find all date range error")
    void findAllByDateRange() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            findAllLiquidationUseCase
                    .findAllByDateRange(LocalDate.of(2016, 1, 1),
                            LocalDate.of(2015, 1, 1),
                            6,
                            1);
        });
        assertEquals("please provide correct date range", exception.getMessage());
    }

    @Test
    @DisplayName("Find all date range ok")
    void testfindAllByDateRange() {
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


        when(iFindAllLiquidationGateway.findLiquidationByDateRange(any(LocalDate.class), any(LocalDate.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(liquidationPaymentResponse)));

        LiquidationPageResponse rta = findAllLiquidationUseCase
                .findAllByDateRange(LocalDate.of(2015, 1, 1),
                        LocalDate.of(2017, 1, 1),
                        6,
                        1);

        assertEquals(1500000.0, rta.getLiquidations().get(0).getLprLastSalary().getValue());
        assertEquals(102854.0, rta.getLiquidations().get(0).getTransportationAllowance().getValue());
        assertEquals("RETIRO INJUSTIFICADO", rta.getLiquidations().get(0).getWithdrawalReason().getValue());
    }
}