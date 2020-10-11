package model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Check {

    public enum Status {
        newMade, modified, confirmed, reported
    }

    private int id;

    private double totalPrice;
    private LocalDateTime localDate;
    private Status status;

    private User cashier;
    private List<Product> products = new ArrayList<>();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public LocalDateTime getLocalDate() { return localDate; }
    public void setLocalDate(LocalDateTime localDate) { this.localDate = localDate; }

    public Status getStatus() {
        if (status == null) {
            return Status.newMade;
        }
        return status;
    }
    public void setStatus(Status status) { this.status = status; }

    public User getCashier() { return cashier; }
    public void setCashier(User cashier) { this.cashier = cashier; }

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Check check = (Check) o;
        return id == check.id &&
                localDate.equals(check.localDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, localDate);
    }
}
