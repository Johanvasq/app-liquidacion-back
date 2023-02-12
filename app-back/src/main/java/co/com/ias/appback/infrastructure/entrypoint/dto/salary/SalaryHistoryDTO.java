package co.com.ias.appback.infrastructure.entrypoint.dto.salary;

import co.com.ias.appback.domain.model.salaryhistory.SalaryHistory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalaryHistoryDTO {


    private Double updatedSalary;

    private LocalDate modificationDate;

    public SalaryHistoryDTO fromDomain(SalaryHistory salaryHistory){
        return new SalaryHistoryDTO(
                salaryHistory.getShUpdatedSalary().getValue(),
                salaryHistory.getShModificationDate().getValue()
        );
    }

}
