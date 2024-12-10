package Domain;

import Repository.EntityParser;
import Repository.Identifiable;
import Exception.*;
import java.time.LocalDateTime;

/**
 * Represents a payment made by a user for a service or product.
 * Implements {@link Identifiable} to provide a unique identifier for the payment and
 * {@link EntityParser} for converting between objects and CSV strings.
 */
public class Payment implements Identifiable {
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
     * @throws ValidationException if any parameter is invalid
     */
    public Payment(int id, double amount, LocalDateTime date, User user, String paymentMethod) {
        if (amount <= 0) {
            throw new ValidationException("Payment amount must be greater than 0.");
        }
        if (date == null || date.isAfter(LocalDateTime.now())) {
            throw new ValidationException("Payment date cannot be null or in the future.");
        }
        if (user == null) {
            throw new ValidationException("User cannot be null.");
        }
        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            throw new ValidationException("Payment method cannot be null or empty.");
        }

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
     * @throws ValidationException if the amount is less than or equal to 0
     */
    public void setAmount(double amount) {
        if (amount <= 0) {
            throw new ValidationException("Payment amount must be greater than 0.");
        }
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
     * @throws ValidationException if the date is null or in the future
     */
    public void setDate(LocalDateTime date) {
        if (date == null || date.isAfter(LocalDateTime.now())) {
            throw new ValidationException("Payment date cannot be null or in the future.");
        }
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
     * @throws ValidationException if the user is null
     */
    public void setUser(User user) {
        if (user == null) {
            throw new ValidationException("User cannot be null.");
        }
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
     * @throws ValidationException if the payment method is null or empty
     */
    public void setPaymentMethod(String paymentMethod) {
        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            throw new ValidationException("Payment method cannot be null or empty.");
        }
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return String.format(
                "Payment Details:\n" +
                        "- ID: %d\n" +
                        "- Amount: %.2f\n" +
                        "- Date: %s\n" +
                        "- User: %s\n" +
                        "- Payment Method: %s\n",
                id, amount, date, user, paymentMethod
        );
    }
}
