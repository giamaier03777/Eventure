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

public class Presentation {

    private final ActivityController activityController;
    private final ActivityScheduleController activityScheduleController;
    private final BookingController bookingController;
    private final EventController eventController;
    private final FreeActivityController freeActivityController;
    private final PaymentController paymentController;
    private final ReservationController reservationController;
    private final ReviewController reviewController;
    private final TicketController ticketController;
    private final UserController userController;
    private final WishlistController wishlistController;
    private User currentUser = null;


    private final Scanner scanner = new Scanner(System.in);

    public Presentation(ActivityController activityController, ActivityScheduleController activityScheduleController,
                        BookingController bookingController, EventController eventController,
                        FreeActivityController freeActivityController, PaymentController paymentController,
                        ReservationController reservationController, ReviewController reviewController,
                        TicketController ticketController, UserController userController,
                        WishlistController wishlistController) {

        this.activityController = activityController;
        this.activityScheduleController = activityScheduleController;
        this.bookingController = bookingController;
        this.eventController = eventController;
        this.freeActivityController = freeActivityController;
        this.paymentController = paymentController;
        this.reservationController = reservationController;
        this.reviewController = reviewController;
        this.ticketController = ticketController;
        this.userController = userController;
        this.wishlistController = wishlistController;
    }

    public void start() {
        while (currentUser == null) {
            System.out.println("Welcome! Please choose an option:");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> login();
                case 2 -> register();
                case 3 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }

        if (currentUser != null) {
            if (currentUser.getRole() == Role.ADMIN) {
                adminMenu();
            } else {
                userMenu();
            }
        }
    }

    private void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = userController.getUserByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            System.out.println("Login successful. Welcome, " + currentUser.getUsername() + "!");
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private void register() {
        try {
            System.out.print("Choose a Username: ");
            String username = scanner.nextLine();

            System.out.print("Choose a Password: ");
            String password = scanner.nextLine();

            Role role = promptForRole();
            int id = userController.generateNewUserId();

            userController.addUser(String.valueOf(id), username, password, role);
            System.out.println("Registration successful. You can now log in.");

        } catch (Exception e) {
            System.out.println("Error during registration: " + e.getMessage());
        }
    }

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



    private void adminMenu() {
        while (true) {
            System.out.println("Admin Menu:\n1. Activity\n2. Activity Schedule\n3. Booking\n4. Event\n5. Free Activity\n6. Payment\n7. Reservation\n8. Review\n9. Ticket\n10. User\n11. Wishlist\n12. Back");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> activityAdminMenu();
                case 2 -> activityScheduleAdminMenu();
                case 3 -> bookingAdminMenu();
                case 4 -> eventAdminMenu();
                case 5 -> freeActivityAdminMenu();
                case 6 -> paymentAdminMenu();
                case 7 -> reservationAdminMenu();
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


        activityController.addActivity(String.valueOf(id), name, String.valueOf(capacity), location, eventType, description, ticketPrice);
    }

    private void viewActivity() {
        System.out.print("Enter Activity ID: ");
        String id = scanner.nextLine();
        Activity activity = activityController.getActivityById(id);
        if (activity != null) {
            System.out.println(activity);
        } else {
            System.out.println("Activity not found.");
        }
    }

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


        activityController.updateActivity(String.valueOf(id), name, String.valueOf(capacity), location, eventType, description, ticketPrice);
    }

    private void deleteActivity() {
        System.out.print("Enter Activity ID to delete: ");
        String id = scanner.nextLine();
        activityController.deleteActivity(id);
    }

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

    private void addActivitySchedule() {
        try {
            System.out.print("Enter Activity Schedule ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Activity ID: ");
            String activityId = scanner.nextLine();
            Activity activity = activityController.getActivityById(activityId);
            if (activity == null) {
                System.out.println("Activity not found with ID: " + activityId);
                return;
            }

            System.out.print("Enter Date (YYYY-MM-DD): ");
            String dateString = scanner.nextLine();
            LocalDate date = LocalDate.parse(dateString);

            System.out.print("Enter Start Time (HH:MM): ");
            String startTimeString = scanner.nextLine();
            LocalTime startTime = LocalTime.parse(startTimeString);

            System.out.print("Enter End Time (HH:MM): ");
            String endTimeString = scanner.nextLine();
            LocalTime endTime = LocalTime.parse(endTimeString);

            System.out.print("Enter Available Capacity: ");
            int capacity = Integer.parseInt(scanner.nextLine());

            activityScheduleController.addActivitySchedule(String.valueOf(id), activity, dateString, startTimeString, endTimeString, String.valueOf(capacity));
        } catch (Exception e) {
            System.out.println("Error adding activity schedule: " + e.getMessage());
        }
    }

    private void viewActivitySchedule() {
        System.out.print("Enter Activity Schedule ID: ");
        String id = scanner.nextLine();
        ActivitySchedule schedule = activityScheduleController.getActivityScheduleById(id);
        System.out.println(schedule != null ? schedule : "Schedule not found.");
    }

    private void updateActivitySchedule() {
        try {
            System.out.print("Enter Activity Schedule ID to Update: ");
            String id = scanner.nextLine();

            System.out.print("Enter Activity ID: ");
            String activityId = scanner.nextLine();
            Activity activity = activityController.getActivityById(activityId);
            if (activity == null) {
                System.out.println("Activity not found with ID: " + activityId);
                return;
            }

            System.out.print("Enter Date (YYYY-MM-DD): ");
            String dateString = scanner.nextLine();
            LocalDate date = LocalDate.parse(dateString);

            System.out.print("Enter Start Time (HH:MM): ");
            String startTimeString = scanner.nextLine();
            LocalTime startTime = LocalTime.parse(startTimeString);

            System.out.print("Enter End Time (HH:MM): ");
            String endTimeString = scanner.nextLine();
            LocalTime endTime = LocalTime.parse(endTimeString);

            System.out.print("Enter Available Capacity: ");
            int capacity = Integer.parseInt(scanner.nextLine());


            activityScheduleController.updateActivitySchedule(id, activity, dateString, startTimeString, endTimeString, String.valueOf(capacity));
        } catch (Exception e) {
            System.out.println("Error updating activity schedule: " + e.getMessage());
        }
    }

    private void deleteActivitySchedule() {
        System.out.print("Enter Activity Schedule ID to delete: ");
        String id = scanner.nextLine();
        activityScheduleController.deleteActivitySchedule(id);
    }

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
            ActivitySchedule activitySchedule = activityScheduleController.getActivityScheduleById(activityScheduleIdString);
            if (activitySchedule == null) {
                System.out.println("Activity Schedule not found with ID: " + activityScheduleIdString);
                return;
            }

            bookingController.addBooking(idString, activitySchedule, customerName, numberOfPeopleString);
        } catch (Exception e) {
            System.out.println("Error adding booking: " + e.getMessage());
        }
    }

    private void viewBooking() {
        System.out.print("Enter Booking ID: ");
        String id = scanner.nextLine();
        Booking booking = bookingController.getBookingById(id);
        System.out.println(booking != null ? booking : "Booking not found.");
    }

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
            ActivitySchedule activitySchedule = activityScheduleController.getActivityScheduleById(activityScheduleIdString);
            if (activitySchedule == null) {
                System.out.println("Activity Schedule not found with ID: " + activityScheduleIdString);
                return;
            }

            bookingController.updateBooking(id, activitySchedule, customerName, numberOfPeopleString);
        } catch (Exception e) {
            System.out.println("Error updating booking: " + e.getMessage());
        }
    }

    private void deleteBooking() {
        System.out.print("Enter Booking ID to delete: ");
        String id = scanner.nextLine();
        bookingController.deleteBooking(id);
    }

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

            eventController.addEvent(idString, eventName, location, capacityString, eventTypeString, currentSizeString, startDateString, endDateString, ticketPrice);
        } catch (Exception e) {
            System.out.println("Error adding event: " + e.getMessage());
        }
    }

    private void viewEvent() {
        System.out.print("Enter Event ID: ");
        String idString = scanner.nextLine();
        Event event = eventController.getEventById(idString);
        System.out.println(event != null ? event : "Event not found.");
    }

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

            eventController.updateEvent(idString, eventName, location, capacityString, eventTypeString, currentSizeString, startDateString, endDateString, priceString);
        } catch (Exception e) {
            System.out.println("Error updating event: " + e.getMessage());
        }
    }

    private void deleteEvent() {
        System.out.print("Enter Event ID to delete: ");
        String idString = scanner.nextLine();
        eventController.deleteEvent(idString);
    }

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

    private void addFreeActivity() {
        try {
            System.out.print("Enter Free Activity ID: ");
            String idString = scanner.nextLine();
            int id = Integer.parseInt(idString);

            System.out.print("Enter Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Location: ");
            String location = scanner.nextLine();

            System.out.print("Enter Event Type (e.g., SPORTS, MUSIC): ");
            String eventTypeString = scanner.nextLine();
            EventType eventType = EventType.valueOf(eventTypeString.toUpperCase());

            System.out.print("Enter Program Details: ");
            String program = scanner.nextLine();


            freeActivityController.addFreeActivity(idString, name, location, eventTypeString, program);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID input. Please enter a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred while adding the free activity: " + e.getMessage());
        }
    }

    private void viewFreeActivity() {
        System.out.print("Enter Free Activity ID: ");
        String idString = scanner.nextLine();
        FreeActivity freeActivity = freeActivityController.getFreeActivityById(idString);
        System.out.println(freeActivity != null ? freeActivity : "Free Activity not found.");
    }

    private void updateFreeActivity() {
        try {
            System.out.print("Enter Free Activity ID to Update: ");
            String idString = scanner.nextLine();
            int id = Integer.parseInt(idString);

            System.out.print("Enter new Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter new Location: ");
            String location = scanner.nextLine();

            System.out.print("Enter new Event Type (e.g., SPORTS, MUSIC): ");
            String eventTypeString = scanner.nextLine();
            EventType eventType = EventType.valueOf(eventTypeString.toUpperCase());

            System.out.print("Enter new Program Details: ");
            String program = scanner.nextLine();

            freeActivityController.updateFreeActivity(idString, name, location, eventTypeString, program);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID input. Please enter a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred while updating the free activity: " + e.getMessage());
        }
    }

    private void deleteFreeActivity() {
        try {
            System.out.print("Enter Free Activity ID to delete: ");
            String idString = scanner.nextLine();
            int id = Integer.parseInt(idString);

            freeActivityController.deleteFreeActivity(idString);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID input. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("An error occurred while deleting the free activity: " + e.getMessage());
        }
    }


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

    private void addPayment() {
        try {
            System.out.print("Enter Payment ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            int userId = currentUser.getId();

            User user = userController.getUserById(String.valueOf(userId));
            if (user == null) {
                System.out.println("User not found.");
                return;
            }

            System.out.print("Enter Amount: ");
            double amount = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter Payment Date (e.g., 2023-12-31T10:15:30): ");
            String dateString = scanner.nextLine();

            String paymentMethod = "CARD";
            paymentController.addPayment(String.valueOf(id), String.valueOf(amount), dateString, user, paymentMethod);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input for ID, userId, or amount. Please enter valid numbers.");
        } catch (DateTimeParseException e) {
            System.out.println("Date must be in a valid format (e.g., '2023-12-31T10:15:30').");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewPayment() {
        System.out.print("Enter Payment ID: ");
        String idString = scanner.nextLine();
        Payment payment = paymentController.getPaymentById(idString);
        if (payment != null) {
            System.out.println(payment);
        } else {
            System.out.println("Payment not found.");
        }
    }

    private void updatePayment() {
        try {
            System.out.print("Enter Payment ID to Update: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter new Amount: ");
            double amount = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter new Payment Date (e.g., 2023-12-31T10:15:30): ");
            String dateString = scanner.nextLine();

            int userId = currentUser.getId();
            User user = userController.getUserById(String.valueOf(userId));
            if (user == null) {
                System.out.println("User not found.");
                return;
            }

            String paymentMethod = "CARD";
            paymentController.updatePayment(String.valueOf(id), String.valueOf(amount), dateString, user, paymentMethod);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input for ID, amount, or userId. Please enter valid numbers.");
        } catch (DateTimeParseException e) {
            System.out.println("Date must be in a valid format (e.g., '2023-12-31T10:15:30').");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deletePayment() {
        try {
            System.out.print("Enter Payment ID to delete: ");
            String idString = scanner.nextLine();
            int id = Integer.parseInt(idString);

            paymentController.deletePayment(idString);
            System.out.println("Payment deleted successfully.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid ID input. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("An error occurred while deleting the payment: " + e.getMessage());
        }
    }

    public void reservationAdminMenu() {
        while (true) {
            System.out.println("Reservation Management:\n1. Add\n2. View\n3. Update\n4. Delete\n5. Back");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addReservation();
                case 2 -> viewReservation();
                case 3 -> updateReservation();
                case 4 -> deleteReservation();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private void addReservation() {
        System.out.print("Enter Reservation ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        int userId = currentUser.getId();
        User user = userController.getUserById(String.valueOf(userId));

        System.out.print("Enter Activity Schedule ID: ");
        int activityScheduleId = Integer.parseInt(scanner.nextLine());
        ActivitySchedule activitySchedule = activityScheduleController.getActivityScheduleById(String.valueOf(activityScheduleId));

        System.out.print("Enter Number of People: ");
        int numberOfPeople = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Reservation Date (YYYY-MM-DDTHH:MM:SS): ");
        String dateInput = scanner.nextLine();
        LocalDateTime reservationDate = LocalDateTime.parse(dateInput);

        reservationController.addReservation(String.valueOf(id), user, activitySchedule,
                String.valueOf(numberOfPeople), dateInput);
    }

    private void viewReservation() {
        System.out.print("Enter Reservation ID to View: ");
        int id = Integer.parseInt(scanner.nextLine());
        Reservation reservation = reservationController.getReservationById(String.valueOf(id));
        if (reservation != null) {
            System.out.println("Reservation Details: " + reservation);
        } else {
            System.out.println("Reservation not found.");
        }
    }

    private void updateReservation() {
        System.out.print("Enter Reservation ID to Update: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter new User ID: ");
        int userId = Integer.parseInt(scanner.nextLine());
        User user = userController.getUserById(String.valueOf(userId));

        System.out.print("Enter new Activity Schedule ID: ");
        int activityScheduleId = Integer.parseInt(scanner.nextLine());
        ActivitySchedule activitySchedule = activityScheduleController.getActivityScheduleById(String.valueOf(activityScheduleId));

        System.out.print("Enter new Number of People: ");
        int numberOfPeople = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter new Reservation Date (YYYY-MM-DDTHH:MM:SS): ");
        String dateInput = scanner.nextLine();
        LocalDateTime reservationDate = LocalDateTime.parse(dateInput);

        reservationController.updateReservation(String.valueOf(id), user, activitySchedule,
                String.valueOf(numberOfPeople), dateInput);
    }

    private void deleteReservation() {
        System.out.print("Enter Reservation ID to Delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        reservationController.deleteReservation(String.valueOf(id));
    }

    private void reviewAdminMenu() {
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

    private void addReview() {
        try {
            System.out.print("Enter Review ID: ");
            String id = scanner.nextLine();
            String userId = String.valueOf(currentUser.getId());

            User user = userController.getUserById(userId);
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
                    for (Activity activity : activityController.getAllActivities()) {
                        if (String.valueOf(activity.getId()).equals(entityId)) {
                            reviewableEntity = activity;
                            break;
                        }
                    }
                    break;

                case "2":
                    for (Event event : eventController.getAllEvents()) {
                        if (String.valueOf(event.getId()).equals(entityId)) {
                            reviewableEntity = event;
                            break;
                        }
                    }
                    break;

                case "3":
                    for (FreeActivity freeActivity : freeActivityController.getAllFreeActivities()) {
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

            reviewController.addReview(id, user, reviewableEntity, comment, dateString);
            System.out.println("Review added successfully.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input for ID. Please enter valid numbers.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter the date in 'yyyy-MM-ddTHH:mm' format.");
        }
    }



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
                entity = eventController.getEventById(id);
                if (entity == null) {
                    System.out.println("Event not found.");
                    return;
                }
            }
            case 1 -> {
                System.out.print("Enter Activity ID: ");
                String id = scanner.nextLine();
                entity = activityController.getActivityById(id);
                if (entity == null) {
                    System.out.println("Activity not found.");
                    return;
                }
            }
            case 3 -> {
                System.out.print("Enter Free Activity ID: ");
                String id = scanner.nextLine();
                entity = freeActivityController.getFreeActivityById(id);
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

        List<Review> reviews = reviewController.getReviewByEvent(entity);
        if (!reviews.isEmpty()) {
            System.out.println("Reviews for selected entity:");
            for (Review review : reviews) {
                System.out.println(review);
            }
        } else {
            System.out.println("No reviews found for this entity.");
        }
    }



    private void updateReview() {
        try {
            System.out.print("Enter Review ID to Update: ");
            String id = scanner.nextLine();
            System.out.print("Enter new Comment: ");
            String comment = scanner.nextLine();
            System.out.print("Enter new Review Date (e.g., 2023-12-31T10:15): ");
            String dateString = scanner.nextLine();
            reviewController.updateReview(id, comment, dateString);
            System.out.println("Review updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for Review ID or other fields.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter the date in 'yyyy-MM-ddTHH:mm' format.");
        }
    }

    private void deleteReview() {
        System.out.print("Enter Review ID to delete: ");
        String id = scanner.nextLine();
        reviewController.deleteReview(id);
        System.out.println("Review deleted successfully.");
    }


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

    private void addTicket() {
        System.out.print("Enter Ticket ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Event ID: ");
        String eventId = scanner.nextLine();
        String userId = String.valueOf(currentUser.getId());
        System.out.print("Enter Participant Name: ");
        String participantName = scanner.nextLine();

        Event event = eventController.getEventById(eventId);
        User user = userController.getUserById(userId);

        if (event == null) {
            System.out.println("Event not found.");
            return;
        }
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        ticketController.addTicket(id, event, user, participantName);
    }

    private void viewTicket() {
        System.out.print("Enter Ticket ID: ");
        String id = scanner.nextLine();
        Ticket ticket = ticketController.getTicketById(id);
        System.out.println(ticket != null ? ticket : "Ticket not found.");
    }

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

        Event event = eventController.getEventById(eventId);
        User user = userController.getUserById(userId);

        if (event == null) {
            System.out.println("Event not found.");
            return;
        }
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        ticketController.updateTicket(id, event, user, participantName, price);
    }

    private void deleteTicket() {
        System.out.print("Enter Ticket ID to delete: ");
        String id = scanner.nextLine();
        ticketController.deleteTicket(id);
    }

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

            userController.addUser(String.valueOf(id), username, password, role);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input for User ID. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("An error occurred while adding the user: " + e.getMessage());
        }
    }

    private void viewUser() {
        System.out.print("Enter User ID: ");
        String idString = scanner.nextLine();
        User user = userController.getUserById(idString);
        if (user != null) {
            System.out.println(user);
        } else {
            System.out.println("User not found.");
        }
    }

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

            userController.updateUser(String.valueOf(id), username, password, role);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input for User ID. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("An error occurred while updating the user: " + e.getMessage());
        }
    }

    private void deleteUser() {
        System.out.print("Enter User ID to delete: ");
        String idString = scanner.nextLine();
        userController.deleteUser(idString);
    }

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

    private void addWishlist() {
        System.out.print("Enter Wishlist ID: ");
        String wishlistId = scanner.nextLine();
        String userId = String.valueOf(currentUser.getId());

        User user = userController.getUserById(userId);
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

        wishlistController.addWishlist(wishlistId, user, items);
        System.out.println("Wishlist added successfully.");
    }

    private void addEventsToWishlist(List<ReviewableEntity> items) {
        List<Event> events = eventController.getAllEvents();
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
            Event event = eventController.getEventById(eventId);
            if (event != null && !items.contains(event)) {
                items.add(event);
                System.out.println("Event added to wishlist.");
            } else {
                System.out.println("Invalid ID or item already added.");
            }
            System.out.print("Enter another Event ID or 'done' to finish: ");
        }
    }

    private void addActivitiesToWishlist(List<ReviewableEntity> items) {
        List<Activity> activities = activityController.getAllActivities();
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
            Activity activity = activityController.getActivityById(activityId);
            if (activity != null && !items.contains(activity)) {
                items.add(activity);
                System.out.println("Activity added to wishlist.");
            } else {
                System.out.println("Invalid ID or item already added.");
            }
            System.out.print("Enter another Activity ID or 'done' to finish: ");
        }
    }

    private void addFreeActivitiesToWishlist(List<ReviewableEntity> items) {
        List<FreeActivity> freeActivities = freeActivityController.getAllFreeActivities();
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
            FreeActivity freeActivity = freeActivityController.getFreeActivityById(freeActivityId);
            if (freeActivity != null && !items.contains(freeActivity)) {
                items.add(freeActivity);
                System.out.println("Free Activity added to wishlist.");
            } else {
                System.out.println("Invalid ID or item already added.");
            }
            System.out.print("Enter another Free Activity ID or 'done' to finish: ");
        }
    }

    private void updateWishlist() {
        System.out.print("Enter Wishlist ID to Update: ");
        String wishlistId = scanner.nextLine();
        System.out.print("Enter new User ID: ");
        String userId = scanner.nextLine();

        User user = userController.getUserById(userId);
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

        wishlistController.updateWishlist(wishlistId, user, items);
        System.out.println("Wishlist updated successfully.");
    }

    private void deleteWishlist() {
        System.out.print("Enter Wishlist ID to delete: ");
        String id = scanner.nextLine();
        wishlistController.deleteWishlist(id);
    }

    private void searchEvents() {
        System.out.print("Enter keyword to search events: ");
        String keyword = scanner.nextLine();
        List<Event> events = eventController.searchEvents(keyword);
        for (Event event : events) {
            System.out.println(event);
        }
    }

    private void filterEventsByType() {
        System.out.print("Enter Event Type (e.g., CONFERENCE): ");
        String eventType = scanner.nextLine();
        List<Event> events = eventController.filterByEventType(eventType);
        for (Event event : events) {
            System.out.println(event);
        }
    }

    private void bookTickets() {
        System.out.print("Enter Ticket ID to book: ");
        String ticketId = scanner.nextLine();
        System.out.print("Enter number of tickets to book: ");
        String numTickets = scanner.nextLine();
        bookingController.bookTicket(ticketId, numTickets);
    }

    private void viewAvailableTickets() {
        System.out.println("Available Tickets:");
        List<Ticket> tickets = ticketController.getAvailableTickets();
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
    }
    private void userMenu () {
            while (true) {
                System.out.println("User Menu:\n" +
                        "1. View Upcoming Events\n" +
                        "2. View Activity Schedules\n" +
                        "3. Book and Pay for Tickets\n" +
                        "4. Filter Activities\n" +
                        "5. Add Review\n" +
                        "6. View Reviews\n" +
                        "7. Create Wishlist\n" +
                        "8. Add to Wishlist\n" +
                        "9. View Wishlist\n" +
                        "10. Back");                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> viewUpcomingEvents();
                    case 2 -> viewActivitySchedules();
                    case 3 -> bookAndPayForTickets();
                    case 4 -> filterActivities();
                    case 5 -> addReview();
                    case 6 -> viewReview();
                    case 7 -> createWishlist();
                    case 8 -> addToWishlist();
                    case 9 -> viewWishlist();
                    case 10 -> {
                        return;
                    }
                    default -> System.out.println("Invalid choice, please try again.");
                }
            }
        }

    private void viewWishlist() {
        System.out.print("Enter Wishlist ID: ");
        String wishlistId = scanner.nextLine();
        Wishlist wishlist = wishlistController.getWishlistById(wishlistId);
        System.out.println(wishlist);
    }

    private void viewUpcomingEvents() {
        List<Event> events = eventController.getUpcomingEvents();

        if (events.isEmpty()) {
            System.out.println("No upcoming events or activities available.");
        } else {
            System.out.println("Upcoming Events:");

            for (Event event : events) {
                int availableTickets = event.getCapacity() - event.getCurrentSize();
                System.out.println("Event: " + event.getName() + " | Date: " + event.getStartDate() + " | Available Tickets: " + availableTickets);
            }
        }
    }


        private void viewActivitySchedules () {
            System.out.print("Enter Activity ID to view schedules: ");
            String activityId = scanner.nextLine();
            Activity activity = activityController.getActivityById(activityId);

            if (activity == null) {
                System.out.println("Activity not found.");
                return;
            }

            List<ActivitySchedule> schedules = activityScheduleController.getSchedulesForActivity(activity);
            if (schedules.isEmpty()) {
                System.out.println("No schedules available for this activity.");
            } else {
                System.out.println("Available Schedules for " + activity.getName() + ":");
                for (ActivitySchedule schedule : schedules) {
                    System.out.println("Date: " + schedule.getDate() + " | Time: " + schedule.getStartTime() + " - " + schedule.getEndTime() + " | Available Capacity: " + schedule.getAvailableCapacity());
                }
            }
        }

    private void bookAndPayForTickets() {
        try {
            System.out.println("What would you like to book?");
            System.out.println("1. Event");
            System.out.println("2. Activity");
            System.out.print("Enter choice (1 or 2): ");
            int choice = Integer.parseInt(scanner.nextLine());

            Event event = null;
            Activity activity = null;

            if (choice == 1) {
                System.out.println("Available events:");
                System.out.println(eventController.getAllEvents());
                System.out.print("Enter Event ID to book tickets: ");
                String eventId = scanner.nextLine();
                event = eventController.getEventById(eventId);

                if (event == null) {
                    System.out.println("Event not found.");
                    return;
                }
            } else if (choice == 2) {
                System.out.println("Available activities:");
                System.out.println(activityController.getAllActivities());
                System.out.print("Enter Activity ID to book tickets: ");
                String activityId = scanner.nextLine();
                activity = activityController.getActivityById(activityId);

                if (activity == null) {
                    System.out.println("Activity not found.");
                    return;
                }
            } else {
                System.out.println("Invalid choice. Please enter 1 for Event or 2 for Activity.");
                return;
            }

            double ticketCost = (event != null) ? event.getPrice() : activity.getPrice();
            int availableTickets = (event != null) ? event.getCapacity() - event.getCurrentSize()
                    : activity.getCapacity() - activity.getCurrentSize();

            System.out.print("Enter number of tickets to book: ");
            int numTickets = Integer.parseInt(scanner.nextLine());

            if (availableTickets < numTickets) {
                System.out.println("Insufficient tickets available. Only " + availableTickets + " tickets left.");
                return;
            }

            double totalCost = numTickets * ticketCost;
            System.out.println("You have to pay: " + totalCost + " euro.");
            if (currentUser.getBalance() < totalCost) {
                System.out.println("Insufficient balance. You need " + totalCost + " euros, but have only " + currentUser.getBalance() + " euros.");
                return;
            }

            System.out.println("Select payment method:");
            System.out.println("1. CASH");
            System.out.println("2. CARD");
            System.out.print("Enter choice (1 or 2): ");
            int paymentChoice = Integer.parseInt(scanner.nextLine());
            String paymentMethod = (paymentChoice == 1) ? "CASH" : "CARD";

            currentUser.setBalance(currentUser.getBalance() - totalCost);

            String paymentId = (event != null) ? String.valueOf(event.getId()) : String.valueOf(activity.getId());
            paymentController.addPayment(paymentId, String.valueOf(totalCost), LocalDateTime.now().toString(), currentUser, paymentMethod);

            System.out.println("Payment successful! Your booking is confirmed.");

            for (int i = 0; i < numTickets; i++) {
                String participantName = "Participant " + (i + 1);
                String ticketId = String.valueOf(ticketController.generateUniqueId());
                ticketController.addTicket(ticketId, event != null ? event : activity, currentUser, participantName);
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid numbers.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error during booking or payment: " + e.getMessage());
        }
    }


    private void filterActivities() {
        System.out.println("Choose filter option:");
        System.out.println("1. Filter by category");
        System.out.println("2. Filter by minimum capacity");
        System.out.print("Enter choice (1 or 2): ");
        int choice = Integer.parseInt(scanner.nextLine());

        List<Activity> filteredActivities;

        switch (choice) {
            case 1 -> {
                System.out.print("Enter category (e.g., RELAXATION, WORKSHOP, ENTERTAINMENT): ");
                String categoryInput = scanner.nextLine().toUpperCase();
                filteredActivities = activityController.filterActivitiesByCategory(categoryInput);
            }
            case 2 -> {
                System.out.print("Enter minimum capacity (number of people): ");
                int minCapacity = Integer.parseInt(scanner.nextLine());
                filteredActivities = activityController.filterActivitiesByCapacity(minCapacity);
            }
            default -> {
                System.out.println("Invalid choice. Returning to menu.");
                return;
            }
        }

        if (filteredActivities.isEmpty()) {
            System.out.println("No activities match the given criteria.");
        } else {
            System.out.println("Filtered Activities:");
            for (Activity activity : filteredActivities) {
                System.out.println(activity);
            }
        }
    }

    private void addToWishlist() {
        System.out.print("Enter Wishlist ID: ");
        String wishlistId = scanner.nextLine();

        Wishlist wishlist = wishlistController.getWishlistById(wishlistId);
        if (wishlist == null) {
            System.out.println("Wishlist not found.");
            return;
        }

        System.out.println("Choose the type of entity to add:");
        System.out.println("1. Activity");
        System.out.println("2. FreeActivity");
        System.out.println("3. Event");
        System.out.print("Enter choice (1, 2, or 3): ");
        int choice = Integer.parseInt(scanner.nextLine());

        ReviewableEntity entity = null;

        switch (choice) {
            case 1:
                System.out.print("Enter Activity ID: ");
                String activityId = scanner.nextLine();
                entity = activityController.getActivityById(activityId);
                break;
            case 2:
                System.out.print("Enter FreeActivity ID: ");
                String freeActivityId = scanner.nextLine();
                entity = freeActivityController.getFreeActivityById(freeActivityId);
                break;
            case 3:
                System.out.print("Enter Event ID: ");
                String eventId = scanner.nextLine();
                entity = eventController.getEventById(eventId);
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        if (entity == null) {
            System.out.println("Entity not found.");
            return;
        }

        wishlistController.addItemToWishlist(String.valueOf(wishlist.getId()), entity);
        System.out.println("Item added to wishlist successfully.");
    }

    private void createWishlist() {
        System.out.print("Enter Wishlist ID: ");
        String id = scanner.nextLine();

        User user = userController.getUserById(String.valueOf(currentUser.getId()));
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        wishlistController.addWishlist(id, user, new ArrayList<>() );
        System.out.println("Wishlist created successfully.");
    }

}