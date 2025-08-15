package Model;

import java.io.Serializable;
import java.time.LocalDate;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String customerCode;
    private String setMenuCode;
    private LocalDate eventDate;
    private int noTable;
    private long unitPrice;
    private long totalCost;

    public Order() {
    }

    public Order(int id, String customerCode, String setMenuCode, LocalDate eventDate, int noTable, long unitPrice, long totalCost) {
        this.id = id;
        this.customerCode = customerCode;
        this.setMenuCode = setMenuCode;
        this.eventDate = eventDate;
        this.noTable = noTable;
        this.unitPrice = unitPrice;
        this.totalCost = unitPrice * noTable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getSetMenuCode() {
        return setMenuCode;
    }

    public void setSetMenuCode(String setMenuCode) {
            this.setMenuCode = setMenuCode.trim();
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public int getNoTable() {
        return noTable;
    }

    public void setNoTable(int noTable) {
        this.noTable = noTable;
    }

    public long getUnitPrice() {
        return unitPrice;
    }

    public long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(long totalCost) {
        this.totalCost = totalCost;
    }
}