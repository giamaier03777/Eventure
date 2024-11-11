package Service;

import Domain.Payment;
import Domain.User;
import Repository.PaymentRepo;

import java.time.LocalDateTime;

public class PaymentService {

    private final PaymentRepo paymentRepo;

    public PaymentService(PaymentRepo paymentRepo) {
        this.paymentRepo = paymentRepo;
    }

    public void addPayment(int id, double amount, LocalDateTime date, User user, String paymentMethod) {
        if (paymentRepo.read(id) != null) {
            throw new IllegalArgumentException("A payment with this ID already exists.");
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

        Payment payment = new Payment(id, amount, date, user, paymentMethod);
        paymentRepo.create(payment);
    }

    public Payment getPaymentById(int id) {
        return paymentRepo.read(id);
    }

    public void updatePayment(int id, double amount, LocalDateTime date, User user, String paymentMethod) {
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

    public void deletePayment(int id) {
        if (paymentRepo.read(id) == null) {
            throw new IllegalArgumentException("Payment with the specified ID does not exist.");
        }

        paymentRepo.delete(id);
    }
}
