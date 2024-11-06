package Domain;

import java.time.LocalDateTime;

public class Payment {
    private int id;
    private double amount;
    private LocalDateTime date;
    private User user;
    private String paymentMethod;

    public Payment(int id, double amount, LocalDateTime date, User user, String paymentMethod){
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.user = user;
        this.paymentMethod = paymentMethod;
    }
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

}