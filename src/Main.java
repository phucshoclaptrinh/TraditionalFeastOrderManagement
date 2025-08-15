import Controller.CustomerManager;
import Controller.FeastMenuManager;
import Model.OrderRepo;
import View.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        CustomerManager customerManager = new CustomerManager();
        FeastMenuManager feastMenuManager = new FeastMenuManager();
        feastMenuManager.LoadFeastMenu("data/feastMenu.csv");
        OrderRepo.InitNextOrderId();

        ConsoleUI UI = new ConsoleUI();
        UI.showUI();
    }
}