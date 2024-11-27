package SQLParser;

import Domain.*;
import Repository.SQLParser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * SQLParser implementation for {@link Payment} entities.
 */
public class PaymentSQLParser implements SQLParser<Payment> {

    private final UserSQLParser userSQLParser;

    /**
     * Constructs a {@link PaymentSQLParser} with its dependencies.
     *
     * @param userSQLParser the parser for {@link User} objects.
     */
    public PaymentSQLParser(UserSQLParser userSQLParser) {
        this.userSQLParser = userSQLParser;
    }

    @Override
    public String getColumns() {
        return "id, amount, date, user_id, payment_method";
    }

    @Override
    public String getPlaceholders() {
        return "?, ?, ?, ?, ?";
    }

    @Override
    public String getUpdateColumns() {
        return "amount = ?, date = ?, user_id = ?, payment_method = ?";
    }

    @Override
    public void fillPreparedStatementForInsert(PreparedStatement stmt, Payment payment) throws SQLException {
        stmt.setInt(1, payment.getId());
        stmt.setDouble(2, payment.getAmount());
        stmt.setObject(3, payment.getDate());
        stmt.setInt(4, payment.getUser().getId());
        stmt.setString(5, payment.getPaymentMethod());
    }

    @Override
    public void fillPreparedStatementForUpdate(PreparedStatement stmt, Payment payment) throws SQLException {
        stmt.setDouble(1, payment.getAmount());
        stmt.setObject(2, payment.getDate());
        stmt.setInt(3, payment.getUser().getId());
        stmt.setString(4, payment.getPaymentMethod());
        stmt.setInt(5, payment.getId()); // WHERE clause
    }

    @Override
    public Payment parseFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        double amount = rs.getDouble("amount");
        LocalDateTime date = rs.getObject("date", LocalDateTime.class);
        String paymentMethod = rs.getString("payment_method");

        int userId = rs.getInt("user_id");
        User user = userSQLParser.parseFromResultSet(rs);

        user.setBalance(rs.getDouble("balance"));

        return new Payment(id, amount, date, user, paymentMethod);
    }

    @Override
    public int getUpdateParametersCount() {
        return 4;
    }
}

