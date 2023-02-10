package co.com.ias.appback.infrastructure.entrypoint;

import co.com.ias.appback.domain.model.constants.GlobalConstants;
import co.com.ias.appback.domain.usecase.employee.FindEmployeeByIdUseCase;
import co.com.ias.appback.domain.usecase.employee.SaveEmployeeUseCase;
import co.com.ias.appback.domain.usecase.employee.UpdateEmployeeUseCase;
import co.com.ias.appback.infrastructure.entrypoint.dto.employee.EmployeeDTO;
import co.com.ias.appback.infrastructure.entrypoint.dto.employee.UpdateEmployeeDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping("/employee")
@CrossOrigin(origins = "*")
public class EmployeeEntryPoint {

    private final SaveEmployeeUseCase saveEmployeeUseCase;
    private final FindEmployeeByIdUseCase findEmployeeByIdUseCase;

    private final UpdateEmployeeUseCase updateEmployeeUseCase;


    @PostMapping
    public ResponseEntity<EmployeeDTO> saveEmployee(@Valid @RequestBody EmployeeDTO employeeDTO){
        employeeDTO.setState(true);
        EmployeeDTO rta = new EmployeeDTO().fromDomain(saveEmployeeUseCase.saveEmployee(employeeDTO.toDomain()));
        return ResponseEntity.status(HttpStatus.CREATED).body(rta);

    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> findEmployee(@PathVariable @Size(
            min = 7, max = 15, message = "the size of the id is only between 7 and 15 digits"
    ) String id){
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(
                        new EmployeeDTO().fromDomain(findEmployeeByIdUseCase.findEmployeeById(id)
                        ));
    }

    @PutMapping
    public ResponseEntity<EmployeeDTO> updateEmployee(@Valid @RequestBody UpdateEmployeeDTO updateEmployeeDTO){
        return ResponseEntity.status(HttpStatus.OK).body(new EmployeeDTO().fromDomain(updateEmployeeUseCase.updateEmployee(
                updateEmployeeDTO.getId(),
                updateEmployeeDTO.getPosition(),
                updateEmployeeDTO.getCurrentSalary(),
                LocalDate.parse(updateEmployeeDTO.getModificationDate(), GlobalConstants.DATE_FORMAT)
        )));


    }



}
