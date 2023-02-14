package co.com.ias.appback.infrastructure.entrypoint.dto.liquidation_page_response;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class PaginationLiquidationDTO {


    @Pattern(regexp = "\\d{4}\\/(0[1-9]|[12][0-9]|3[01])\\/(0[1-9]|1[0-2])$", message = "the date format is \"yyyy-dd-MM\"")
    private String minRangeDate;

    @Pattern(regexp = "\\d{4}\\/(0[1-9]|[12][0-9]|3[01])\\/(0[1-9]|1[0-2])$", message = "the date format is \"yyyy-dd-MM\"")
    private String maxRangeDate;

    @Min(value = 5, message = "minimum 5 records per page")
    @Max(value = 15, message = "maximum 15 records per page")
    private Integer recordsPerPage;

    @Min(value = 1, message = "the pages start with 1")
    private Integer page;
}
