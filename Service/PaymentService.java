package Service;

import Domain.Payment;
import Domain.User;
import Repository.IRepository;
import Repository.InMemoryRepo;

import java.time.LocalDateTime;

/**
 * Service class for managing payments in the system.
 */
public class PaymentService {

    private final IRepository<Payment> paymentRepo;

    /**
     * Constructs a new {@code PaymentService}.
     *
     * @param paymentRepo the repository for storing and managing payments.
     */
    public PaymentService(IRepository<Payment> paymentRepo) {
        this.paymentRepo = paymentRepo;
    }

    /**
     * Adds a new payment to the repository.
     *
     * @param idString      the unique identifier of the payment as a string.
     * @param amountString  the payment amount as a string.
     * @param dateString    the date of the payment as a string in ISO-8601 format.
     * @param user          the user who made the payment.
     * @param paymentMethod the method of payment (e.g., "CASH", "CARD").
     * @throws IllegalArgumentException if validation fails or the payment already exists.
     */
    public void addPayment(String idString, String amountString, String dateString, User user, String paymentMethod) {
        int id = Integer.parseInt(idString);
        double amount = Double.parseDouble(amountString);
        LocalDateTime date = LocalDateTime.parse(dateString);

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be a positive value.");
        }

        if (date == null || date.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Date cannot be in the future and must be valid.");
        }

        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            throw new IllegalArgumentException("Payment method cannot be empty.");
        }

        Payment payment = new Payment(id, amount, date, user, paymentMethod);
        paymentRepo.create(payment);
    }

    /**
     * Retrieves a payment by its ID.
     *
     * @param id the unique identifier of the payment as a string.
     * @return the {@code Payment} object if found, or {@code null} if not found.
     */
    public Payment getPaymentById(String id) {
        return paymentRepo.read(Integer.parseInt(id));
    }

    /**
     * Updates an existing payment in the repository.
     *
     * @param idString      the unique identifier of the payment as a string.
     * @param amountString  the updated payment amount as a string.
     * @param dateString    the updated date of the payment as a string in ISO-8601 format.
     * @param user          the updated user who made the payment.
     * @param paymentMethod the updated method of payment.
     * @throws IllegalArgumentException if the payment does not exist or validation fails.
     */
    public void updatePayment(String idString, String amountString, String dateString, User user, String paymentMethod) {
        int id = Integer.parseInt(idString);
        double amount = Double.parseDouble(amountString);
        LocalDateTime date = LocalDateTime.parse(dateString);
        Payment existingPayment = paymentRepo.read(id);

        if (existingPayment == null) {
            throw new IllegalArgumentException("Payment with the specified ID does not exist.");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be a positive value.");
        }

        if (date == null || date.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Date cannot be in the future and must be valid.");
        }

        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            throw new IllegalArgumentException("Payment method cannot be empty.");
        }

        Payment updatedPayment = new Payment(id, amount, date, user, paymentMethod);
        paymentRepo.update(updatedPayment);
    }

    /**
     * Deletes a payment by its ID.
     *
     * @param id the unique identifier of the payment as a string.
     * @throws IllegalArgumentException if the payment does not exist.
     */
    public void deletePayment(String id) {
        if (paymentRepo.read(Integer.parseInt(id)) == null) {
            throw new IllegalArgumentException("Payment with the specified ID does not exist.");
        }

        paymentRepo.delete(Integer.parseInt(id));
    }
}
