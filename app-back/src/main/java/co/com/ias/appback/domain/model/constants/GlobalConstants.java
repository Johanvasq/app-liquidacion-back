package co.com.ias.appback.domain.model.constants;

import java.time.format.DateTimeFormatter;

public class GlobalConstants {
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy/dd/MM");
    private static final double VACATIONS_PER_MONTH = 1.25;
    private static final double DAYS_PER_MONTH = 30D;
    public static final double VACATIONS_PER_LABORAL_DAY = VACATIONS_PER_MONTH / DAYS_PER_MONTH;
}
