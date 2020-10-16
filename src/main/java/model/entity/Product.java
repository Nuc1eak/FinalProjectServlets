package model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Product {

    public enum Measure {
        kg, pc
    }

    private int id;

    private String code;
    private String name;
    private String price;
    private String amount;
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

    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public List<Check> getChecks() { return checks; }
    public void setChecks(List<Check> checks) { this.checks = checks; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Integer.parseInt(code) == Integer.parseInt(product.code) &&
                name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
