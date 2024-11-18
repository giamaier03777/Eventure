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

public class PresentationUser {
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

    public PresentationUser(ActivityController activityController, ActivityScheduleController activityScheduleController,
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
    public void start(User user) {
        this.currentUser = user;
        boolean backToMainMenu = userMenu();
        if (backToMainMenu) {
            this.currentUser = null;
        }
    }

    private boolean userMenu() {
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
                    "10. Back to Main Menu");
            int choice = Integer.parseInt(scanner.nextLine());

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
                    return true;
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
