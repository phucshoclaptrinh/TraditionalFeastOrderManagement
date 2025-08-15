package View;

import java.time.LocalDate;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

public interface DialogInterface {
    String askString(String prompt, Predicate<String> validator, String errorMessage);
    int askInt(String prompt, IntPredicate validator, String errorMessage);
    LocalDate askDate(String prompt, String format, Predicate<LocalDate> validator, String errorMessage);
}