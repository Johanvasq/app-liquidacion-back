package co.com.ias.appback.infrastructure.adapters.jpa.entity;

import co.com.ias.appback.domain.model.employee.Employee;
import co.com.ias.appback.domain.model.employee.attributes.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "employee")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDBO {
    @Id
    private String id;
    private String name;
    private LocalDate contractStart;
    private String position;
    private Boolean state;
    private Double currentSalary;

    public Employee toDomain(){
        return new Employee(
                new EmployeeId(this.id),
                new EmployeeName(this.name),
                new EmployeeContractStart(this.contractStart),
                new EmployeePosition(this.position),
                new EmployeeState(this.state),
                new EmployeeCurrentSalary(this.currentSalary)
        );
    }

    public EmployeeDBO fromDomain(Employee employee){
        return new EmployeeDBO(
                employee.getEmployeeId().getValue(),
                employee.getEmployeeName().getValue(),
                employee.getEmployeeContractStart().getValue(),
                employee.getEmployeePosition().getValue(),
                employee.getEmployeeState().getValue(),
                employee.getEmployeeCurrentSalary().getValue()
        );
    }
}
