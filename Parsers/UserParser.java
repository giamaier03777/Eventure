package Parsers;

import Domain.*;
import Repository.EntityParser;


/**
 * A parser for {@link User} entities to enable CSV serialization and deserialization.
 */
public class UserParser implements EntityParser<User> {

    @Override
    public String toCSV(User user) {
        return user.getId() + "," +
                user.getUsername() + "," +
                user.getPassword() + "," +
                user.getRole() + "," +
                user.getBalance();
    }

    @Override
    public User parseFromCSV(String csv) {
        String[] fields = csv.split(",");

        int id = Integer.parseInt(fields[0]);
        String username = fields[1];
        String password = fields[2];
        Role role = Role.valueOf(fields[3].toUpperCase());
        double balance = Double.parseDouble(fields[4]);

        User user = new User(id, username, password, role);
        user.setBalance(balance);

        return user;
    }
}

