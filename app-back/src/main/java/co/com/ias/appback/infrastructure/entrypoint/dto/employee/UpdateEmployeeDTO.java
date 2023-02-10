package co.com.ias.appback.infrastructure.entrypoint.dto.employee;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UpdateEmployeeDTO {

    @NotNull(message = "The id can't be null")
    @Size(min = 7, max = 15, message = "The size of the id can only be between 7 and 15 characters")
    private String id;

    @Size(min = 10, max = 30, message = "position size is only between 10 and 30 characters")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "the position cannot contain special characters")
    private String position;

    @DecimalMin(value = "1160000", message = "the salary can't be less than SMMLV ($1.160.000)")
    @DecimalMax(value = "7000000", message = "the salary can't be more than $7.000.000")
    private Double currentSalary;

    @NotNull(message = "The date can't be null")
    @Pattern(regexp = "\\d{4}\\/(0[1-9]|[12][0-9]|3[01])\\/(0[1-9]|1[0-2])$", message = "the date format is \"yyyy-dd-MM\"")
    private String modificationDate;

}
