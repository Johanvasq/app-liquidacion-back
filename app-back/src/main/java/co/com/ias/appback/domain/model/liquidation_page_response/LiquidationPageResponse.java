package co.com.ias.appback.domain.model.liquidation_page_response;

import co.com.ias.appback.domain.model.employee_page_response.attributes.PageResponsePaging;
import co.com.ias.appback.domain.model.employee_page_response.attributes.PageResponseRemainingResults;
import co.com.ias.appback.domain.model.employee_page_response.attributes.PageResponseResults;
import co.com.ias.appback.domain.model.liquidation_payment_response.LiquidationPaymentResponse;

import java.util.List;

public class LiquidationPageResponse {
    private final PageResponsePaging pageResponsePaging;
    private final PageResponseResults pageResponseResults;
    private final PageResponseRemainingResults pageResponseRemainingResults;
    private final List<LiquidationPaymentResponse> liquidations;

    public LiquidationPageResponse(PageResponsePaging pageResponsePaging, PageResponseResults pageResponseResults, PageResponseRemainingResults pageResponseRemainingResults, List<LiquidationPaymentResponse> liquidations) {
        this.pageResponsePaging = pageResponsePaging;
        this.pageResponseResults = pageResponseResults;
        this.pageResponseRemainingResults = pageResponseRemainingResults;
        this.liquidations = liquidations;
    }

    public PageResponsePaging getPageResponsePaging() {
        return pageResponsePaging;
    }

    public PageResponseResults getPageResponseResults() {
        return pageResponseResults;
    }

    public PageResponseRemainingResults getPageResponseRemainingResults() {
        return pageResponseRemainingResults;
    }

    public List<LiquidationPaymentResponse> getLiquidations() {
        return liquidations;
    }
}
