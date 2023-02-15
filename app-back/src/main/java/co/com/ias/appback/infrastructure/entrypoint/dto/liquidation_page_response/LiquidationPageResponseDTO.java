package co.com.ias.appback.infrastructure.entrypoint.dto.liquidation_page_response;

import co.com.ias.appback.domain.model.liquidation_page_response.LiquidationPageResponse;
import co.com.ias.appback.infrastructure.entrypoint.dto.liquidation_response.LiquidationPaymentResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LiquidationPageResponseDTO {

    private Integer totalPages;
    private Integer results;
    private Integer remainingResults;
    private List<LiquidationPaymentResponseDTO> liquidationPaymentResponseDTO;

    public LiquidationPageResponseDTO fromDomain(LiquidationPageResponse liquidationPageResponse){
        return new LiquidationPageResponseDTO(
                liquidationPageResponse.getPageResponsePaging().value(),
                liquidationPageResponse.getPageResponseResults().value(),
                liquidationPageResponse.getPageResponseRemainingResults().value(),
                liquidationPageResponse.getLiquidations()
                        .stream()
                        .map(liquidation -> new LiquidationPaymentResponseDTO().fromDomain(liquidation))
                        .toList()
        );
    }
}
