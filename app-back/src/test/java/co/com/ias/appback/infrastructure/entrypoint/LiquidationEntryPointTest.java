package co.com.ias.appback.infrastructure.entrypoint;

import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.employee.attributes.*;
import co.com.ias.appback.domain.model.employee_page_response.attributes.PageResponsePaging;
import co.com.ias.appback.domain.model.employee_page_response.attributes.PageResponseRemainingResults;
import co.com.ias.appback.domain.model.employee_page_response.attributes.PageResponseResults;
import co.com.ias.appback.domain.model.liquidation_page_response.LiquidationPageResponse;
import co.com.ias.appback.domain.model.liquidation_payment_request.LiquidationRequest;
import co.com.ias.appback.domain.model.liquidation_payment_request.attributes.EmployeeContractEnd;
import co.com.ias.appback.domain.model.liquidation_payment_request.attributes.LRWithdrawalReason;
import co.com.ias.appback.domain.model.liquidation_payment_response.LiquidationPaymentResponse;
import co.com.ias.appback.domain.model.liquidation_payment_response.attributes.*;
import co.com.ias.appback.domain.usecase.liquidation.FindAllLiquidationUseCase;
import co.com.ias.appback.domain.usecase.liquidation.FindLiquidationByIdUseCase;
import co.com.ias.appback.domain.usecase.liquidation.SaveLiquidationUseCase;
import co.com.ias.appback.infrastructure.entrypoint.dto.liquidation_page_response.PaginationLiquidationDTO;
import co.com.ias.appback.infrastructure.entrypoint.dto.liquidation_request.LiquidationRequestDTO;
import co.com.ias.appback.infrastructure.entrypoint.dto.liquidation_response.LiquidationPaymentResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LiquidationEntryPoint.class)
class LiquidationEntryPointTest {

    @MockBean
    private FindAllLiquidationUseCase findAllLiquidationUseCase;
    @MockBean
    private FindLiquidationByIdUseCase findLiquidationByIdUseCase;
    @MockBean
    private SaveLiquidationUseCase saveLiquidationUseCase;
    @Autowired
    private MockMvc mockMvc;
    @Test
    @DisplayName("save liquidation correct")
    void saveLiquidation() throws Exception {
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
        LiquidationRequestDTO liquidationRequestDTO = new LiquidationRequestDTO(
                "123456789",
                "RETIRO VOLUNTARIO",
                "2017/10/01"
        );

        LiquidationPaymentResponseDTO liquidationPaymentResponseDTO = new LiquidationPaymentResponseDTO()
                .fromDomain(liquidationPaymentResponse);

        ObjectMapper mapper = new ObjectMapper();

        when(saveLiquidationUseCase.saveEmployeeLiquidation(any(LiquidationRequest.class)))
                .thenReturn(liquidationPaymentResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/employee/liquidation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(liquidationRequestDTO)))
                    .andExpect(status().isCreated())
                .andExpect(content()
                        .string(containsString(liquidationPaymentResponseDTO.getEmployeeId())))
                .andExpect(content().string(containsString(liquidationPaymentResponseDTO.getEmployeeContractStart())))
                .andExpect(content().string(containsString(liquidationPaymentResponseDTO.getEmployeePosition())))
                .andExpect(content().string(containsString(liquidationPaymentResponseDTO.getWithdrawalReason())))
                .andExpect(content().string(containsString(liquidationPaymentResponseDTO.getEmployeeContractEnd())))
                .andExpect(content().string(containsString(liquidationPaymentResponseDTO.getEmployeeName())));
    }

    @Test
    @DisplayName("Find liquidation correctly")
    void findLiquidationByEmployeeId() throws Exception {

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

        LiquidationPaymentResponseDTO liquidationPaymentResponseDTO = new LiquidationPaymentResponseDTO()
                .fromDomain(liquidationPaymentResponse);

        when(findLiquidationByIdUseCase.findLiquidationById(any(String.class)))
                .thenReturn(liquidationPaymentResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/liquidation/{id}","123456789"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(content()
                        .string(containsString(liquidationPaymentResponseDTO.getEmployeeId())))
                .andExpect(content().string(containsString(liquidationPaymentResponseDTO.getEmployeeContractStart())))
                .andExpect(content().string(containsString(liquidationPaymentResponseDTO.getEmployeePosition())))
                .andExpect(content().string(containsString(liquidationPaymentResponseDTO.getWithdrawalReason())))
                .andExpect(content().string(containsString(liquidationPaymentResponseDTO.getEmployeeContractEnd())))
                .andExpect(content().string(containsString(liquidationPaymentResponseDTO.getEmployeeName())));
    }

    @Test
    void findAllLiquidation() throws Exception {
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
        PaginationLiquidationDTO paginationLiquidationDTO = new PaginationLiquidationDTO(
                "2016/03/03",
                "2016/04/03",
                6,
                1
        );

        LiquidationPageResponse liquidationPageResponse = new LiquidationPageResponse(
                new PageResponsePaging(1),
                new PageResponseResults(1),
                new PageResponseRemainingResults(1),
                List.of(liquidationPaymentResponse)
        );
        LiquidationPaymentResponseDTO liquidationPaymentResponseDTO = new LiquidationPaymentResponseDTO()
                .fromDomain(liquidationPaymentResponse);
        ObjectMapper mapper = new ObjectMapper();


        when(findAllLiquidationUseCase.findAllByDateRange(any(LocalDate.class), any(LocalDate.class), any(Integer.class), any(Integer.class)))
                .thenReturn(liquidationPageResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/employee/liquidation/date-range")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(paginationLiquidationDTO)))
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(content()
                        .string(containsString(liquidationPaymentResponseDTO.getEmployeeId())))
                .andExpect(content().string(containsString(liquidationPaymentResponseDTO.getEmployeeContractStart())))
                .andExpect(content().string(containsString(liquidationPaymentResponseDTO.getEmployeePosition())))
                .andExpect(content().string(containsString(liquidationPaymentResponseDTO.getWithdrawalReason())))
                .andExpect(content().string(containsString(liquidationPaymentResponseDTO.getEmployeeContractEnd())))
                .andExpect(content().string(containsString(liquidationPaymentResponseDTO.getEmployeeName())));
    }
}