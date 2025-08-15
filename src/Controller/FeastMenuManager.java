package Controller;

import Model.FeastMenu;
import Model.SetMenu;

import java.util.List;

public class FeastMenuManager {
    public boolean LoadFeastMenu(String csvPath) {
        return FeastMenu.LoadFeastMenu(csvPath);
    }

    public boolean IsExist() {
        return FeastMenu.IsExist();
    }

    public List<SetMenu> SortMenuByPrice() {
        return FeastMenu.getMenusSortedByPrice();
    }

    public SetMenu SearchByCode(String code) {
        return FeastMenu.SearchByCode(code);
    }
}