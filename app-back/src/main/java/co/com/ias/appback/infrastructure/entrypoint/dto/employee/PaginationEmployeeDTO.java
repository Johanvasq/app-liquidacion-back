package co.com.ias.appback.infrastructure.entrypoint.dto.employee;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class PaginationEmployeeDTO {

    @DecimalMin(value = "1160000", message = "the min range of the salary can't be less than SMMLV ($1.160.000)")
    @DecimalMax(value = "7000000", message = "the max range of the salary can't be more than $7.000.000")
    private Double minRangeSalary;


    @DecimalMin(value = "1160000", message = "the min range of the salary can't be less than SMMLV ($1.160.000)")
    @DecimalMax(value = "7000000", message = "the max range of the salary can't be more than $7.000.000")
    private Double maxRangeSalary;

    @Min(value = 5, message = "minimum 5 records per page")
    @Max(value = 15, message = "maximum 15 records per page")
    private Integer recordsPerPage;

    @NotNull(message = "you must send the value of the page, start with 1")
    @Min(value = 1, message = "the pages start with 1")
    private Integer page;

}
