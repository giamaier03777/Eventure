package Presentation;

import Controller.AdminController;
import Controller.UserController;
import Domain.Role;
import Domain.User;
import Service.RoleBasedMenuService;

import java.util.Scanner;

/**
 * The `LoginUI` class provides a user interface for logging in,
 * registering new users, and navigating the application based on user roles.
 */
public class LoginUI {
    private final AdminController adminController;
    private final UserController userController;
    private final RoleBasedMenuService menuService;
    private final Scanner scanner;

    /**
     * Constructs a new instance of `LoginUI`.
     *
     * @param adminController the controller for admin-related operations
     * @param userController  the controller for user-related operations
     * @param menuService     the service for role-based menu navigation
     */
    public LoginUI(AdminController adminController, UserController userController, RoleBasedMenuService menuService) {
        this.adminController = adminController;
        this.userController = userController;
        this.menuService = menuService;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Starts the login UI and displays the main menu.
     */
    public void start() {
        while (true) {
            System.out.println("\n=== Welcome to the Application ===");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> login();
                case "2" -> register();
                case "3" -> {
                    System.out.println("Exiting application...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Handles the login functionality, prompting the user for credentials
     * and directing them to the appropriate menu based on their role.
     */
    private void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = adminController.getUserByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Login successful. Welcome, " + user.getUsername() + "!");
            menuService.start(user);
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    /**
     * Handles the registration process for new users,
     * prompting them to provide necessary details.
     */
    private void register() {
        try {
            System.out.print("Choose a Username: ");
            String username = scanner.nextLine();

            System.out.print("Choose a Password: ");
            String password = scanner.nextLine();

            Role role = promptForRole();
            if (role == null) {
                System.out.println("Registration cancelled due to invalid role selection.");
                return;
            }

            int id = adminController.generateNewUserId();
            adminController.addUser(String.valueOf(id), username, password, role);

            System.out.println("Registration successful. You can now log in.");
        } catch (Exception e) {
            System.out.println("Error during registration: " + e.getMessage());
        }
    }

    /**
     * Prompts the user to select a role during registration.
     *
     * @return the selected `Role`, or `null` if the input is invalid
     */
    private Role promptForRole() {
        System.out.println("Select Role:");
        System.out.println("1. USER");
        System.out.println("2. ADMIN");
        System.out.print("Enter choice (1 or 2): ");

        String choice = scanner.nextLine();
        return switch (choice) {
            case "1" -> Role.USER;
            case "2" -> Role.ADMIN;
            default -> {
                System.out.println("Invalid selection. Please choose 1 for USER or 2 for ADMIN.");
                yield null;
            }
        };
    }
}
