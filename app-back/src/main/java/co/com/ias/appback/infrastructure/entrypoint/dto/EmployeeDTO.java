package co.com.ias.appback.infrastructure.entrypoint.dto;

import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.employee.attributes.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.util.Assert.isTrue;

@NoArgsConstructor
@Getter
@Setter
public class EmployeeDTO {
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy/dd/MM");
    @NotNull(message = "The name can't be null")
    @Size(max = 50, message = "The name only allows 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "The name can't contain special characters")
    private String name;
    @NotNull(message = "The id can't be null")
    @Size(min = 7, max = 15, message = "The size of the id can only be between 7 and 15 characters")
    private String id;

    @Pattern(regexp = "\\d{4}\\/(0[1-9]|[12][0-9]|3[01])\\/(0[1-9]|1[0-2])$", message = "the date format is \"yyyy-dd-MM\"")
    private String contractStart;

    @Size(min = 10, max = 30, message = "position size is only between 10 and 30 characters")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "the position cannot contain special characters")
    private String position;

    @DecimalMin(value = "1160000", message = "the salary can't be less than SMMLV ($1.160.000)")
    @DecimalMax(value = "7000000", message = "the salary can't be more than $7.000.000")
    private Double currentSalary;
    private Boolean state;

    public EmployeeDTO(String id,String name, String contractStart, String position, Double currentSalary, Boolean state) {
        this.id = id;
        this.name = name;
        validationContractStart(LocalDate.parse(contractStart, DATE_FORMAT));
        this.contractStart = contractStart;
        this.position = position;
        this.currentSalary = currentSalary;
        this.state = state;
    }

    public Employee toDomain(){
        return new Employee(
                new EmployeeId(this.id),
                new EmployeeName(this.name),
                new EmployeeContractStart(LocalDate.parse(this.contractStart, DATE_FORMAT)),
                new EmployeePosition(this.position),
                new EmployeeState(this.state),
                new EmployeeCurrentSalary(this.currentSalary)
        );
    }

    public EmployeeDTO fromDomain(Employee employee){
        return new EmployeeDTO(
                employee.getEmployeeId().getValue(),
                employee.getEmployeeName().getValue(),
                employee.getEmployeeContractStart().getValue().format(DATE_FORMAT),
                employee.getEmployeePosition().getValue(),
                employee.getEmployeeCurrentSalary().getValue(),
                employee.getEmployeeState().getValue()
        );
    }

    /**
     * validation of value "Contract Start"
     * @param value of value "Contract start"
     * @throws IllegalArgumentException if the object isn't valid
     */
    private void validationContractStart(LocalDate value) throws IllegalArgumentException{
        isTrue(value.isAfter(LocalDate.of(2015,1,1)),
                "the date can't be before of 01/01/2015");
        isTrue(value.isBefore(LocalDate.of(2023,6,6)),
                "the date can't be after of 06/06/2023");

    }

}
