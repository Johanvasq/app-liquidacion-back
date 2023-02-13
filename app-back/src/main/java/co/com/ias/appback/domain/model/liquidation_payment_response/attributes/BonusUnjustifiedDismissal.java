package co.com.ias.appback.domain.model.liquidation_payment_response.attributes;

public class BonusUnjustifiedDismissal {
    private final Double value;

    public BonusUnjustifiedDismissal(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }
}
