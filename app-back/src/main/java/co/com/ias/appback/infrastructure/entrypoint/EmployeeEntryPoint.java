package co.com.ias.appback.infrastructure.entrypoint;

import co.com.ias.appback.domain.usecase.SaveEmployeeUseCase;
import co.com.ias.appback.infrastructure.entrypoint.dto.EmployeeDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/employee")
public class EmployeeEntryPoint {

    private final SaveEmployeeUseCase saveEmployeeUseCase;

    @PostMapping
    public ResponseEntity<EmployeeDTO> saveEmployee(@Valid @RequestBody EmployeeDTO employeeDTO){
        employeeDTO.setState(true);
        EmployeeDTO rta = new EmployeeDTO().fromDomain(saveEmployeeUseCase.saveEmployee(employeeDTO.toDomain()));
        return ResponseEntity.status(HttpStatus.CREATED).body(rta);

    }



}
