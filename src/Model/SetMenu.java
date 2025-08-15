package Model;

import java.io.Serializable;

public class SetMenu implements Serializable {
    private String code;
    private String name;
    private long price;
    private String priceStr;
    private String ingredients;

    public SetMenu() {
    }

    public SetMenu(String code, String name, long price, String priceStr, String ingredients) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.priceStr = priceStr;
        this.ingredients = ingredients;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getPriceStr() {
        return priceStr;
    }

    public void setPriceStr(String priceStr) {
        this.priceStr = priceStr;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}