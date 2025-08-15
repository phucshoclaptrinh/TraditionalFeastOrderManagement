package View;

public class Dialogs {
    // ERROR
    public static final String ERROR_CUSTOMER_CODE = "Invalid Format. Code must start with C/G/K and have 4 digits.";
    public static final String ERROR_CUSTOMER_NAME = "Invalid Format. Name is required and must be 2-25 characters long.";
    public static final String ERROR_CUSTOMER_PHONE = "Invalid Format. Phone must be 10 digits and start with 0.";
    public static final String ERROR_CUSTOMER_EMAIL = "Invalid Format. Please enter a valid email address.";
    public static final String ERROR_SET_MENU_CODE = "This set code is not in the feast menu.";
    public static final String ERROR_ORDER_TABLES = "The number of table must be a positive integer.";
    public static final String ERROR_ORDER_DATE = "The date must be in the future and in the right format.";
    public static final String ERROR_ORDER_ID = "The order does not exist.";
    public static final String ERROR_ORDER_UPDATE_DAY = "Cannot update an order whose event date occurred before the current date";
    public static final String ERROR_NO_DATA_FOUND = "No data in the system.";
    // REGISTER
    public static final String PROMPT_CUSTOMER_REGISTER_CODE = "Enter customer code (e.g., C1234): ";
    public static final String PROMPT_CUSTOMER_REGISTER_NAME = "Enter name (2-25 characters): ";
    public static final String PROMPT_CUSTOMER_REGISTER_PHONE = "Enter phone number (10 digits, start with 0): ";
    public static final String PROMPT_CUSTOMER_REGISTER_EMAIL = "Enter email: ";
    // UPDATE
    public static final String PROMPT_CUSTOMER_UPDATE_NAME = "Enter new name (leave blank to keep): ";
    public static final String PROMPT_CUSTOMER_UPDATE_PHONE = "Enter new phone (leave blank to keep): ";
    public static final String PROMPT_CUSTOMER_UPDATE_EMAIL = "Enter new email (leave blank to keep): ";
    // FIND BY NAME
    public static final String PROMPT_CUSTOMER_FIND_BY_NAME = "Enter keyword: ";
    // SET MENU
    public static final String PROMPT_SET_MENU_CODE = "Enter set menu code: ";
    public static final String PROMPT_NEW_SET_MENU_CODE = "Enter new set menu code: ";
    // ORDER
    public static final String PROMPT_ORDER_ID = "Enter the order ID for update:";
    public static final String PROMPT_ORDER_TABLES = "Enter number of tables: ";
    public static final String PROMPT_NEW_ORDER_TABLES = "Enter new number of tables: ";
    public static final String PROMPT_ORDER_DATE = "Enter the event date (dd/MM/yyyy): ";
    public static final String PROMPT_NEW_ORDER_DATE = "Enter new event date (dd/MM/yyyy): ";
    public static final String ORDER_DUPLICATE = "Duplicate data !";
    // DECOR
    public static final String SET_OF_MINUS = "-------------------------------------------------------------------------";
    public static final String TABLE_ROW_FORMAT = "%-10s | %-25s | %-15s | %-25s";
}