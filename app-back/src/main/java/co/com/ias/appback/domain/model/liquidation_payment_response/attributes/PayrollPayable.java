package co.com.ias.appback.domain.model.liquidation_payment_response.attributes;

public class PayrollPayable {
    private final Double value;


    public PayrollPayable(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }
}
