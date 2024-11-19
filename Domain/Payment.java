package Domain;

import Repository.EntityParser;
import Repository.Identifiable;

import java.time.LocalDateTime;

/**
 * Represents a payment made by a user for a service or product.
 * Implements {@link Identifiable} to provide a unique identifier for the payment and
 * {@link EntityParser} for converting between objects and CSV strings.
 */
public class Payment implements Identifiable, EntityParser {
    private int id;
    private double amount;
    private LocalDateTime date;
    private User user;
    private String paymentMethod;

    /**
     * Constructs a Payment instance with the specified details.
     *
     * @param id            the unique identifier of the payment
     * @param amount        the amount of the payment
     * @param date          the date and time of the payment
     * @param user          the user who made the payment
     * @param paymentMethod the method of payment (e.g., "CASH", "CARD")
     */
    public Payment(int id, double amount, LocalDateTime date, User user, String paymentMethod) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.user = user;
        this.paymentMethod = paymentMethod;
    }

    /**
     * Default constructor for creating an empty {@code Payment} object.
     */
    public Payment() {

    }

    /**
     * Gets the ID of the payment.
     *
     * @return the payment ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the payment.
     *
     * @param id the new payment ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the amount of the payment.
     *
     * @return the payment amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the payment.
     *
     * @param amount the new payment amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Gets the date and time of the payment.
     *
     * @return the payment date
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Sets the date and time of the payment.
     *
     * @param date the new payment date
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Gets the user who made the payment.
     *
     * @return the user who made the payment
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user who made the payment.
     *
     * @param user the new user associated with the payment
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the payment method used.
     *
     * @return the payment method (e.g., "CASH", "CARD")
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Sets the payment method used.
     *
     * @param paymentMethod the new payment method (e.g., "CASH", "CARD")
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Converts the {@code Payment} object into a comma-separated value (CSV) string.
     *
     * @return a CSV string representation of the {@code Payment} object.
     */
    @Override
    public String toCSV() {
        return id + "," + amount + "," + date + "," + user.getId() + "," + paymentMethod;
    }

    /**
     * Parses a {@code Payment} object from a CSV string.
     *
     * @param csv the CSV string containing the {@code Payment} data.
     * @return a {@code Payment} object populated with the parsed data.
     */
    @Override
    public Payment parseFromCSV(String csv) {
        String[] parts = csv.split(",", 5);

        int id = Integer.parseInt(parts[0]);
        double amount = Double.parseDouble(parts[1]);
        LocalDateTime date = LocalDateTime.parse(parts[2]);

        String userCSV = parts[3];
        User user = new User().parseFromCSV(userCSV);

        String paymentMethod = parts[4];

        Payment payment = new Payment(id, amount, date, user, paymentMethod);
        payment.setId(id);
        return payment;
    }
}
