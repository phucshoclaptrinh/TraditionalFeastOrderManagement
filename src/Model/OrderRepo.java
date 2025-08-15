package Model;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderRepo {
    private static final Map<String, Order> orders = new HashMap<>();
    private static int nextOrderID = 1;

    public static void InitNextOrderId() {
        nextOrderID = DataStore.LoadNextOrderId();
    }

    private static int GetNextId() {
        int id = nextOrderID++;
        DataStore.SaveNextOrderId(nextOrderID);
        return id;
    }

    public static void LoadFromFile() {
        List<Order> list = DataStore.LoadOrders(Model.DataStore.FILE_ORDERS);
        orders.clear();
        int max = 0;
        if (list != null) {
            for (Order o : list) {
                orders.put(Integer.toString(o.getId()), o);
                if (o.getId() > max) max = o.getId();
            }
        }
        int seq = Model.DataStore.LoadNextOrderId();
        nextOrderID = Math.max(seq, max + 1);
        Model.DataStore.SaveNextOrderId(nextOrderID);
    }

    public static Order PlaceOrder(String customerCode, String setMenuCode, LocalDate eventDate, int noTable, long unitPrice) {
        long totalCost = 0;
        Order order = new Order(GetNextId(), customerCode, setMenuCode, eventDate, noTable, unitPrice, totalCost);
        orders.put(Integer.toString(order.getId()), order);
        return order;
    }

    public static boolean isDuplicate(String ccode, String cset, LocalDate cdate) {
        for (Order order : orders.values()) {
            if (order.getCustomerCode().equalsIgnoreCase(ccode) && order.getSetMenuCode().equalsIgnoreCase(cset) && order.getEventDate().equals(cdate))
                return true;
        }
        return false;
    }

    public static Order SearchByID(int id) {
        return orders.get(Integer.toString(id));
    }

    public static Order UpdateOrder(int id, String newSetMenuCode, int newNoTable, LocalDate newEventDate) {
        Order order = SearchByID(id);
        order.setSetMenuCode(newSetMenuCode);
        order.setNoTable(newNoTable);
        order.setEventDate(newEventDate);
        long unitPrice = FeastMenu.SearchByCode(newSetMenuCode).getPrice();
        order.setTotalCost(unitPrice * order.getNoTable());
        return order;
    }

    public static java.util.Collection<Order> getAll() {
        return orders.values();
    }

    public static final Comparator<Order> ID_DATE_CID_SETMENU_PRICE_NOTABLE_TOTALCOST = (o1, o2) -> {
        int result = Integer.compare(o1.getId(), o2.getId());
        if (result != 0) return result;
        result = o1.getEventDate().compareTo(o2.getEventDate());
        if (result != 0) return result;
        result = o1.getCustomerCode().compareTo(o2.getCustomerCode());
        if (result != 0) return result;
        result = o1.getSetMenuCode().compareTo(o2.getSetMenuCode());
        if (result != 0) return result;
        result = Long.compare(o1.getUnitPrice(), o2.getUnitPrice());
        if (result != 0) return result;
        result = Integer.compare(o1.getNoTable(), o2.getNoTable());
        if (result != 0) return result;
        result = Long.compare(o1.getTotalCost(), o2.getTotalCost());
        return result;
    };
}