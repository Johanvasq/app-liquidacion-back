package co.com.ias.appback.infrastructure.entrypoint;


import co.com.ias.appback.domain.usecase.salary.FindSalaryEmployeeUseCase;
import co.com.ias.appback.infrastructure.entrypoint.dto.salary.SalaryHistoryDTO;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/salary")
@CrossOrigin(origins = "*")
public class SalaryHistoryEntryPoint {

    private final FindSalaryEmployeeUseCase findSalaryEmployeeUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<List<SalaryHistoryDTO>> findSalaryHistoryByEmployeeId(
            @PathVariable @Size(min = 7, max = 15, message = "the size of the id is only between 7 and 15 digits"
            ) String id){
        return ResponseEntity.status(HttpStatus.FOUND).body(
                findSalaryEmployeeUseCase.findSalaryByEmployeeId(id)
                        .stream()
                        .map(salaryHistory -> new SalaryHistoryDTO().fromDomain(salaryHistory))
                        .toList()
        );
    }
}
