package Service;

import Domain.User;
import Domain.Role;
import Repository.IRepository;
import Exception.*;

/**
 * Service class for managing users in the system.
 */
public class UserService {

    private final IRepository<User> userRepo;

    /**
     * Constructs a UserService with the specified user repository.
     *
     * @param userRepo the repository used to store users.
     */
    public UserService(IRepository<User> userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * Adds a new user to the system.
     *
     * @param id       the unique ID of the user.
     * @param username the username of the user.
     * @param password the password of the user.
     * @param role     the role of the user (e.g., USER or ADMIN).
     * @throws EntityAlreadyExistsException if the user ID already exists.
     * @throws ValidationException          if username or password is empty, or the role is null.
     */
    public void addUser(String id, String username, String password, Role role) {
        try {
            int userId = Integer.parseInt(id);

            if (userRepo.read(userId) != null) {
                throw new EntityAlreadyExistsException("A user with this ID already exists.");
            }

            validateUserInputs(username, password, role);

            User user = new User(userId, username, password, role);
            userRepo.create(user);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format. ID must be a number: " + id, e);
        }
    }

    /**
     * Retrieves a user by their unique ID.
     *
     * @param id the unique ID of the user.
     * @return the user with the specified ID.
     * @throws EntityNotFoundException if the user with the specified ID does not exist.
     */
    public User getUserById(String id) {
        try {
            int userId = Integer.parseInt(id);
            User user = userRepo.read(userId);
            if (user == null) {
                throw new EntityNotFoundException("User with ID " + id + " not found.");
            }
            return user;
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format. ID must be a number: " + id, e);
        }
    }

    /**
     * Updates an existing user's details.
     *
     * @param id       the unique ID of the user to update.
     * @param username the updated username of the user.
     * @param password the updated password of the user.
     * @param role     the updated role of the user.
     * @throws EntityNotFoundException if the user does not exist.
     * @throws ValidationException     if any of the parameters are invalid.
     */
    public void updateUser(String id, String username, String password, Role role) {
        try {
            int userId = Integer.parseInt(id);

            User existingUser = userRepo.read(userId);
            if (existingUser == null) {
                throw new EntityNotFoundException("User with ID " + id + " not found.");
            }

            validateUserInputs(username, password, role);

            existingUser.setUsername(username);
            existingUser.setPassword(password);
            existingUser.setRole(role);
            userRepo.update(existingUser);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format. ID must be a number: " + id, e);
        }
    }

    /**
     * Deletes a user by their unique ID.
     *
     * @param id the unique ID of the user to delete.
     * @throws EntityNotFoundException if the user does not exist.
     */
    public void deleteUser(String id) {
        try {
            int userId = Integer.parseInt(id);
            User user = userRepo.read(userId);
            if (user == null) {
                throw new EntityNotFoundException("User with ID " + id + " not found.");
            }
            userRepo.delete(userId);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format. ID must be a number: " + id, e);
        }
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user.
     * @return the user with the specified username, or null if no such user exists.
     */
    public User getUserByUsername(String username) {
        return userRepo.findAll().stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * Generates a new unique ID for a user that is not already taken.
     *
     * @return a new unique ID.
     */
    public int generateNewUntakenId() {
        return userRepo.findAll().stream()
                .mapToInt(User::getId)
                .max()
                .orElse(0) + 1;
    }

    /**
     * Retrieves the ID of a given user.
     *
     * @param user the user whose ID is to be retrieved.
     * @return the ID of the user.
     */
    public int getUserId(User user) {
        return user.getId();
    }

    private void validateUserInputs(String username, String password, Role role) {
        if (username == null || username.trim().isEmpty()) {
            throw new ValidationException("Username cannot be empty.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new ValidationException("Password cannot be empty.");
        }
        if (role == null) {
            throw new ValidationException("Role cannot be null.");
        }
    }
}
