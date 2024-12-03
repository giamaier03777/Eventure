package Presentation;

import Controller.*;
import Domain.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The `PresentationAdmin` class provides the user interface for admin users,
 * allowing them to manage activities, schedules, bookings, events, and other related entities.
 */
public class PresentationAdmin {
    private final AdminController adminController;
    private User currentUser = null;

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Constructs a new `PresentationAdmin` instance.
     *
     * @param adminController the controller that handles admin operations.
     */
    public PresentationAdmin(AdminController adminController) {
        this.adminController = adminController;
    }

    /**
     * Starts the admin interface with the given user.
     *
     * @param user the currently logged-in user.
     */
    public void start(User user) {
        this.currentUser = user;
        adminMenu();
    }

    /**
     * Prompts the admin to select a role (USER or ADMIN).
     *
     * @return the selected {@link Role}, or {@code null} if the selection is invalid.
     */
    private Role promptForRole() {
        System.out.println("Select Role:");
        System.out.println("1. USER");
        System.out.println("2. ADMIN");
        System.out.print("Enter choice (1 or 2): ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                return Role.USER;
            case "2":
                return Role.ADMIN;
            default:
                System.out.println("Invalid selection. Please choose 1 for USER or 2 for ADMIN.");
                return null;
        }
    }

    /**
     * Displays the main admin menu and handles navigation.
     */
    private void adminMenu() {
        while (true) {
            System.out.println("Admin Menu:\n1. Activity\n2. Activity Schedule\n3. Booking\n4. Event\n5. Free Activity\n6. Payment\n7. Reservation\n8. Review\n9. Ticket\n10. User\n11. Wishlist\n12. Back to Main Menu");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> activityAdminMenu();
                case 2 -> activityScheduleAdminMenu();
                case 3 -> bookingAdminMenu();
                case 4 -> eventAdminMenu();
                case 5 -> freeActivityAdminMenu();
                case 6 -> paymentAdminMenu();
                case 7 -> reviewAdminMenu();
                case 8 -> reviewAdminMenu();
                case 9 -> ticketAdminMenu();
                case 10 -> userAdminMenu();
                case 11 -> wishlistAdminMenu();
                case 12 -> {
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    /**
     * Displays the Activity Management menu and handles related operations.
     */
    private void activityAdminMenu() {
        while (true) {
            System.out.println("Activity Management:\n1. Add\n2. View\n3. Update\n4. Delete\n5. Back");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addActivity();
                case 2 -> viewActivity();
                case 3 -> updateActivity();
                case 4 -> deleteActivity();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    /**
     * Prompts the admin to add a new activity.
     */
    private void addActivity() {
        System.out.println("Enter Activity Details:");
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Capacity: ");
        int capacity = Integer.parseInt(scanner.nextLine());
        System.out.print("Location: ");
        String location = scanner.nextLine();
        System.out.print("Event Type (e.g., CONFERENCE): ");
        String eventType = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();
        System.out.println("Enter ticket price: ");
        double ticketPrice = Double.parseDouble(scanner.nextLine());

        adminController.addActivity(String.valueOf(id), name, String.valueOf(capacity), location, eventType, description, ticketPrice);
    }

    /**
     * Prompts the admin to view an activity by its ID.
     */
    private void viewActivity() {
        System.out.print("Enter Activity ID: ");
        String id = scanner.nextLine();
        Activity activity = adminController.getActivityById(id);
        if (activity != null) {
            System.out.println(activity);
        } else {
            System.out.println("Activity not found.");
        }
    }

    /**
     * Prompts the admin to update an existing activity.
     */
    private void updateActivity() {
        System.out.println("Enter Updated Activity Details:");
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Capacity: ");
        int capacity = Integer.parseInt(scanner.nextLine());
        System.out.print("Location: ");
        String location = scanner.nextLine();
        System.out.print("Event Type (e.g., CONFERENCE): ");
        String eventType = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();
        System.out.println("Enter ticket price: ");
        double ticketPrice = Double.parseDouble(scanner.nextLine());

        adminController.updateActivity(String.valueOf(id), name, String.valueOf(capacity), location, eventType, description, ticketPrice);
    }

    /**
     * Prompts the admin to delete an activity by its ID.
     */
    private void deleteActivity() {
        System.out.print("Enter Activity ID to delete: ");
        String id = scanner.nextLine();
        adminController.deleteActivity(id);
    }

    /**
     * Displays the Activity Schedule Management menu and handles related operations.
     */
    private void activityScheduleAdminMenu() {
        while (true) {
            System.out.println("Activity Schedule Management:\n1. Add\n2. View\n3. Update\n4. Delete\n5. Back");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addActivitySchedule();
                case 2 -> viewActivitySchedule();
                case 3 -> updateActivitySchedule();
                case 4 -> deleteActivitySchedule();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    /**
     * Prompts the admin to add a new activity schedule.
     */
    private void addActivitySchedule() {
        try {
            System.out.print("Enter Activity Schedule ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Activity ID: ");
            String activityId = scanner.nextLine();
            Activity activity = adminController.getActivityById(activityId);
            if (activity == null) {
                System.out.println("Activity not found with ID: " + activityId);
                return;
            }

            System.out.print("Enter Date (YYYY-MM-DD): ");
            String dateString = scanner.nextLine();
            System.out.print("Enter Start Time (HH:MM): ");
            String startTimeString = scanner.nextLine();
            System.out.print("Enter End Time (HH:MM): ");
            String endTimeString = scanner.nextLine();
            System.out.print("Enter Available Capacity: ");
            String capacity = scanner.nextLine();

            adminController.addActivitySchedule(String.valueOf(id), activity, dateString, startTimeString, endTimeString, capacity);
        } catch (Exception e) {
            System.out.println("Error adding activity schedule: " + e.getMessage());
        }
    }

    /**
     * Prompts the admin to view an activity schedule by its ID.
     */
    private void viewActivitySchedule() {
        System.out.print("Enter Activity Schedule ID: ");
        String id = scanner.nextLine();
        ActivitySchedule schedule = adminController.getActivityScheduleById(id);
        System.out.println(schedule != null ? schedule : "Schedule not found.");
    }


    /**
     * Updates an existing activity schedule.
     * Prompts the admin to enter updated details for the activity schedule.
     */
    private void updateActivitySchedule() {
        try {
            System.out.print("Enter Activity Schedule ID to Update: ");
            String id = scanner.nextLine();
            System.out.print("Enter Activity ID: ");
            String activityId = scanner.nextLine();
            Activity activity = adminController.getActivityById(activityId);
            if (activity == null) {
                System.out.println("Activity not found with ID: " + activityId);
                return;
            }

            System.out.print("Enter Date (YYYY-MM-DD): ");
            String dateString = scanner.nextLine();
            System.out.print("Enter Start Time (HH:MM): ");
            String startTimeString = scanner.nextLine();
            System.out.print("Enter End Time (HH:MM): ");
            String endTimeString = scanner.nextLine();
            System.out.print("Enter Available Capacity: ");
            String capacity = scanner.nextLine();

            adminController.updateActivitySchedule(id, activity, dateString, startTimeString, endTimeString, capacity);
        } catch (Exception e) {
            System.out.println("Error updating activity schedule: " + e.getMessage());
        }
    }

    /**
     * Deletes an existing activity schedule.
     * Prompts the admin to enter the ID of the activity schedule to delete.
     */
    private void deleteActivitySchedule() {
        System.out.print("Enter Activity Schedule ID to delete: ");
        String id = scanner.nextLine();
        adminController.deleteActivitySchedule(id);
    }

    /**
     * Displays the Booking Management menu and handles related operations.
     */
    private void bookingAdminMenu() {
        while (true) {
            System.out.println("Booking Management:\n1. Add\n2. View\n3. Update\n4. Delete\n5. Back");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addBooking();
                case 2 -> viewBooking();
                case 3 -> updateBooking();
                case 4 -> deleteBooking();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    /**
     * Adds a new booking for an activity schedule.
     * Prompts the admin to provide details such as customer name, number of people, and schedule ID.
     */
    private void addBooking() {
        try {
            System.out.print("Enter Booking ID: ");
            String idString = scanner.nextLine();
            System.out.print("Enter Customer Name: ");
            String customerName = scanner.nextLine();
            System.out.print("Enter Number of People: ");
            String numberOfPeopleString = scanner.nextLine();
            System.out.print("Enter Activity Schedule ID: ");
            String activityScheduleIdString = scanner.nextLine();
            ActivitySchedule activitySchedule = adminController.getActivityScheduleById(activityScheduleIdString);
            if (activitySchedule == null) {
                System.out.println("Activity Schedule not found with ID: " + activityScheduleIdString);
                return;
            }
            adminController.addBooking(idString, activitySchedule, customerName, numberOfPeopleString);
        } catch (Exception e) {
            System.out.println("Error adding booking: " + e.getMessage());
        }
    }

    /**
     * Views an existing booking.
     * Prompts the admin to enter the ID of the booking to view.
     */
    private void viewBooking() {
        System.out.print("Enter Booking ID: ");
        String id = scanner.nextLine();
        Booking booking = adminController.getBookingById(id);
        System.out.println(booking != null ? booking : "Booking not found.");
    }

    /**
     * Updates an existing booking.
     * Prompts the admin to provide new details for the booking, including schedule and number of people.
     */
    private void updateBooking() {
        try {
            System.out.print("Enter Booking ID to Update: ");
            String id = scanner.nextLine();
            System.out.print("Enter new Customer Name: ");
            String customerName = scanner.nextLine();
            System.out.print("Enter new Number of People: ");
            String numberOfPeopleString = scanner.nextLine();
            System.out.print("Enter new Activity Schedule ID: ");
            String activityScheduleIdString = scanner.nextLine();
            ActivitySchedule activitySchedule = adminController.getActivityScheduleById(activityScheduleIdString);
            if (activitySchedule == null) {
                System.out.println("Activity Schedule not found with ID: " + activityScheduleIdString);
                return;
            }
            adminController.updateBooking(id, activitySchedule, customerName, numberOfPeopleString);
        } catch (Exception e) {
            System.out.println("Error updating booking: " + e.getMessage());
        }
    }

    /**
     * Deletes an existing booking.
     * Prompts the admin to enter the ID of the booking to delete.
     */
    private void deleteBooking() {
        System.out.print("Enter Booking ID to delete: ");
        String id = scanner.nextLine();
        adminController.deleteBooking(id);
    }

    /**
     * Displays the Event Management menu and handles related operations.
     */
    private void eventAdminMenu() {
        while (true) {
            System.out.println("Event Management:\n1. Add\n2. View\n3. Update\n4. Delete\n5. Back");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addEvent();
                case 2 -> viewEvent();
                case 3 -> updateEvent();
                case 4 -> deleteEvent();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    /**
     * Adds a new event.
     * Prompts the admin to provide details such as event name, location, capacity, and ticket price.
     */
    private void addEvent() {
        try {
            System.out.print("Enter Event ID: ");
            String idString = scanner.nextLine();
            System.out.print("Enter Event Name: ");
            String eventName = scanner.nextLine();
            System.out.print("Enter Event Location: ");
            String location = scanner.nextLine();
            System.out.print("Enter Event Capacity: ");
            String capacityString = scanner.nextLine();
            System.out.print("Enter Event Type (e.g., CONCERT, SEMINAR, etc.): ");
            String eventTypeString = scanner.nextLine();
            System.out.print("Enter Current Size: ");
            String currentSizeString = scanner.nextLine();
            System.out.print("Enter Start Date (yyyy-MM-ddTHH:mm): ");
            String startDateString = scanner.nextLine();
            System.out.print("Enter End Date (yyyy-MM-ddTHH:mm): ");
            String endDateString = scanner.nextLine();
            System.out.println("Enter ticket price: ");
            double ticketPrice = Double.parseDouble(scanner.nextLine());

            adminController.addEvent(idString, eventName, location, capacityString, eventTypeString, currentSizeString, startDateString, endDateString, ticketPrice);
        } catch (Exception e) {
            System.out.println("Error adding event: " + e.getMessage());
        }
    }

    /**
     * Views an existing event.
     * Prompts the admin to enter the ID of the event to view.
     */
    private void viewEvent() {
        System.out.print("Enter Event ID: ");
        String idString = scanner.nextLine();
        Event event = adminController.getEventById(idString);
        System.out.println(event != null ? event : "Event not found.");
    }

    /**
     * Updates an existing event.
     * Prompts the admin to provide new details for the event, including location, capacity, and ticket price.
     */
    private void updateEvent() {
        try {
            System.out.print("Enter Event ID to Update: ");
            String idString = scanner.nextLine();
            System.out.print("Enter new Event Name: ");
            String eventName = scanner.nextLine();
            System.out.print("Enter new Event Location: ");
            String location = scanner.nextLine();
            System.out.print("Enter new Event Capacity: ");
            String capacityString = scanner.nextLine();
            System.out.print("Enter new Event Type (e.g., CONCERT, SEMINAR, etc.): ");
            String eventTypeString = scanner.nextLine();
            System.out.print("Enter new Current Size: ");
            String currentSizeString = scanner.nextLine();
            System.out.print("Enter new Start Date (yyyy-MM-ddTHH:mm): ");
            String startDateString = scanner.nextLine();
            System.out.print("Enter new End Date (yyyy-MM-ddTHH:mm): ");
            String endDateString = scanner.nextLine();
            System.out.println("Enter ticket price: ");
            double priceString = Double.parseDouble(scanner.nextLine());

            adminController.updateEvent(idString, eventName, location, capacityString, eventTypeString, currentSizeString, startDateString, endDateString, priceString);
        } catch (Exception e) {
            System.out.println("Error updating event: " + e.getMessage());
        }
    }

    /**
     * Deletes an existing event.
     * Prompts the admin to enter the ID of the event to delete.
     */
    private void deleteEvent() {
        System.out.print("Enter Event ID to delete: ");
        String idString = scanner.nextLine();
        adminController.deleteEvent(idString);
    }


    /**
     * Displays the Free Activity Management menu and handles related operations.
     */
    private void freeActivityAdminMenu() {
        while (true) {
            System.out.println("Free Activity Management:\n1. Add\n2. View\n3. Update\n4. Delete\n5. Back");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addFreeActivity();
                case 2 -> viewFreeActivity();
                case 3 -> updateFreeActivity();
                case 4 -> deleteFreeActivity();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    /**
     * Adds a new free activity.
     * Prompts the admin to provide details such as ID, name, location, event type, and program details.
     */
    private void addFreeActivity() {
        try {
            System.out.print("Enter Free Activity ID: ");
            String idString = scanner.nextLine();
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Location: ");
            String location = scanner.nextLine();
            System.out.print("Enter Event Type (e.g., SPORTS, MUSIC): ");
            String eventTypeString = scanner.nextLine();
            System.out.print("Enter Program Details: ");
            String program = scanner.nextLine();

            adminController.addFreeActivity(idString, name, location, eventTypeString, program);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID input. Please enter a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred while adding the free activity: " + e.getMessage());
        }
    }

    /**
     * Views an existing free activity.
     * Prompts the admin to enter the ID of the free activity to view.
     */
    private void viewFreeActivity() {
        System.out.print("Enter Free Activity ID: ");
        String idString = scanner.nextLine();
        FreeActivity freeActivity = adminController.getFreeActivityById(idString);
        System.out.println(freeActivity != null ? freeActivity : "Free Activity not found.");
    }

    /**
     * Updates an existing free activity.
     * Prompts the admin to provide updated details such as name, location, event type, and program details.
     */
    private void updateFreeActivity() {
        try {
            System.out.print("Enter Free Activity ID to Update: ");
            String idString = scanner.nextLine();
            System.out.print("Enter new Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter new Location: ");
            String location = scanner.nextLine();
            System.out.print("Enter new Event Type (e.g., SPORTS, MUSIC): ");
            String eventTypeString = scanner.nextLine();
            System.out.print("Enter new Program Details: ");
            String program = scanner.nextLine();

            adminController.updateFreeActivity(idString, name, location, eventTypeString, program);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID input. Please enter a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred while updating the free activity: " + e.getMessage());
        }
    }

    /**
     * Deletes an existing free activity.
     * Prompts the admin to enter the ID of the free activity to delete.
     */
    private void deleteFreeActivity() {
        try {
            System.out.print("Enter Free Activity ID to delete: ");
            String idString = scanner.nextLine();

            adminController.deleteFreeActivity(idString);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID input. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("An error occurred while deleting the free activity: " + e.getMessage());
        }
    }

    /**
     * Displays the Payment Management menu and handles related operations.
     */
    private void paymentAdminMenu() {
        while (true) {
            System.out.println("Payment Management:\n1. Add\n2. View\n3. Update\n4. Delete\n5. Back");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addPayment();
                case 2 -> viewPayment();
                case 3 -> updatePayment();
                case 4 -> deletePayment();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    /**
     * Adds a new payment.
     * Prompts the admin to provide details such as payment ID, amount, date, and payment method.
     */
    private void addPayment() {
        try {
            System.out.print("Enter Payment ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            int userId = currentUser.getId();
            User user = adminController.getUserById(String.valueOf(userId));
            if (user == null) {
                System.out.println("User not found.");
                return;
            }

            System.out.print("Enter Amount: ");
            double amount = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter Payment Date (e.g., 2023-12-31T10:15:30): ");
            String dateString = scanner.nextLine();

            String paymentMethod = "PAYPAL";
            adminController.addPayment(String.valueOf(id), String.valueOf(amount), dateString, user, paymentMethod);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input for ID, userId, or amount. Please enter valid numbers.");
        } catch (DateTimeParseException e) {
            System.out.println("Date must be in a valid format (e.g., '2023-12-31T10:15:30').");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    /**
     * Views a specific payment by ID.
     * Prompts the admin to enter the payment ID and displays the payment details if found.
     */
    private void viewPayment() {
        System.out.print("Enter Payment ID: ");
        String idString = scanner.nextLine();
        Payment payment = adminController.getPaymentById(idString);
        if (payment != null) {
            System.out.println(payment);
        } else {
            System.out.println("Payment not found.");
        }
    }

    /**
     * Updates a specific payment by ID.
     * Prompts the admin to provide updated details such as amount, date, and payment method.
     */
    private void updatePayment() {
        try {
            System.out.print("Enter Payment ID to Update: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter new Amount: ");
            double amount = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter new Payment Date (e.g., 2023-12-31T10:15:30): ");
            String dateString = scanner.nextLine();
            int userId = currentUser.getId();
            User user = adminController.getUserById(String.valueOf(userId));
            if (user == null) {
                System.out.println("User not found.");
                return;
            }

            String paymentMethod = "CARD";
            adminController.updatePayment(String.valueOf(id), String.valueOf(amount), dateString, user, paymentMethod);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input for ID, amount, or userId. Please enter valid numbers.");
        } catch (DateTimeParseException e) {
            System.out.println("Date must be in a valid format (e.g., '2023-12-31T10:15:30').");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Deletes a specific payment by ID.
     * Prompts the admin to enter the payment ID and deletes the payment if found.
     */
    private void deletePayment() {
        try {
            System.out.print("Enter Payment ID to delete: ");
            String idString = scanner.nextLine();
            int id = Integer.parseInt(idString);

            adminController.deletePayment(idString);
            System.out.println("Payment deleted successfully.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid ID input. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("An error occurred while deleting the payment: " + e.getMessage());
        }
    }

    /**
     * Displays the Review Management menu and handles related operations.
     */
    public void reviewAdminMenu() {
        while (true) {
            System.out.println("Review Management:\n1. Add\n2. View\n3. Update\n4. Delete\n5. Back");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice, please enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> addReview();
                case 2 -> viewReview();
                case 3 -> updateReview();
                case 4 -> deleteReview();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    /**
     * Adds a new review.
     * Prompts the admin to select a reviewable entity, then provides details such as comment and date.
     */
    private void addReview() {
        try {
            System.out.print("Enter Review ID: ");
            String id = scanner.nextLine();
            String userId = String.valueOf(currentUser.getId());
            User user = adminController.getUserById(userId);
            if (user == null) {
                System.out.println("User not found.");
                return;
            }

            System.out.println("Select the type of Reviewable Entity:");
            System.out.println("1. Activity");
            System.out.println("2. Event");
            System.out.println("3. Free Activity");
            System.out.print("Enter your choice (1-3): ");
            String choice = scanner.nextLine();
            System.out.print("Enter Reviewable Entity ID: ");
            String entityId = scanner.nextLine();
            ReviewableEntity reviewableEntity = null;

            switch (choice) {
                case "1":
                    for (Activity activity : adminController.getAllActivities()) {
                        if (String.valueOf(activity.getId()).equals(entityId)) {
                            reviewableEntity = activity;
                            break;
                        }
                    }
                    break;

                case "2":
                    for (Event event : adminController.getAllEvents()) {
                        if (String.valueOf(event.getId()).equals(entityId)) {
                            reviewableEntity = event;
                            break;
                        }
                    }
                    break;

                case "3":
                    for (FreeActivity freeActivity : adminController.getAllFreeActivities()) {
                        if (String.valueOf(freeActivity.getId()).equals(entityId)) {
                            reviewableEntity = freeActivity;
                            break;
                        }
                    }
                    break;

                default:
                    System.out.println("Invalid choice. Please select a valid type.");
                    return;
            }

            if (reviewableEntity == null) {
                System.out.println("Reviewable Entity not found with ID " + entityId + ".");
                return;
            }

            System.out.print("Enter Comment: ");
            String comment = scanner.nextLine();
            System.out.print("Enter Review Date (e.g., 2023-12-31T10:15): ");
            String dateString = scanner.nextLine();

            adminController.addReview(id, user, reviewableEntity, comment, dateString);
            System.out.println("Review added successfully.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input for ID. Please enter valid numbers.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter the date in 'yyyy-MM-ddTHH:mm' format.");
        }
    }

    /**
     * Views reviews for a specific reviewable entity.
     * Prompts the admin to select an entity type (Activity, Event, Free Activity) and displays the reviews if available.
     */
    private void viewReview() {
        System.out.println("Select the type of reviewable entity:");
        System.out.println("1. Activity");
        System.out.println("2. Event");
        System.out.println("3. Free Activity");
        System.out.print("Enter choice (1, 2, or 3): ");

        int choice = Integer.parseInt(scanner.nextLine());
        ReviewableEntity entity = null;

        switch (choice) {
            case 2 -> {
                System.out.print("Enter Event ID: ");
                String id = scanner.nextLine();
                entity = adminController.getEventById(id);
                if (entity == null) {
                    System.out.println("Event not found.");
                    return;
                }
            }
            case 1 -> {
                System.out.print("Enter Activity ID: ");
                String id = scanner.nextLine();
                entity = adminController.getActivityById(id);
                if (entity == null) {
                    System.out.println("Activity not found.");
                    return;
                }
            }
            case 3 -> {
                System.out.print("Enter Free Activity ID: ");
                String id = scanner.nextLine();
                entity = adminController.getFreeActivityById(id);
                if (entity == null) {
                    System.out.println("Free Activity not found.");
                    return;
                }
            }
            default -> {
                System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                return;
            }
        }

        List<Review> reviews = adminController.getReviewByEvent(entity);
        if (!reviews.isEmpty()) {
            System.out.println("Reviews for selected entity:");
            for (Review review : reviews) {
                System.out.println(review);
            }
        } else {
            System.out.println("No reviews found for this entity.");
        }
    }

    /**
     * Updates a specific review.
     * Prompts the admin to provide a review ID and updated details such as comment and review date.
     */
    private void updateReview() {
        try {
            System.out.print("Enter Review ID to Update: ");
            String id = scanner.nextLine();
            System.out.print("Enter new Comment: ");
            String comment = scanner.nextLine();
            System.out.print("Enter new Review Date (e.g., 2023-12-31T10:15): ");
            String dateString = scanner.nextLine();
            adminController.updateReview(id, comment, dateString);
            System.out.println("Review updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for Review ID or other fields.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter the date in 'yyyy-MM-ddTHH:mm' format.");
        }
    }

    /**
     * Deletes a specific review by ID.
     * Prompts the admin to enter the review ID and deletes the review if found.
     */
    private void deleteReview() {
        System.out.print("Enter Review ID to delete: ");
        String id = scanner.nextLine();
        adminController.deleteReview(id);
        System.out.println("Review deleted successfully.");
    }

    /**
     * Displays the Ticket Management menu and handles related operations.
     */
    private void ticketAdminMenu() {
        while (true) {
            System.out.println("Ticket Management:\n1. Add\n2. View\n3. Update\n4. Delete\n5. Back");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addTicket();
                case 2 -> viewTicket();
                case 3 -> updateTicket();
                case 4 -> deleteTicket();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    /**
     * Adds a new ticket.
     * Prompts the admin to provide details such as ticket ID, event ID, user ID, and participant name.
     */
    private void addTicket() {
        System.out.print("Enter Ticket ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Event ID: ");
        String eventId = scanner.nextLine();
        String userId = String.valueOf(currentUser.getId());
        System.out.print("Enter Participant Name: ");
        String participantName = scanner.nextLine();

        Event event = adminController.getEventById(eventId);
        User user = adminController.getUserById(userId);

        if (event == null) {
            System.out.println("Event not found.");
            return;
        }
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        adminController.addTicket(id, event, user, participantName);
    }



    /**
     * Views a specific ticket by its ID.
     * Prompts the admin to enter a ticket ID and displays the ticket details if found.
     */
    private void viewTicket() {
        System.out.print("Enter Ticket ID: ");
        String id = scanner.nextLine();
        Ticket ticket = adminController.getTicketById(id);
        System.out.println(ticket != null ? ticket : "Ticket not found.");
    }

    /**
     * Updates a specific ticket by its ID.
     * Prompts the admin to provide updated details, such as event, user, participant name, and price.
     */
    private void updateTicket() {
        System.out.print("Enter Ticket ID to Update: ");
        String id = scanner.nextLine();
        System.out.print("Enter new Event ID: ");
        String eventId = scanner.nextLine();
        System.out.print("Enter new User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter new Participant Name: ");
        String participantName = scanner.nextLine();
        System.out.println("Enter new Price: ");
        double price = Double.parseDouble(scanner.nextLine());

        Event event = adminController.getEventById(eventId);
        User user = adminController.getUserById(userId);

        if (event == null) {
            System.out.println("Event not found.");
            return;
        }
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        adminController.updateTicket(id, event, user, participantName, price);
    }

    /**
     * Deletes a specific ticket by its ID.
     * Prompts the admin to enter a ticket ID and deletes the ticket if found.
     */
    private void deleteTicket() {
        System.out.print("Enter Ticket ID to delete: ");
        String id = scanner.nextLine();
        adminController.deleteTicket(id);
    }

    /**
     * Displays the User Management menu and handles related operations.
     * Allows adding, viewing, updating, and deleting users.
     */
    private void userAdminMenu() {
        while (true) {
            System.out.println("User Management:\n1. Add\n2. View\n3. Update\n4. Delete\n5. Back");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addUser();
                case 2 -> viewUser();
                case 3 -> updateUser();
                case 4 -> deleteUser();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    /**
     * Adds a new user.
     * Prompts the admin to provide user details, including ID, username, password, and role.
     */
    private void addUser() {
        try {
            System.out.print("Enter User ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Username: ");
            String username = scanner.nextLine();

            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            Role role = promptForRole();

            if (role == null) {
                System.out.println("Invalid role selection. Please try again.");
                return;
            }

            adminController.addUser(String.valueOf(id), username, password, role);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input for User ID. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("An error occurred while adding the user: " + e.getMessage());
        }
    }

    /**
     * Views a specific user by their ID.
     * Prompts the admin to enter a user ID and displays the user details if found.
     */
    private void viewUser() {
        System.out.print("Enter User ID: ");
        String idString = scanner.nextLine();
        User user = adminController.getUserById(idString);
        if (user != null) {
            System.out.println(user);
        } else {
            System.out.println("User not found.");
        }
    }

    /**
     * Updates a specific user by their ID.
     * Prompts the admin to provide updated details, including username, password, and role.
     */
    private void updateUser() {
        try {
            System.out.print("Enter User ID to Update: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter new Username: ");
            String username = scanner.nextLine();
            System.out.print("Enter new Password: ");
            String password = scanner.nextLine();

            Role role = promptForRole();

            if (role == null) {
                System.out.println("Invalid role selection. Please try again.");
                return;
            }

            adminController.updateUser(String.valueOf(id), username, password, role);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input for User ID. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("An error occurred while updating the user: " + e.getMessage());
        }
    }

    /**
     * Deletes a specific user by their ID.
     * Prompts the admin to enter a user ID and deletes the user if found.
     */
    private void deleteUser() {
        System.out.print("Enter User ID to delete: ");
        String idString = scanner.nextLine();
        adminController.deleteUser(idString);
    }

    /**
     * Displays the Wishlist Management menu and handles related operations.
     * Allows adding, updating, and deleting wishlists.
     */
    private void wishlistAdminMenu() {
        while (true) {
            System.out.println("Wishlist Management:\n1. Add\n2. Update\n3. Delete\n4. Back");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addWishlist();
                case 2 -> updateWishlist();
                case 3 -> deleteWishlist();
                case 4 -> {
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    /**
     * Adds a new wishlist for the current user.
     * Prompts the admin to select items to add to the wishlist.
     */
    private void addWishlist() {
        System.out.print("Enter Wishlist ID: ");
        String wishlistId = scanner.nextLine();
        String userId = String.valueOf(currentUser.getId());

        User user = adminController.getUserById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        List<ReviewableEntity> items = new ArrayList<>();

        boolean continueAdding = true;
        while (continueAdding) {
            System.out.println("Select category to add to wishlist:\n1. Event\n2. Activity\n3. Free Activity\n4. Finish adding");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addEventsToWishlist(items);
                case 2 -> addActivitiesToWishlist(items);
                case 3 -> addFreeActivitiesToWishlist(items);
                case 4 -> continueAdding = false;
                default -> System.out.println("Invalid choice, please try again.");
            }
        }

        adminController.addWishlist(wishlistId, user, items);
        System.out.println("Wishlist added successfully.");
    }

    /**
     * Adds events to the wishlist.
     * Prompts the admin to select events by ID and adds them to the provided list.
     *
     * @param items the list of items in the wishlist
     */
    private void addEventsToWishlist(List<ReviewableEntity> items) {
        List<Event> events = adminController.getAllEvents();
        if (events.isEmpty()) {
            System.out.println("No events available.");
            return;
        }

        System.out.println("Available Events:");
        for (Event event : events) {
            System.out.println("ID: " + event.getId() + " | Name: " + event.getName());
        }

        System.out.print("Enter Event ID to add (or type 'done' to finish): ");
        String eventId;
        while (!(eventId = scanner.nextLine()).equalsIgnoreCase("done")) {
            Event event = adminController.getEventById(eventId);
            if (event != null && !items.contains(event)) {
                items.add(event);
                System.out.println("Event added to wishlist.");
            } else {
                System.out.println("Invalid ID or item already added.");
            }
            System.out.print("Enter another Event ID or 'done' to finish: ");
        }
    }

    /**
     * Adds activities to the wishlist.
     * Prompts the admin to select activities by ID and adds them to the provided list.
     *
     * @param items the list of items in the wishlist
     */
    private void addActivitiesToWishlist(List<ReviewableEntity> items) {
        List<Activity> activities = adminController.getAllActivities();
        if (activities.isEmpty()) {
            System.out.println("No activities available.");
            return;
        }

        System.out.println("Available Activities:");
        for (Activity activity : activities) {
            System.out.println("ID: " + activity.getId() + " | Name: " + activity.getName());
        }

        System.out.print("Enter Activity ID to add (or type 'done' to finish): ");
        String activityId;
        while (!(activityId = scanner.nextLine()).equalsIgnoreCase("done")) {
            Activity activity = adminController.getActivityById(activityId);
            if (activity != null && !items.contains(activity)) {
                items.add(activity);
                System.out.println("Activity added to wishlist.");
            } else {
                System.out.println("Invalid ID or item already added.");
            }
            System.out.print("Enter another Activity ID or 'done' to finish: ");
        }
    }

    /**
     * Adds free activities to the wishlist.
     * Prompts the admin to select free activities by ID and adds them to the provided list.
     *
     * @param items the list of items in the wishlist
     */
    private void addFreeActivitiesToWishlist(List<ReviewableEntity> items) {
        List<FreeActivity> freeActivities = adminController.getAllFreeActivities();
        if (freeActivities.isEmpty()) {
            System.out.println("No free activities available.");
            return;
        }

        System.out.println("Available Free Activities:");
        for (FreeActivity freeActivity : freeActivities) {
            System.out.println("ID: " + freeActivity.getId() + " | Name: " + freeActivity.getName());
        }

        System.out.print("Enter Free Activity ID to add (or type 'done' to finish): ");
        String freeActivityId;
        while (!(freeActivityId = scanner.nextLine()).equalsIgnoreCase("done")) {
            FreeActivity freeActivity = adminController.getFreeActivityById(freeActivityId);
            if (freeActivity != null && !items.contains(freeActivity)) {
                items.add(freeActivity);
                System.out.println("Free Activity added to wishlist.");
            } else {
                System.out.println("Invalid ID or item already added.");
            }
            System.out.print("Enter another Free Activity ID or 'done' to finish: ");
        }
    }

    /**
     * Updates an existing wishlist.
     * Prompts the admin to select new user and items for the wishlist.
     */
    private void updateWishlist() {
        System.out.print("Enter Wishlist ID to Update: ");
        String wishlistId = scanner.nextLine();
        System.out.print("Enter new User ID: ");
        String userId = scanner.nextLine();

        User user = adminController.getUserById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        List<ReviewableEntity> items = new ArrayList<>();

        boolean continueUpdating = true;
        while (continueUpdating) {
            System.out.println("Select category to add to wishlist:\n1. Event\n2. Activity\n3. Free Activity\n4. Finish updating");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addEventsToWishlist(items);
                case 2 -> addActivitiesToWishlist(items);
                case 3 -> addFreeActivitiesToWishlist(items);
                case 4 -> continueUpdating = false;
                default -> System.out.println("Invalid choice, please try again.");
            }
        }

        adminController.updateWishlist(wishlistId, user, items);
        System.out.println("Wishlist updated successfully.");
    }

    /**
     * Deletes a wishlist by its ID.
     * Prompts the admin to enter a wishlist ID and deletes the wishlist if found.
     */
    private void deleteWishlist() {
        System.out.print("Enter Wishlist ID to delete: ");
        String id = scanner.nextLine();
        adminController.deleteWishlist(id);
    }

    /**
     * Searches events by a keyword.
     * Prompts the admin to enter a keyword and displays matching events.
     */
    private void searchEvents() {
        System.out.print("Enter keyword to search events: ");
        String keyword = scanner.nextLine();
        List<Event> events = adminController.searchEvents(keyword);
        for (Event event : events) {
            System.out.println(event);
        }
    }

    /**
     * Filters events by their type.
     * Prompts the admin to enter an event type and displays matching events.
     */
    private void filterEventsByType() {
        System.out.print("Enter Event Type (e.g., CONFERENCE): ");
        String eventType = scanner.nextLine();
        List<Event> events = adminController.filterByEventType(eventType);
        for (Event event : events) {
            System.out.println(event);
        }
    }

    /**
     * Books tickets.
     * Prompts the admin to enter a ticket ID and the number of tickets to book.
     */
    private void bookTickets() {
        System.out.print("Enter Ticket ID to book: ");
        String ticketId = scanner.nextLine();
        System.out.print("Enter number of tickets to book: ");
        String numTickets = scanner.nextLine();
        // Booking logic is missing in the provided snippet.
    }

    /**
     * Views all available tickets.
     * Displays a list of tickets that are currently available for booking.
     */
    private void viewAvailableTickets() {
        System.out.println("Available Tickets:");
        List<Ticket> tickets = adminController.getAvailableTickets();
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }

}
