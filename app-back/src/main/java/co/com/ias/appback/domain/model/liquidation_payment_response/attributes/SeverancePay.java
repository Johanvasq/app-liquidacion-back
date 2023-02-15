package co.com.ias.appback.domain.model.liquidation_payment_response.attributes;

public class SeverancePay {
    private final Double value;

    public SeverancePay(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }
}
