package Controller;

import Model.Customer;
import Model.CustomerRepo;
import Utils.CustomerValidator;

import java.util.*;

public class CustomerManager {
    private final CustomerRepo customers;

    public CustomerManager() {
        this.customers = new CustomerRepo();
    }

    public Customer FindCustomerByCode(String code) {
        return customers.SearchByCode(code);
    }

    public Customer CreateCustomer(String code, String name, String phoneNumber, String email) {
        Customer newCustomer = new Customer(code, name, phoneNumber, email);
        customers.add(newCustomer);
        return newCustomer;
    }

    public void UpdateCustomer(String code, String name, String phoneNumber, String email) {
        customers.UpdateCustomer(code, name, phoneNumber, email);
    }

    public List<Customer> FindCustomersByName(String key){
        return CustomerRepo.SearchByName(key);
    }

    public Collection<Customer> GetAll() {
        return customers.getAll();
    }
}