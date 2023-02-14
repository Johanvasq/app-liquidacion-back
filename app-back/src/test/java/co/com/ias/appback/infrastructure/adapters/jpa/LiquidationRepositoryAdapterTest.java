package co.com.ias.appback.infrastructure.adapters.jpa;

import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.employee.attributes.*;
import co.com.ias.appback.domain.model.liquidation_payment_request.attributes.EmployeeContractEnd;
import co.com.ias.appback.domain.model.liquidation_payment_request.attributes.LRWithdrawalReason;
import co.com.ias.appback.domain.model.liquidation_payment_response.LiquidationPaymentResponse;
import co.com.ias.appback.domain.model.liquidation_payment_response.attributes.*;
import co.com.ias.appback.infrastructure.adapters.jpa.entity.EmployeeDBO;
import co.com.ias.appback.infrastructure.adapters.jpa.entity.LiquidationPaymentResponseDBO;
import co.com.ias.appback.infrastructure.adapters.jpa.repository.IEmployeeRepositoryAdapter;
import co.com.ias.appback.infrastructure.adapters.jpa.repository.ILiquidationRepositoryAdapter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class LiquidationRepositoryAdapterTest {

    @InjectMocks
    private LiquidationRepositoryAdapter liquidationRepositoryAdapter;

    @Autowired
    private ILiquidationRepositoryAdapter repository;
    @Autowired
    IEmployeeRepositoryAdapter repositoryEmployee;

    @BeforeAll
    void init(){ liquidationRepositoryAdapter = new LiquidationRepositoryAdapter(repository); }

    @Test
    @DisplayName("find all liquidation by range")
    void findLiquidationByDateRange() {
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
                new EmployeeCurrentSalary(2000000.0),
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
        Pageable pageable = PageRequest.of(0, 6);

        EmployeeDBO employeeDBO = new EmployeeDBO().fromDomain(employee);

        LiquidationPaymentResponseDBO liquidationPaymentResponseDBO = new LiquidationPaymentResponseDBO().fromDomain(liquidationPaymentResponse);

        repositoryEmployee.save(employeeDBO);
        repository.save(liquidationPaymentResponseDBO);

        Page<LiquidationPaymentResponse> rta = liquidationRepositoryAdapter.findLiquidationByDateRange(
                LocalDate.of(2015,1,1),
                LocalDate.of(2030,1,1),
                pageable
        );

        assertEquals(rta.getContent().get(0).getServiceBonus().getValue(), liquidationPaymentResponse.getServiceBonus().getValue());
        assertEquals(rta.getContent().get(0).getServiceBonus().getValue(), liquidationPaymentResponseDBO.getServiceBonus());
        assertEquals(rta.getContent().get(0).getInterestSeverancePay().getValue(), liquidationPaymentResponse.getInterestSeverancePay().getValue());
        assertEquals(rta.getContent().get(0).getInterestSeverancePay().getValue(), liquidationPaymentResponseDBO.getInterestSeverancePay());
        assertEquals(rta.getContent().get(0).getEmployeeContractStart().getValue(), liquidationPaymentResponse.getEmployeeContractStart().getValue());
        assertEquals(rta.getContent().get(0).getEmployeeContractStart().getValue(), liquidationPaymentResponseDBO.getEmployeeContractStart());

    }

    @Test
    @DisplayName("save liquidation")
    void saveLiquidation() {
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
                new EmployeeCurrentSalary(2000000.0),
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

        LiquidationPaymentResponseDBO liquidationPaymentResponseDBO = new LiquidationPaymentResponseDBO().fromDomain(liquidationPaymentResponse);

        repository.save(liquidationPaymentResponseDBO);

        LiquidationPaymentResponse rta = liquidationRepositoryAdapter.saveLiquidation(liquidationPaymentResponse);

        assertEquals(rta.getClass(), liquidationPaymentResponse.getClass());
        assertEquals(rta.getServiceBonus().getValue(), liquidationPaymentResponse.getServiceBonus().getValue());
        assertEquals(rta.getServiceBonus().getValue(), liquidationPaymentResponseDBO.getServiceBonus());
        assertEquals(rta.getInterestSeverancePay().getValue(), liquidationPaymentResponse.getInterestSeverancePay().getValue());
        assertEquals(rta.getInterestSeverancePay().getValue(), liquidationPaymentResponseDBO.getInterestSeverancePay());
        assertEquals(rta.getEmployeeContractStart().getValue(), liquidationPaymentResponse.getEmployeeContractStart().getValue());
        assertEquals(rta.getEmployeeContractStart().getValue(), liquidationPaymentResponseDBO.getEmployeeContractStart());


    }

    @Test
    @DisplayName("find liquidation by employee id")
    void findLiquidationByEmployeeId() {
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
                new EmployeeCurrentSalary(2000000.0),
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

        EmployeeDBO employeeDBO = new EmployeeDBO().fromDomain(employee);

        LiquidationPaymentResponseDBO liquidationPaymentResponseDBO = new LiquidationPaymentResponseDBO().fromDomain(liquidationPaymentResponse);

        repositoryEmployee.save(employeeDBO);
        repository.save(liquidationPaymentResponseDBO);

        LiquidationPaymentResponse rta = liquidationRepositoryAdapter.findLiquidationByEmployeeId("123456789");

        assertEquals(rta.getServiceBonus().getValue(), liquidationPaymentResponse.getServiceBonus().getValue());
        assertEquals(rta.getServiceBonus().getValue(), liquidationPaymentResponseDBO.getServiceBonus());
        assertEquals(rta.getInterestSeverancePay().getValue(), liquidationPaymentResponse.getInterestSeverancePay().getValue());
        assertEquals(rta.getInterestSeverancePay().getValue(), liquidationPaymentResponseDBO.getInterestSeverancePay());
        assertEquals(rta.getEmployeeContractStart().getValue(), liquidationPaymentResponse.getEmployeeContractStart().getValue());
        assertEquals(rta.getEmployeeContractStart().getValue(), liquidationPaymentResponseDBO.getEmployeeContractStart());

    }
}