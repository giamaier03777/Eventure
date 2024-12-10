package Service;

import Domain.Role;
import Domain.User;
import Presentation.PresentationAdmin;
import Presentation.PresentationUser;
import Exception.*;

/**
 * Service to manage role-based navigation to different menus for users and administrators.
 */
public class RoleBasedMenuService {
    private final PresentationAdmin adminMenu;
    private final PresentationUser userMenu;

    /**
     * Constructs a {@code RoleBasedMenuService} with the specified admin and user menu presentations.
     *
     * @param adminMenu the menu interface for administrators.
     * @param userMenu  the menu interface for regular users.
     */
    public RoleBasedMenuService(PresentationAdmin adminMenu, PresentationUser userMenu) {
        this.adminMenu = adminMenu;
        this.userMenu = userMenu;
    }

    /**
     * Starts the appropriate menu based on the user's role.
     *
     * @param user the user whose role determines the menu to be displayed.
     * @throws ValidationException if the user or role is invalid.
     */
    public void start(User user) {
        if (user == null) {
            throw new ValidationException("No user is logged in. Unable to proceed.");
        }

        if (user.getRole() == null) {
            throw new ValidationException("The user does not have a valid role. Access denied.");
        }

        switch (user.getRole()) {
            case ADMIN:
                System.out.println("Welcome Admin!");
                adminMenu.start(user);
                break;

            case USER:
                System.out.println("Welcome User!");
                userMenu.start(user);
                break;

            default:
                throw new ValidationException("Unknown role. Access denied.");
        }
    }
}
