package co.com.ias.appback.infrastructure.adapters.jpa.entity;

import co.com.ias.appback.domain.model.salary_history.SalaryHistory;
import co.com.ias.appback.domain.model.salary_history.attributes.SalaryHistoryModificationDate;
import co.com.ias.appback.domain.model.salary_history.attributes.SalaryHistoryUpdatedSalary;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "salary_history")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalaryHistoryDBO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeDBO employeeDBO;

    private Double updatedSalary;

    private LocalDate modificationDate;


    public SalaryHistoryDBO(EmployeeDBO employeeDBO, Double updatedSalary, LocalDate modificationDate) {
        this.employeeDBO = employeeDBO;
        this.updatedSalary = updatedSalary;
        this.modificationDate = modificationDate;
    }

    public  SalaryHistory toDomain(){
        return new SalaryHistory(
                this.employeeDBO.toDomain(),
                new SalaryHistoryUpdatedSalary(this.updatedSalary),
                new SalaryHistoryModificationDate(this.modificationDate)
        );
    }

    public SalaryHistoryDBO fromDomain(SalaryHistory salaryHistory){
        return new SalaryHistoryDBO(
                new EmployeeDBO().fromDomain(salaryHistory.getEmployee()),
                salaryHistory.getShUpdatedSalary().getValue(),
                salaryHistory.getShModificationDate().getValue()
        );
    }



}
