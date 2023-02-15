package co.com.ias.appback.domain.model.liquidation_payment_response.attributes;

public class InterestSeverancePay {
    private final Double value;

    public InterestSeverancePay(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }
}
