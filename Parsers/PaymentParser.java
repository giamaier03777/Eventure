package Parsers;

import Domain.Payment;
import Domain.User;
import Repository.EntityParser;

import java.time.LocalDateTime;

/**
 * A parser for the {@link Payment} class, used for converting between {@code Payment} objects and their CSV representation.
 */
public class PaymentParser implements EntityParser<Payment> {

    private final UserParser userParser;

    /**
     * Constructs a {@link PaymentParser} with its dependencies.
     *
     * @param userParser the parser for {@link User} objects.
     */
    public PaymentParser(UserParser userParser) {
        this.userParser = userParser;
    }

    @Override
    public String toCSV(Payment payment) {
        return payment.getId() + "," +
                payment.getAmount() + "," +
                payment.getDate() + "," +
                userParser.toCSV(payment.getUser()) + "," +
                payment.getPaymentMethod();
    }

    @Override
    public Payment parseFromCSV(String csv) {
        String[] fields = csv.split(",", 5);

        int id = Integer.parseInt(fields[0]);

        double amount = Double.parseDouble(fields[1]);

        LocalDateTime date = LocalDateTime.parse(fields[2]);

        User user = userParser.parseFromCSV(fields[3]);

        String paymentMethod = fields[4];

        return new Payment(id, amount, date, user, paymentMethod);
    }
}
