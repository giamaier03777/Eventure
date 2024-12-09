package Domain;

import Repository.EntityParser;
import Repository.Identifiable;

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
     */
    public User(int id, String username, String password, Role role) {
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
     */
    public void setUsername(String username) {
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
     */
    public void setPassword(String password) {
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
     */
    public void setRole(Role role) {
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
     */
    public void increaseBalance(double amount) {
        this.balance += amount;
    }

    /**
     * Sets the user's balance to a specified amount.
     *
     * @param amount the new balance amount.
     */
    public void setBalance(double amount) {
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
