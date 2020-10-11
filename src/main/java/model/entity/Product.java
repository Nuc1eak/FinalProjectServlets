package model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Product {

    public enum Measure {
        kg, pc
    }

    private int id;

    private int code;
    private String name;
    private int price;
    private int amount;
    private Measure measure;

    private List<Check> checks = new ArrayList<>();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Measure getMeasure() {
        if (measure == null) {
            return Measure.kg;
        }
        return measure;
    }
    public void setMeasure(Measure measure) { this.measure = measure; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }

    public List<Check> getChecks() { return checks; }
    public void setChecks(List<Check> checks) { this.checks = checks; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return code == product.code &&
                name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
