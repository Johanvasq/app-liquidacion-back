package co.com.ias.appback.domain.model.liquidation_payment_response.attributes;

public class ServiceBonus {
    private final Double value;

    public ServiceBonus(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }
}
