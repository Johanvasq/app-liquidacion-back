package co.com.ias.appback.infrastructure.entrypoint;


import co.com.ias.appback.domain.model.constants.GlobalConstants;
import co.com.ias.appback.domain.usecase.liquidation.FindAllLiquidationUseCase;
import co.com.ias.appback.domain.usecase.liquidation.FindLiquidationByIdUseCase;
import co.com.ias.appback.domain.usecase.liquidation.SaveLiquidationUseCase;
import co.com.ias.appback.infrastructure.entrypoint.dto.liquidation_page_response.LiquidationPageResponseDTO;
import co.com.ias.appback.infrastructure.entrypoint.dto.liquidation_page_response.PaginationLiquidationDTO;
import co.com.ias.appback.infrastructure.entrypoint.dto.liquidation_request.LiquidationRequestDTO;
import co.com.ias.appback.infrastructure.entrypoint.dto.liquidation_response.LiquidationPaymentResponseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping("/employee/liquidation")
@CrossOrigin(origins = "*")
public class LiquidationEntryPoint {

    private FindAllLiquidationUseCase findAllLiquidationUseCase;
    private FindLiquidationByIdUseCase findLiquidationByIdUseCase;
    private SaveLiquidationUseCase saveLiquidationUseCase;

    @PostMapping
    public ResponseEntity<LiquidationPaymentResponseDTO> saveLiquidation(@Valid @RequestBody LiquidationRequestDTO liquidationRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new LiquidationPaymentResponseDTO()
                        .fromDomain(saveLiquidationUseCase.saveEmployeeLiquidation(liquidationRequestDTO.toDomain()))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<LiquidationPaymentResponseDTO> findLiquidationByEmployeeId(@PathVariable @Size(
            min = 7, max = 15, message = "the size of the id is only between 7 and 15 digits"
    ) String id){
        return ResponseEntity.status(HttpStatus.FOUND).body(
                new LiquidationPaymentResponseDTO()
                        .fromDomain(findLiquidationByIdUseCase.findLiquidationById(id))
        );
    }

    @PostMapping("/date-range")
    public ResponseEntity<LiquidationPageResponseDTO> findAllLiquidation(@Valid @RequestBody PaginationLiquidationDTO paginationLiquidationDTO) {
        LocalDate minDate = paginationLiquidationDTO.getMinRangeDate() != null ?
                LocalDate.parse(paginationLiquidationDTO.getMinRangeDate(), GlobalConstants.DATE_FORMAT) : null;

        LocalDate maxDate = paginationLiquidationDTO.getMaxRangeDate() != null ?
                LocalDate.parse(paginationLiquidationDTO.getMaxRangeDate(), GlobalConstants.DATE_FORMAT) : null;

        return ResponseEntity.status(HttpStatus.FOUND).body(
                new LiquidationPageResponseDTO()
                        .fromDomain(findAllLiquidationUseCase.findAllByDateRange(
                                minDate,
                                maxDate,
                                paginationLiquidationDTO.getRecordsPerPage(),
                                paginationLiquidationDTO.getPage()))
        );

    }
}
