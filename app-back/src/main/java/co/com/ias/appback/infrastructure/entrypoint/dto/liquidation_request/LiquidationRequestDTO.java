package co.com.ias.appback.infrastructure.entrypoint.dto.liquidation_request;

import co.com.ias.appback.domain.model.constants.GlobalConstants;
import co.com.ias.appback.domain.model.employee.attributes.EmployeeId;
import co.com.ias.appback.domain.model.liquidation_payment_request.LiquidationRequest;
import co.com.ias.appback.domain.model.liquidation_payment_request.attributes.EmployeeContractEnd;
import co.com.ias.appback.domain.model.liquidation_payment_request.attributes.LRWithdrawalReason;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class LiquidationRequestDTO {
    @NotNull(message = "The id can't be null")
    @Size(min = 7, max = 15, message = "The size of the id can only be between 7 and 15 characters")
    private String employeeId;
    @NotNull(message = "the withdrawal reason can't be null")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "the withdrawal reason cannot contain special characters")
    private String withdrawalReason;
    @NotNull(message = "the date can't be null")
    @Pattern(regexp = "\\d{4}\\/(0[1-9]|[12][0-9]|3[01])\\/(0[1-9]|1[0-2])$", message = "the date format is \"yyyy-dd-MM\"")
    private String contractEnd;

    public LiquidationRequest toDomain() {
        return new LiquidationRequest(
                new EmployeeId(this.employeeId),
                new LRWithdrawalReason(this.withdrawalReason),
                new EmployeeContractEnd(LocalDate.parse(this.contractEnd, GlobalConstants.DATE_FORMAT))
        );
    }
}
