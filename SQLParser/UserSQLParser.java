package SQLParser;

import Domain.*;
import Repository.SQLParser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SQLParser implementation for {@link User} entities.
 */
public class UserSQLParser implements SQLParser<User> {

    @Override
    public String getColumns() {
        return "id, username, password, role, balance";
    }

    @Override
    public String getPlaceholders() {
        return "?, ?, ?, ?, ?";
    }

    @Override
    public String getUpdateColumns() {
        return "username = ?, password = ?, role = ?, balance = ?";
    }

    @Override
    public void fillPreparedStatementForInsert(PreparedStatement stmt, User user) throws SQLException {
        stmt.setInt(1, user.getId());
        stmt.setString(2, user.getUsername());
        stmt.setString(3, user.getPassword());
        stmt.setString(4, user.getRole().name());
        stmt.setDouble(5, user.getBalance());
    }

    @Override
    public void fillPreparedStatementForUpdate(PreparedStatement stmt, User user) throws SQLException {
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPassword());
        stmt.setString(3, user.getRole().name());
        stmt.setDouble(4, user.getBalance());
        stmt.setInt(5, user.getId());
    }

    @Override
    public User parseFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String username = rs.getString("username");
        String password = rs.getString("password");
        Role role = Role.valueOf(rs.getString("role").toUpperCase());
        double balance = rs.getDouble("balance");

        User user = new User(id, username, password, role);
        user.setBalance(balance);

        return user;
    }

    @Override
    public int getUpdateParametersCount() {
        return 4;
    }
}
