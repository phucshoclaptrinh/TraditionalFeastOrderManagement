// CustomerValidator.java
package Utils;
import Model.Customer;

import java.util.regex.Pattern;

public class CustomerValidator extends Validation {
    public static boolean isValidCode(String code) {
        return !Validation.isBlank(code) && Pattern.matches("^[CGK]\\d{4}$", code);
    }

    public static boolean isValidName(String name) {
        return !Validation.isBlank(name) && name.length() >= 2 && name.length() <= 25;
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return !Validation.isBlank(phoneNumber) && Pattern.matches("^0\\d{9}$", phoneNumber);
    }

    public static boolean isValidEmail(String email) {
        return !Validation.isBlank(email) && Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", email);
    }

    public static boolean isValidCustomer(Customer customer) {
        return customer != null && isValidCode(customer.getCode()) && isValidEmail(customer.getEmail()) && isValidName(customer.getName()) && isValidPhoneNumber(customer.getPhoneNumber());
    }
}