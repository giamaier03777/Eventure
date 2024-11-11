package Controller;

import Domain.User;
import Domain.Role;
import Service.UserService;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void addUser(String idString, String username, String password, Role role) {
        try {
            int id = Integer.parseInt(idString);

            userService.addUser(id, username, password, role);
            System.out.println("User added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public User getUserById(String idString) {
        try {
            int id = Integer.parseInt(idString);
            return userService.getUserById(id);
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
            return null;
        }
    }

    public void updateUser(String idString, String username, String password, Role role) {
        try {
            int id = Integer.parseInt(idString);

            userService.updateUser(id, username, password, role);
            System.out.println("User updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteUser(String idString) {
        try {
            int id = Integer.parseInt(idString);
            userService.deleteUser(id);
            System.out.println("User deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
