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
        while (true) {
            System.out.println("Select Role:\n1. Admin\n2. User\n3. Exit");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> adminMenu();
                case 2 -> userMenu();
                case 3 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
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


        activityController.addActivity(String.valueOf(id), name, String.valueOf(capacity), location, eventType, description);
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


        activityController.updateActivity(String.valueOf(id), name, String.valueOf(capacity), location, eventType, description);
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

            eventController.addEvent(idString, eventName, location, capacityString, eventTypeString, currentSizeString, startDateString, endDateString);
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

            eventController.updateEvent(idString, eventName, location, capacityString, eventTypeString, currentSizeString, startDateString, endDateString);
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

            System.out.print("Enter User ID: ");
            int userId = Integer.parseInt(scanner.nextLine());

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

            System.out.print("Enter User ID: ");
            int userId = Integer.parseInt(scanner.nextLine());
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
                    return;  // Exit the menu
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private void addReservation() {
        System.out.print("Enter Reservation ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter User ID: ");
        int userId = Integer.parseInt(scanner.nextLine());
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
            System.out.print("Enter User ID: ");
            String userId = scanner.nextLine();
            System.out.print("Enter Reviewable Entity ID: ");
            String entityId = scanner.nextLine();
            System.out.print("Enter Comment: ");
            String comment = scanner.nextLine();
            System.out.print("Enter Review Date (e.g., 2023-12-31T10:15): ");
            String dateString = scanner.nextLine();

            User user = userController.getUserById(userId);
            //ReviewableEntity reviewableEntity =

            if (user == null) {
                System.out.println("User not found.");
                return;
            }
            if (reviewableEntity == null) {
                System.out.println("Reviewable Entity not found.");
                return;
            }

            reviewController.addReview(id, user, reviewableEntity, comment, dateString);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for ID or Entity ID. Please enter valid numbers.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter the date in 'yyyy-MM-ddTHH:mm' format.");
        }
    }

    private void viewReview() {
        System.out.print("Enter Review ID: ");
        String id = scanner.nextLine();
        Review review = reviewController.getReviewById(id);
        if (review != null) {
            System.out.println(review);
        } else {
            System.out.println("Review not found.");
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
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
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

        ticketController.updateTicket(id, event, user, participantName);
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


private void wishlistAdminMenu() {
    while (true) {
        System.out.println("Wishlist Management:\n1. Add\n2. View\n3. Update\n4. Delete\n5. Back");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> addWishlist();
            case 2 -> viewWishlist();
            case 3 -> updateWishlist();
            case 4 -> deleteWishlist();
            case 5 -> {
                return;
            }
            default -> System.out.println("Invalid choice, please try again.");
        }
    }
}

private void addWishlist() {
    System.out.print("Enter Wishlist ID: ");
    String id = scanner.nextLine();
    System.out.print("Enter User ID: ");
    String userId = scanner.nextLine();
    System.out.print("Enter Reviewable Entity IDs (comma-separated): ");
    String entityIds = scanner.nextLine();

    User user = userController.getUserById(userId);
    if (user == null) {
        System.out.println("User not found.");
        return;
    }

    List<ReviewableEntity> items = new ArrayList<>();
    String[] entityIdArray = entityIds.split(",");
    for (String entityId : entityIdArray) {
        ReviewableEntity item = reviewableEntityService.getEntityById(entityId.trim());
        if (item != null) {
            items.add(item);
        } else {
            System.out.println("ReviewableEntity with ID " + entityId.trim() + " not found.");
        }
    }

    wishlistController.addWishlist(id, user, items);
}

private void viewWishlist() {
    System.out.print("Enter Wishlist ID: ");
    String id = scanner.nextLine();
    Wishlist wishlist = wishlistController.getWishlistById(id);
    System.out.println(wishlist != null ? wishlist : "Wishlist not found.");
}

private void updateWishlist() {
    System.out.print("Enter Wishlist ID to Update: ");
    String id = scanner.nextLine();
    System.out.print("Enter new User ID: ");
    String userId = scanner.nextLine();
    System.out.print("Enter new Reviewable Entity IDs (comma-separated): ");
    String entityIds = scanner.nextLine();

    User user = userController.getUserById(userId);
    if (user == null) {
        System.out.println("User not found.");
        return;
    }


    List<ReviewableEntity> items = new ArrayList<>();
    String[] entityIdArray = entityIds.split(",");
    for (String entityId : entityIdArray) {
        ReviewableEntity item = reviewableEntityService.getEntityById(entityId.trim());
        if (item != null) {
            items.add(item);
        } else {
            System.out.println("ReviewableEntity with ID " + entityId.trim() + " not found.");
        }
    }

    wishlistController.updateWishlist(id, user, items);
}

private void deleteWishlist() {
    System.out.print("Enter Wishlist ID to delete: ");
    String id = scanner.nextLine();
    wishlistController.deleteWishlist(id);
}


private void userMenu() {
    while (true) {
        System.out.println("User Menu:\n1. Search Events\n2. Filter by Event Type\n3. Book Tickets\n4. View Available Tickets\n5. Back");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> searchEvents();
            case 2 -> filterEventsByType();
            case 3 -> bookTickets();
            case 4 -> viewAvailableTickets();
            case 5 -> {
                return;
            }
            default -> System.out.println("Invalid choice, please try again.");
        }
    }
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
}

