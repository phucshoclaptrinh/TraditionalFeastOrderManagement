package View;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

public class ConsoleDialog implements DialogInterface {
    private final Scanner scanner;

    public ConsoleDialog(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String askString(String prompt, Predicate<String> validator, String errorMessage) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (validator == null || validator.test(input)) {
                return input;
            } else {
                System.out.println("    => " + errorMessage);
            }
        }
    }

    @Override
    public int askInt(String prompt, IntPredicate validator, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (validator == null || validator.test(value))
                    return value;
                else
                    System.out.println("    => " + errorMessage);
            } catch (NumberFormatException e) {
                System.out.println("    => " + errorMessage);
            }
        }
    }

    @Override
    public LocalDate askDate(String prompt, String format, Predicate<LocalDate> validator, String errorMessage) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                LocalDate date = LocalDate.parse(input, formatter);
                if (validator == null || validator.test(date)) {
                    return date;
                }
                System.out.println("    => " + errorMessage);
            } catch (DateTimeParseException e) {
                System.out.println("    => " + errorMessage);
            }
        }
    }
}