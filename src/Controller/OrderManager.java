package Controller;

import Model.Customer;
import Model.Order;
import Model.OrderRepo;

import java.time.LocalDate;
import java.util.Collection;

public class OrderManager {
    public Order PlaceOrder(String customerCode, String setMenuCode, LocalDate eventDate, int noTable, long unitPrice) {
        return OrderRepo.PlaceOrder(customerCode, setMenuCode, eventDate, noTable, unitPrice);
    }

    public boolean CheckDuplicate(String code, String set, LocalDate date) {
        return OrderRepo.isDuplicate(code, set, date);
    }

    public Order SearchByID(int id) {
        return OrderRepo.SearchByID(id);
    }

    public Order UpdateOrder(int id, String newSetMenuCode, int newNoTable, LocalDate newEventDate) {
        return OrderRepo.UpdateOrder(id, newSetMenuCode, newNoTable, newEventDate);
    }

    public Collection<Order> GetAll() {
        return OrderRepo.getAll();
    }
}