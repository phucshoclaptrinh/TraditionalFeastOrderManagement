package Utils;

public class Validation {
    public static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static boolean isYN(String s) {
        return s.equalsIgnoreCase("Y") || s.equalsIgnoreCase("N");
    }

    public static boolean isNumeric(String s) {
        if (s == null || s.trim().isEmpty()) return true;
        try {
            Integer.parseInt(s.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}