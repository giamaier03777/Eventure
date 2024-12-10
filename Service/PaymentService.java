package Service;

import Domain.Payment;
import Domain.User;
import Repository.IRepository;
import Exception.*;

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
     * @throws EntityAlreadyExistsException if the payment already exists.
     * @throws ValidationException if validation fails.
     */
    public void addPayment(String idString, String amountString, String dateString, User user, String paymentMethod) {
        try {
            int id = Integer.parseInt(idString);
            double amount = Double.parseDouble(amountString);
            LocalDateTime date = LocalDateTime.parse(dateString);

            if (paymentRepo.read(id) != null) {
                throw new EntityAlreadyExistsException("A payment with this ID already exists.");
            }

            validatePaymentInputs(amount, date, user, paymentMethod);

            Payment payment = new Payment(id, amount, date, user, paymentMethod);
            paymentRepo.create(payment);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid number format for ID or amount.", e);
        }
    }

    /**
     * Retrieves a payment by its ID.
     *
     * @param id the unique identifier of the payment as a string.
     * @return the {@code Payment} object if found.
     * @throws EntityNotFoundException if the payment does not exist.
     */
    public Payment getPaymentById(String id) {
        try {
            int paymentId = Integer.parseInt(id);
            Payment payment = paymentRepo.read(paymentId);
            if (payment == null) {
                throw new EntityNotFoundException("Payment with ID " + id + " not found.");
            }
            return payment;
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format. ID must be a number: " + id, e);
        }
    }

    /**
     * Updates an existing payment in the repository.
     *
     * @param idString      the unique identifier of the payment as a string.
     * @param amountString  the updated payment amount as a string.
     * @param dateString    the updated date of the payment as a string in ISO-8601 format.
     * @param user          the updated user who made the payment.
     * @param paymentMethod the updated method of payment.
     * @throws EntityNotFoundException if the payment does not exist.
     * @throws ValidationException if validation fails.
     */
    public void updatePayment(String idString, String amountString, String dateString, User user, String paymentMethod) {
        try {
            int id = Integer.parseInt(idString);
            double amount = Double.parseDouble(amountString);
            LocalDateTime date = LocalDateTime.parse(dateString);

            Payment existingPayment = paymentRepo.read(id);
            if (existingPayment == null) {
                throw new EntityNotFoundException("Payment with ID " + id + " not found.");
            }

            validatePaymentInputs(amount, date, user, paymentMethod);

            Payment updatedPayment = new Payment(id, amount, date, user, paymentMethod);
            paymentRepo.update(updatedPayment);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid number format for ID or amount.", e);
        }
    }

    /**
     * Deletes a payment by its ID.
     *
     * @param id the unique identifier of the payment as a string.
     * @throws EntityNotFoundException if the payment does not exist.
     */
    public void deletePayment(String id) {
        try {
            int paymentId = Integer.parseInt(id);
            if (paymentRepo.read(paymentId) == null) {
                throw new EntityNotFoundException("Payment with ID " + id + " not found.");
            }
            paymentRepo.delete(paymentId);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format. ID must be a number: " + id, e);
        }
    }

    private void validatePaymentInputs(double amount, LocalDateTime date, User user, String paymentMethod) {
        if (amount <= 0) {
            throw new ValidationException("Amount must be a positive value.");
        }
        if (date == null || date.isAfter(LocalDateTime.now())) {
            throw new ValidationException("Date cannot be in the future and must be valid.");
        }
        if (user == null) {
            throw new ValidationException("User cannot be null.");
        }
        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            throw new ValidationException("Payment method cannot be empty.");
        }
    }
}
