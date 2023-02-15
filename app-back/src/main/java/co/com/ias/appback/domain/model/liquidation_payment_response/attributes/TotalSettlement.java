package co.com.ias.appback.domain.model.liquidation_payment_response.attributes;

public class TotalSettlement {

    private final Double value;

    public TotalSettlement(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }
}
