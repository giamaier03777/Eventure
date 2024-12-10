package Domain;

import Repository.EntityParser;
import Repository.Identifiable;
import Exception.*;


/**
 * Represents a user within the system.
 * Implements {@link Identifiable} for unique identification and {@link EntityParser} for CSV serialization/deserialization.
 */
public class User implements Identifiable {
    private int id;
    private String username;
    private String password;
    private Role role;
    private double balance;

    /**
     * Constructs a new {@code User}.
     *
     * @param id       the unique identifier for the user.
     * @param username the username of the user.
     * @param password the password of the user.
     * @param role     the role of the user (e.g., USER or ADMIN).
     * @throws ValidationException if any parameter is invalid
     */
    public User(int id, String username, String password, Role role) {
        if (username == null || username.trim().isEmpty()) {
            throw new ValidationException("Username cannot be null or empty.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new ValidationException("Password cannot be null or empty.");
        }
        if (role == null) {
            throw new ValidationException("Role cannot be null.");
        }

        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.balance = 0;
    }

    /**
     * Default constructor for creating an empty {@code User} object.
     */
    public User() {
    }

    /**
     * Gets the unique identifier of the user.
     *
     * @return the user ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the user.
     *
     * @param id the user ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the username of the user.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username the username.
     * @throws ValidationException if the username is null or empty
     */
    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new ValidationException("Username cannot be null or empty.");
        }
        this.username = username;
    }

    /**
     * Gets the password of the user.
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the password.
     * @throws ValidationException if the password is null or empty
     */
    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new ValidationException("Password cannot be null or empty.");
        }
        this.password = password;
    }

    /**
     * Gets the role of the user.
     *
     * @return the user role.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     *
     * @param role the user role.
     * @throws ValidationException if the role is null
     */
    public void setRole(Role role) {
        if (role == null) {
            throw new ValidationException("Role cannot be null.");
        }
        this.role = role;
    }

    /**
     * Gets the current balance of the user.
     *
     * @return the user's balance.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Increases the user's balance by a specified amount.
     *
     * @param amount the amount to add to the balance.
     * @throws ValidationException if the amount is negative
     */
    public void increaseBalance(double amount) {
        if (amount < 0) {
            throw new ValidationException("Amount to increase must be non-negative.");
        }
        this.balance += amount;
    }

    /**
     * Sets the user's balance to a specified amount.
     *
     * @param amount the new balance amount.
     * @throws ValidationException if the amount is negative
     */
    public void setBalance(double amount) {
        if (amount < 0) {
            throw new ValidationException("Balance cannot be negative.");
        }
        this.balance = amount;
    }

    @Override
    public String toString() {
        return String.format(
                "User Details:\n" +
                        "- ID: %d\n" +
                        "- Username: %s\n" +
                        "- Balance: %.2f\n",
                id,
                username != null ? username : "No username",
                balance
        );
    }
}
