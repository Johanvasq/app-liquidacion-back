package co.com.ias.appback.domain.model.liquidation_payment_request.attributes;


import org.springframework.util.Assert;

public class LRWithdrawalReason {
    private final String value;

    public LRWithdrawalReason(String value) {
        validation(value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private void validation(String value) throws IllegalArgumentException{
        Assert.isTrue(
                (value.equalsIgnoreCase("RETIRO VOLUNTARIO") ||
                value.equalsIgnoreCase("RETIRO JUSTIFICADO") ||
                value.equalsIgnoreCase("RETIRO INJUSTIFICADO")),
                """
                        please provide one of the following options:\s
                        - RETIRO VOLUNTARIO
                        - RETIRO JUSTIFICADO
                        - RETIRO INJUSTIFICADO
                        """
        );
    }
}
