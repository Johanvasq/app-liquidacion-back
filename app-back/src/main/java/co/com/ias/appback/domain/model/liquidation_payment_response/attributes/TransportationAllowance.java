package co.com.ias.appback.domain.model.liquidation_payment_response.attributes;

public class TransportationAllowance {
    private final Double value;

    public TransportationAllowance(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }
}
