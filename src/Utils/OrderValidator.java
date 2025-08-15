package Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class OrderValidator extends Validation {
    public static boolean isDateFormat(String dateStr, String pattern) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        try {
            LocalDate.parse(dateStr.trim(), formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

}