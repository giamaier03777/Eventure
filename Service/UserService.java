package Service;

import Domain.User;
import Domain.Role;
import Repository.UserRepo;

public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void addUser(int id, String username, String password, Role role) {
        if (userRepo.read(id) != null) {
            throw new IllegalArgumentException("A user with this ID already exists.");
        }

        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }

        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null.");
        }

        User user = new User(id, username, password, role);
        userRepo.create(user);
    }

    public User getUserById(int id) {
        User user = userRepo.read(id);
        if (user == null) {
            throw new IllegalArgumentException("User with the specified ID does not exist.");
        }
        return user;
    }

    public void updateUser(int id, String username, String password, Role role) {
        User existingUser = userRepo.read(id);

        if (existingUser == null) {
            throw new IllegalArgumentException("User with the specified ID does not exist.");
        }

        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }

        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null.");
        }

        existingUser.setUsername(username);
        existingUser.setPassword(password);
        existingUser.setRole(role);
        userRepo.update(existingUser);
    }

    public void deleteUser(int id) {
        User user = userRepo.read(id);
        if (user == null) {
            throw new IllegalArgumentException("User with the specified ID does not exist.");
        }
        userRepo.delete(id);
    }

}

