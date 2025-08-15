package Model;

import Utils.CustomerValidator;

import java.util.*;

public class CustomerRepo {
    private static final Map<String, Customer> customers = new HashMap<>();

    public static void LoadFromFile() {
        java.util.List<Customer> list = Model.DataStore.LoadCustomers(Model.DataStore.FILE_CUSTOMERS);
        customers.clear();
        if (list != null) {
            for (Customer c : list) customers.put(c.getCode(), c);
        }
    }

    public Customer SearchByCode(String code) {
        return customers.get(code);
    }

    public void add(Customer customer) {
        customers.put(customer.getCode(), customer);
    }

    public void UpdateCustomer(String code, String name, String phoneNumber, String email) {
        Customer customer = SearchByCode(code);
        if (CustomerValidator.isValidCustomer(customer)) {
            customer.setName(name);
            customer.setPhoneNumber(phoneNumber);
            customer.setEmail(email);
        }
        else
            System.out.println("This customer does not exist .");
    }

    public static final Comparator<Customer> NAME_CODE_PHONE_EMAIL = (c1, c2) -> {
        int result = c1.getName().toLowerCase().compareTo(c2.getName().toLowerCase());
        if (result != 0) return result;
        result = c1.getCode().compareTo(c2.getCode());
        if (result != 0) return result;
        result = c1.getPhoneNumber().compareTo(c2.getPhoneNumber());
        if (result != 0) return result;
        result = c1.getEmail().compareTo(c2.getEmail());
        return result;
    };

    public static List<Customer> SearchByName(String key) {
        ArrayList<Customer> list = new ArrayList<Customer>();
        if (key == null) return list;
        String k = key.trim().toLowerCase();
        for (Customer c : customers.values()) {
            String name = c.getName();
            if (name != null && name.toLowerCase().contains(k)) {
                list.add(c);
            }
        }
        list.sort(NAME_CODE_PHONE_EMAIL);
        return list;
    }

    public Collection<Customer> getAll() {
        return customers.values();
    }

    public static final Comparator<Customer> CODE_NAME_PHONE_EMAIL = (c1, c2) -> {
        int result = c1.getCode().compareTo(c2.getCode());
        if (result != 0) return result;
        result = c1.getName().toLowerCase().compareTo(c2.getName().toLowerCase());
        if (result != 0) return result;
        result = c1.getPhoneNumber().compareTo(c2.getPhoneNumber());
        if (result != 0) return result;
        result = c1.getEmail().compareTo(c2.getEmail());
        return result;
    };
}