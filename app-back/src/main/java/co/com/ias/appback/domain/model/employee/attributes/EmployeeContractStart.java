package co.com.ias.appback.domain.model.employee.attributes;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.springframework.util.Assert.*;

public class EmployeeContractStart {

    private final LocalDate value;

    public EmployeeContractStart(LocalDate value) {
        validation(value);
        this.value = value;
    }

    public LocalDate getValue() {
        return value;
    }


    /**
     * validation of value "Contract Start"
     * @param value
     * @return true or false
     * @throws IllegalArgumentException if the object isn't valid
     */
    private void validation(LocalDate value) throws IllegalArgumentException{
        notNull(value, "the date can't be empty");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/dd/MM");
        try {
            LocalDate.parse(value.toString(), format);
        } catch (DateTimeParseException e) {
            isTrue(false, "the correct format is yyyy/dd/MM");
        }
        isTrue(value.isBefore(LocalDate.of(2015,01,01)),
                "the date can't be before of 01/01/2015");
        isTrue(value.isAfter(LocalDate.of(2015,06,06)),
                "the date can't be after of 06/06/2015");

    }

}
