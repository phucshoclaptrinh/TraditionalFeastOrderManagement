package View;

import Controller.CustomerManager;
import Controller.FeastMenuManager;
import Controller.OrderManager;
import Model.*;
import Utils.CustomerValidator;
import Utils.OrderValidator;
import Utils.SetMenuValidator;
import Utils.Validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ConsoleUI {
    private static final Scanner scanner = new Scanner(System.in);
    private static CustomerManager customerManager = new CustomerManager();
    private static FeastMenuManager feastMenuManager = new FeastMenuManager();
    private static OrderManager orderManager = new OrderManager();
    private static DialogInterface dialog = new ConsoleDialog(scanner);;
    private boolean isRunning = true;

    public ConsoleUI() {
        CustomerRepo.LoadFromFile();
        OrderRepo.LoadFromFile();
    }

    public void showUI() {
        while (isRunning) {
            System.out.println("\n--- TRADITIONAL FEAST ORDER MANAGEMENT ---");
            System.out.println("1. Register customers.");
            System.out.println("2. Update customer information.");
            System.out.println("3. Search for customer information by name.");
            System.out.println("4. Display feast menus.");
            System.out.println("5. Place a feast order.");
            System.out.println("6. Update order information.");
            System.out.println("7. Save data to file.");
            System.out.println("8. Display Customer or Order lists.");
            System.out.println("Others- Quit.");
            int choice = dialog.askInt("-> Your choice: ", null ,"Invalid choice");

            switch (choice) {
                case 1:
                    repeat(ConsoleUI::RegisterCustomer);
                    break;
                case 2:
                    repeat(ConsoleUI::UpdateCustomer);
                    break;
                case 3:
                    repeat(ConsoleUI::FindCustomersByName);
                    break;
                case 4:
                    DisplayFeastMenu();
                    break;
                case 5:
                    repeat(ConsoleUI::PlaceFeastOrder);
                    break;
                case 6:
                    repeat(ConsoleUI::UpdateOrderInformation);
                    break;
                case 7:
                    SaveDataToFile();
                    break;
                case 8:
                    DisplayListsFromFile();
                    break;
                case 9:
                    System.out.println("Exiting...");
                    this.isRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void repeat(Runnable method) {
        String choice;
        do {
            method.run();
            choice = dialog.askString("Do you want to continue? (Y/N):", Validation::isYN, "Invalid input.");
        } while (choice.trim().equalsIgnoreCase("Y"));
    }

    private static void RegisterCustomer() {
        String code = dialog.askString(Dialogs.PROMPT_CUSTOMER_REGISTER_CODE, CustomerValidator::isValidCode, Dialogs.ERROR_CUSTOMER_CODE);
        String name = dialog.askString(Dialogs.PROMPT_CUSTOMER_REGISTER_NAME, CustomerValidator::isValidName, Dialogs.ERROR_CUSTOMER_NAME);
        String phone = dialog.askString(Dialogs.PROMPT_CUSTOMER_REGISTER_PHONE, CustomerValidator::isValidPhoneNumber, Dialogs.ERROR_CUSTOMER_PHONE);
        String email = dialog.askString(Dialogs.PROMPT_CUSTOMER_REGISTER_EMAIL, CustomerValidator::isValidEmail, Dialogs.ERROR_CUSTOMER_EMAIL);
        Customer customer = customerManager.CreateCustomer(code, name, phone, email);
        System.out.println("Customer registered successfully!");
        System.out.println(customer);
    }

    private static void UpdateCustomer() {
        String code = dialog.askString(Dialogs.PROMPT_CUSTOMER_REGISTER_CODE, CustomerValidator::isValidCode, Dialogs.ERROR_CUSTOMER_CODE);
        Customer selectedUpdate = customerManager.FindCustomerByCode(code);
        if (selectedUpdate == null)
            System.out.println("    => This customer does not exist.");
        else {
            System.out.println("Current info: \n" + selectedUpdate);
            String name = dialog.askString(Dialogs.PROMPT_CUSTOMER_UPDATE_NAME, input -> Validation.isBlank(input) || CustomerValidator.isValidName(input), Dialogs.ERROR_CUSTOMER_NAME);
            String phone = dialog.askString(Dialogs.PROMPT_CUSTOMER_UPDATE_PHONE, input -> Validation.isBlank(input) || CustomerValidator.isValidPhoneNumber(input), Dialogs.ERROR_CUSTOMER_PHONE);
            String email = dialog.askString(Dialogs.PROMPT_CUSTOMER_UPDATE_EMAIL, input -> Validation.isBlank(input) || CustomerValidator.isValidEmail(input), Dialogs.ERROR_CUSTOMER_EMAIL);
            customerManager.UpdateCustomer(code, name, phone, email);
            System.out.println("Updated successfully!");
            System.out.println(selectedUpdate);
        }
    }

    private static void PrintAllCustomerInList(List<Customer> list) {
        System.out.println(Dialogs.SET_OF_MINUS);
        System.out.printf(Dialogs.TABLE_ROW_FORMAT, "Code", "Customer Name", "Phone", "Email");
        System.out.println();
        System.out.println(Dialogs.SET_OF_MINUS);
        for (Customer customer : list) {
            System.out.println(customer);
        }
        System.out.println(Dialogs.SET_OF_MINUS);
    }

    private static void FindCustomersByName() {
        System.out.println(Dialogs.PROMPT_CUSTOMER_FIND_BY_NAME);
        String key = scanner.nextLine().trim();
        List<Customer> result = customerManager.FindCustomersByName(key);
        if (result == null || result.isEmpty()) {
            System.out.println("    => This customer does not exist .");
        } else {
            PrintAllCustomerInList(result);
        }
    }

    private void DisplayFeastMenu() {
        boolean ok = feastMenuManager.LoadFeastMenu("data/feastMenu.csv");
        if (!ok) {
            System.out.println("Cannot read data from feastMenu.csv. Please check it.");
            return;
        }


        List<SetMenu> menus = feastMenuManager.SortMenuByPrice();
        if (menus.isEmpty()) {
            System.out.println("Cannot read data from feastMenu.csv. Please check it.");
            return;
        }

        System.out.println(Dialogs.SET_OF_MINUS);
        System.out.println("List of Set Menus for ordering party:");
        System.out.println(Dialogs.SET_OF_MINUS);

        for (SetMenu m : menus) {
            System.out.printf("%-10s : %s\n", "Code",  m.getCode());
            System.out.printf("%-10s : %s\n", "Name",  m.getName());
            System.out.printf("%-10s : %s\n", "Price", m.getPriceStr());
            System.out.println("Ingredients:");
            String[] items = m.getIngredients().split("\\+");
            for (String it : items) {
                String s = it.trim();
                if (!s.isEmpty()) System.out.println("+ " + s);
            }
            System.out.println(Dialogs.SET_OF_MINUS);
        }
    }

    private static void PrintOneFeastOrder(Customer customer, SetMenu setMenu, Order order) {
        System.out.println(Dialogs.SET_OF_MINUS);
        System.out.printf("Customer order information [Order ID: %d]\n", order.getId());
        System.out.println(Dialogs.SET_OF_MINUS);
        System.out.printf("%-16s : %s\n", "Code", order.getCustomerCode());
        System.out.printf("%-16s : %s\n", "Customer name", customer.getName());
        System.out.printf("%-16s : %s\n", "Phone number", customer.getPhoneNumber());
        System.out.printf("%-16s : %s\n", "Email", customer.getEmail());
        System.out.println(Dialogs.SET_OF_MINUS);
        System.out.printf("%-16s : %s\n", "Code of set menu", setMenu.getCode());
        System.out.printf("%-16s : %s\n", "Set menu name", setMenu.getName());
        System.out.printf("%-16s : %s\n", "Event date", order.getEventDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.printf("%-16s : %d\n", "Number of tables", order.getNoTable());
        System.out.printf("%-16s : %s\n", "Price", setMenu.getPriceStr());
        System.out.println("Ingredients:");
        String[] items = setMenu.getIngredients().split("\\+");
        for (String it : items) {
            String s = it.trim();
            if (!s.isEmpty()) System.out.println("+ " + s);
        }
        System.out.println(Dialogs.SET_OF_MINUS);
        System.out.printf("%-16s : %s\n", "Total cost", String.format(Locale.US,"%,d VND", order.getTotalCost()));
        System.out.println(Dialogs.SET_OF_MINUS);
    }

    private static void PlaceFeastOrder() {
        String customerCode = dialog.askString(Dialogs.PROMPT_CUSTOMER_REGISTER_CODE, CustomerValidator::isValidCode, Dialogs.ERROR_CUSTOMER_CODE);
        String setMenuCode = dialog.askString(Dialogs.PROMPT_SET_MENU_CODE, SetMenuValidator::isContainSetCode, Dialogs.ERROR_SET_MENU_CODE);
        LocalDate eventDate = dialog.askDate(Dialogs.PROMPT_ORDER_DATE, "dd/MM/yyyy", input -> input.isAfter(LocalDate.now()), Dialogs.ERROR_ORDER_DATE);
        int noTable = dialog.askInt(Dialogs.PROMPT_ORDER_TABLES, input -> input > 0, Dialogs.ERROR_ORDER_TABLES);
        long setPrice = feastMenuManager.SearchByCode(setMenuCode).getPrice();

        if (orderManager.CheckDuplicate(customerCode, setMenuCode, eventDate)) {
            System.out.println("    => " +Dialogs.ORDER_DUPLICATE);
        } else {
            Customer customer = customerManager.FindCustomerByCode(customerCode);
            SetMenu setMenu = feastMenuManager.SearchByCode(setMenuCode);
            Order order = orderManager.PlaceOrder(customerCode, setMenuCode, eventDate, noTable, setPrice);
            PrintOneFeastOrder(customer, setMenu, order);
        }
    }

    private static void UpdateOrderInformation() {
        int id = dialog.askInt(Dialogs.PROMPT_ORDER_ID, input -> input > 0, Dialogs.ERROR_ORDER_ID);
        Order order = orderManager.SearchByID(id);
        if (order.getEventDate().isBefore(LocalDate.now())) {
            System.out.println(Dialogs.ERROR_ORDER_UPDATE_DAY);
        }

        String newSetMenuCode = dialog.askString(Dialogs.PROMPT_NEW_SET_MENU_CODE, input -> Validation.isBlank(input) || SetMenuValidator.isContainSetCode(input), Dialogs.ERROR_SET_MENU_CODE);
        if (Validation.isBlank(newSetMenuCode))
            newSetMenuCode = order.getSetMenuCode();
        // ask number of table
        Integer newNoTable = null;
        while (true) {
            String newNoTableStr = dialog.askString(Dialogs.PROMPT_NEW_ORDER_TABLES, input -> Validation.isBlank(input) || Validation.isNumeric(input), Dialogs.ERROR_ORDER_TABLES);

            if (Validation.isBlank(newNoTableStr)) {
                break;
            }

            int value = Integer.parseInt(newNoTableStr.trim());
            if (value <= 0) {
                System.out.println(Dialogs.ERROR_ORDER_TABLES);
                continue;
            }

            newNoTable = value;
            break;
        }
        if (newNoTable == null)
            newNoTable = order.getNoTable();
        // ask date
        String newEventDateStr = dialog.askString(Dialogs.PROMPT_NEW_ORDER_DATE, input -> Validation.isBlank(input) || OrderValidator.isDateFormat(input, "dd/MM/yyyy"), Dialogs.ERROR_ORDER_DATE);
        LocalDate newEventDate = null;
        if (!Validation.isBlank(newEventDateStr)) {
            newEventDate = LocalDate.parse(
                    newEventDateStr.trim(),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy")
            );
        }
        if (newEventDate == null)
            newEventDate = order.getEventDate();

        order = orderManager.UpdateOrder(id, newSetMenuCode, newNoTable, newEventDate);
        Customer customer = customerManager.FindCustomerByCode(order.getCustomerCode());
        SetMenu setMenu = feastMenuManager.SearchByCode(order.getSetMenuCode());
        System.out.println("Updated order information:");
        PrintOneFeastOrder(customer, setMenu, order);
    }

    private void SaveDataToFile() {
        System.out.println("== Save data to file ==");
        System.out.println("1: Customers");
        System.out.println("2: Orders");
        System.out.println("Others: Cancel");
        System.out.print("Your choice: ");

        String opt = scanner.nextLine().trim();
        switch (opt) {
            case "1": {
                boolean ok = DataStore.SaveCustomers(customerManager.GetAll(), DataStore.FILE_CUSTOMERS);
                System.out.println(ok
                        ? "Customer data has been successfully saved to customers.dat"
                        : "Error saving customers.");
                break;
            }
            case "2": {
                boolean ok = DataStore.SaveOrders(orderManager.GetAll(), DataStore.FILE_ORDERS);
                System.out.println(ok
                        ? "Order data has been successfully saved to feast_order_service.dat"
                        : "Error saving orders.");
                break;
            }
            default:
                System.out.println("Canceled.");
        }
    }

    private void DisplayListsFromFile() {
        System.out.println("== Display lists ==");
        System.out.println("1: Customers");
        System.out.println("2: Orders");
        System.out.print("Your choice: ");

        String opt = scanner.nextLine().trim();
        switch (opt) {
            case "1":
                DisplayCustomersFromFile();
                break;
            case "2":
                DisplayOrdersFromFile();
                break;
        }
    }

    private void DisplayCustomersFromFile() {
        List<Customer> list = DataStore.LoadCustomers(DataStore.FILE_CUSTOMERS);
        if (list == null || list.isEmpty()) {
            System.out.println(Dialogs.ERROR_NO_DATA_FOUND);
            return;
        }
        list.sort(CustomerRepo.CODE_NAME_PHONE_EMAIL);
        PrintAllCustomerInList(list);
    }

    private void DisplayOrdersFromFile() {
        feastMenuManager.LoadFeastMenu("data/feastMenu.csv");
        List<Order> orders = DataStore.LoadOrders(DataStore.FILE_ORDERS);
        if (orders == null || orders.isEmpty()) {
            System.out.println(Dialogs.ERROR_NO_DATA_FOUND);
            return;
        }
        List<Customer> custList = DataStore.LoadCustomers(DataStore.FILE_CUSTOMERS);

        Map<String, Customer> custMap = new HashMap<>();
        if (custList != null) for (Customer c : custList) custMap.put(c.getCode(), c);

        orders.sort(OrderRepo.ID_DATE_CID_SETMENU_PRICE_NOTABLE_TOTALCOST);

        for (Order o : orders) {
            Customer customer = custMap.get(o.getCustomerCode());
            SetMenu setMenu   = feastMenuManager.SearchByCode(o.getSetMenuCode());
            PrintOneFeastOrder(customer, setMenu, o);
        }
    }
}