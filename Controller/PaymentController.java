package Controller;

import Domain.Payment;
import Domain.User;
import Service.PaymentService;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void addPayment(String idString, String amountString, String dateString, User user, String paymentMethod) {
        try {
            int id = Integer.parseInt(idString);
            double amount = Double.parseDouble(amountString);
            LocalDateTime date = LocalDateTime.parse(dateString);

            paymentService.addPayment(id, amount, date, user, paymentMethod);
            System.out.println("Payment added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID and amount must be valid numbers.");
        } catch (DateTimeParseException e) {
            System.out.println("Date must be in a valid format (e.g., '2023-12-31T10:15:30').");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public Payment getPaymentById(String idString) {
        try {
            int id = Integer.parseInt(idString);
            return paymentService.getPaymentById(id);
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
            return null;
        }
    }

    public void updatePayment(String idString, String amountString, String dateString, User user, String paymentMethod) {
        try {
            int id = Integer.parseInt(idString);
            double amount = Double.parseDouble(amountString);
            LocalDateTime date = LocalDateTime.parse(dateString);

            paymentService.updatePayment(id, amount, date, user, paymentMethod);
            System.out.println("Payment updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID and amount must be valid numbers.");
        } catch (DateTimeParseException e) {
            System.out.println("Date must be in a valid format (e.g., '2023-12-31T10:15:30').");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deletePayment(String idString) {
        try {
            int id = Integer.parseInt(idString);
            paymentService.deletePayment(id);
            System.out.println("Payment deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
