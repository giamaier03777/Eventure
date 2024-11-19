package Service;

import Domain.Role;
import Domain.User;
import Presentation.PresentationAdmin;
import Presentation.PresentationUser;

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
     */
    public void start(User user) {
        if (user == null) {
            System.out.println("No user logged in. Exiting...");
            return;
        }

        if (user.getRole() == Role.ADMIN) {
            System.out.println("Welcome Admin!");
            adminMenu.start(user);
        } else if (user.getRole() == Role.USER) {
            System.out.println("Welcome User!");
            userMenu.start(user);
        } else {
            System.out.println("Unknown role. Access denied.");
        }
    }
}
