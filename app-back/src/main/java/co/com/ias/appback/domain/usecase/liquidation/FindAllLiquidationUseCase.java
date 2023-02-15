package co.com.ias.appback.domain.usecase.liquidation;

import co.com.ias.appback.domain.model.employee_page_response.attributes.PageResponsePaging;
import co.com.ias.appback.domain.model.employee_page_response.attributes.PageResponseRemainingResults;
import co.com.ias.appback.domain.model.employee_page_response.attributes.PageResponseResults;
import co.com.ias.appback.domain.model.gateway.liquidation.IFindAllLiquidationGateway;
import co.com.ias.appback.domain.model.liquidation_page_response.LiquidationPageResponse;
import co.com.ias.appback.domain.model.liquidation_payment_response.LiquidationPaymentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public class FindAllLiquidationUseCase {

    private final IFindAllLiquidationGateway iFindAllLiquidationGateway;

    public FindAllLiquidationUseCase(IFindAllLiquidationGateway iFindAllLiquidationGateway) {
        this.iFindAllLiquidationGateway = iFindAllLiquidationGateway;
    }

    public LiquidationPageResponse findAllByDateRange(LocalDate minDate,
                                                      LocalDate maxDate,
                                                      Integer recordsPerPage,
                                                      Integer page){
        LocalDate selectedMinDate = minDate != null ? minDate : LocalDate.of(2015,1,1);
        LocalDate selectedMaxDate = maxDate != null ? maxDate : LocalDate.of(2100,1,1);
        if(selectedMinDate.isAfter(selectedMaxDate)){
            throw new IllegalArgumentException("please provide correct date range");
        }
        int selectedRecordsPerPage = recordsPerPage != null ? recordsPerPage : 5;
        Pageable pageable = PageRequest.of(page - 1, selectedRecordsPerPage);

        Page<LiquidationPaymentResponse> rta = iFindAllLiquidationGateway
                .findLiquidationByDateRange(selectedMinDate, selectedMaxDate, pageable);

        return new LiquidationPageResponse(
                new PageResponsePaging(rta.getTotalPages()),
                new PageResponseResults((int) rta.getTotalElements()),
                new PageResponseRemainingResults((int) rta.getTotalElements() - (rta.getNumber() * rta.getSize())),
                rta.getContent()
        );
    }
}
