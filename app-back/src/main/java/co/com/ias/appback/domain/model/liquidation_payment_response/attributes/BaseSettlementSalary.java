package co.com.ias.appback.domain.model.liquidation_payment_response.attributes;

public class BaseSettlementSalary {

    private final Double value;

    public BaseSettlementSalary(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }
}
