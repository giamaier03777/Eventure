package Presentation;

import Controller.*;
import Domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * The PresentationUser class handles the user interface for standard users,
 * allowing them to interact with events, activities, reviews, and more.
 */
public class PresentationUser {
    private final UserController userController;
    private User currentUser = null;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Constructs the PresentationUser class with a given UserController.
     *
     * @param userController the controller used to manage user operations
     */
    public PresentationUser(UserController userController) {
        this.userController = userController;
    }

    /**
     * Starts the user interface for the current user.
     *
     * @param user the current user
     */
    public void start(User user) {
        this.currentUser = user;
        userMenu();
    }

    /**
     * Displays the main menu for the user and handles their choices.
     *
     * @return true if the user chooses to exit to the main menu
     */
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
                    "10. Sort Events by price (asc)\n" +
                    "11. Sort Events by price (desc)\n" +
                    "12. Sort Events Alphabetical\n" +
                    "13. Find the favorite tourist-spot\n" +
                    "14. View balance\n" +
                    "15. Add balance\n" +
                    "16. Back to Main Menu");
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
                case 10 -> sortByPriceAsc();
                case 11 -> sortByPriceDesc();
                case 12 -> sortEntitiesAlphabetically();
                case 13 -> showMostPopularEntities();
                case 14 -> viewBalance();
                case 15 -> addMoney();
                case 16 -> {
                    return true;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    /**
     * Displays the user's current balance.
     */
    private void viewBalance() {
        System.out.println(currentUser.getBalance());
    }

    private void addMoney() {
        System.out.println("Enter the amount to add to balance");
        double amount = Double.parseDouble(scanner.nextLine());
        currentUser.increaseBalance(amount);
    }

    /**
     * Displays the most popular events and activities based on participant count.
     */
    private void showMostPopularEntities() {
        Map<ReviewableEntity, Integer> popularEntities = userController.getMostPopularEntities();

        if (popularEntities.isEmpty()) {
            System.out.println("No data available for popular entities.");
        } else {
            System.out.println("Most Popular Entities:");
            for (Map.Entry<ReviewableEntity, Integer> entry : popularEntities.entrySet()) {
                System.out.println("Entity: " + entry.getKey().getName() + ", Total Participants: " + entry.getValue());
            }
        }
    }

    /**
     * Sorts entities alphabetically and displays the results.
     */
    private void sortEntitiesAlphabetically() {
        System.out.println("Choose the type of entity to sort:");
        System.out.println("1. Events");
        System.out.println("2. Activities");
        System.out.println("3. Free Activities");
        System.out.print("Enter choice (1, 2, or 3): ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> displaySortedEvents(userController.getEventsSortedByName());
            case 2 -> displaySortedActivities(userController.getActivitiesSortedByName());
            case 3 -> displaySortedFreeActivities(userController.getFreeActivitiesSortedByName());
            default -> System.out.println("Invalid choice. Please enter 1, 2, or 3.");
        }
    }

    /**
     * Displays sorted events.
     *
     * @param events the list of events
     */
    private void displaySortedEvents(List<Event> events) {
        if (events.isEmpty()) {
            System.out.println("No events available to sort.");
        } else {
            System.out.println("Events sorted alphabetically:");
            events.forEach(event -> System.out.println(event.getName() + " | Price: " + event.getPrice()));
        }
    }

    /**
     * Displays sorted activities.
     *
     * @param activities the list of activities
     */
    private void displaySortedActivities(List<Activity> activities) {
        if (activities.isEmpty()) {
            System.out.println("No activities available to sort.");
        } else {
            System.out.println("Activities sorted alphabetically:");
            activities.forEach(activity -> System.out.println(activity.getName() + " | Price: " + activity.getPrice()));
        }
    }

    /**
     * Displays sorted free activities.
     *
     * @param freeActivities the list of free activities
     */
    private void displaySortedFreeActivities(List<FreeActivity> freeActivities) {
        if (freeActivities.isEmpty()) {
            System.out.println("No free activities available to sort.");
        } else {
            System.out.println("Free Activities sorted alphabetically:");
            freeActivities.forEach(freeActivity -> System.out.println(freeActivity.getName()));
        }
    }

    /**
     * Sorts events by price in ascending order and displays the results.
     */
    private void sortByPriceAsc() {
        List<Event> events = userController.getEventsSortedByPriceAsc();

        if (events.isEmpty()) {
            System.out.println("No events available to sort.");
            return;
        }

        System.out.println("Events sorted by price (ascending):");
        for (Event event : events) {
            System.out.println("Event: " + event.getName() + " | Price: " + event.getPrice() + " | Date: " + event.getStartDate());
        }
    }

    /**
     * Sorts events by price in descending order and displays the results.
     */
    private void sortByPriceDesc() {
        List<Event> events = userController.getEventsSortedByPriceDesc();

        if (events.isEmpty()) {
            System.out.println("No events available to sort.");
            return;
        }

        System.out.println("Events sorted by price (descending):");
        for (Event event : events) {
            System.out.println("Event: " + event.getName() + " | Price: " + event.getPrice() + " | Date: " + event.getStartDate());
        }
    }

    /**
     * Views the details of a specific wishlist by its ID.
     */
    private void viewWishlist() {
        System.out.print("Enter Wishlist ID: ");
        String wishlistId = scanner.nextLine();
        Wishlist wishlist = userController.getWishlistById(wishlistId);
        System.out.println(wishlist);
    }

    /**
     * Views upcoming events and their available tickets.
     */
    private void viewUpcomingEvents() {
        List<Event> events = userController.getUpcomingEvents();

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

    /**
     * Displays schedules for a specific activity by its ID.
     */
    private void viewActivitySchedules() {
        System.out.print("Enter Activity ID to view schedules: ");
        String activityId = scanner.nextLine();
        Activity activity = userController.getActivityById(activityId);

        if (activity == null) {
            System.out.println("Activity not found.");
            return;
        }

        List<ActivitySchedule> schedules = userController.getSchedulesForActivity(activity);
        if (schedules.isEmpty()) {
            System.out.println("No schedules available for this activity.");
        } else {
            System.out.println("Available Schedules for " + activity.getName() + ":");
            for (ActivitySchedule schedule : schedules) {
                System.out.println("Date: " + schedule.getDate() + " | Time: " + schedule.getStartTime() + " - " + schedule.getEndTime() + " | Available Capacity: " + schedule.getAvailableCapacity());
            }
        }
    }

    /**
     * Books and processes payment for tickets for either events or activities.
     * The user selects the type of entity to book, the number of tickets, and the payment method.
     * Validates ticket availability and user balance before confirming the booking.
     */
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
                System.out.println(userController.getAllEvents());
                System.out.print("Enter Event ID to book tickets: ");
                String eventId = scanner.nextLine();
                event = userController.getEventById(eventId);

                if (event == null) {
                    System.out.println("Event not found.");
                    return;
                }
            } else if (choice == 2) {
                System.out.println("Available activities:");
                System.out.println(userController.getAllActivities());
                System.out.print("Enter Activity ID to book tickets: ");
                String activityId = scanner.nextLine();
                activity = userController.getActivityById(activityId);

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
            userController.addPayment(paymentId, String.valueOf(totalCost), LocalDateTime.now().toString(), currentUser, paymentMethod);

            System.out.println("Payment successful! Your booking is confirmed.");

            for (int i = 0; i < numTickets; i++) {
                String participantName = "Participant " + (i + 1);
                String ticketId = String.valueOf(userController.generateUniqueId());
                userController.addTicket(ticketId, event != null ? event : activity, currentUser, participantName);
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter valid numbers.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error during booking or payment: " + e.getMessage());
        }
    }

    /**
     * Filters activities based on the user's chosen criteria.
     * The user can filter activities by category or by minimum capacity.
     * Displays the filtered list of activities if matching results are found.
     */
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
                filteredActivities = userController.filterActivitiesByCategory(categoryInput);
            }
            case 2 -> {
                System.out.print("Enter minimum capacity (number of people): ");
                int minCapacity = Integer.parseInt(scanner.nextLine());
                filteredActivities = userController.filterActivitiesByCapacity(minCapacity);
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

    /**
     * Adds a review for a specified reviewable entity (activity, event, or free activity).
     * The user provides the review details, including the entity ID, comment, and review date.
     * Validates the entity and ensures it exists before adding the review.
     */
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
                    for (Activity activity : userController.getAllActivities()) {
                        if (String.valueOf(activity.getId()).equals(entityId)) {
                            reviewableEntity = activity;
                            break;
                        }
                    }
                    break;

                case "2":
                    for (Event event : userController.getAllEvents()) {
                        if (String.valueOf(event.getId()).equals(entityId)) {
                            reviewableEntity = event;
                            break;
                        }
                    }
                    break;

                case "3":
                    for (FreeActivity freeActivity : userController.getAllFreeActivities()) {
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

            userController.addReview(id, user, reviewableEntity, comment, dateString);
            System.out.println("Review added successfully.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input for ID. Please enter valid numbers.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please enter the date in 'yyyy-MM-ddTHH:mm' format.");
        }
    }

    /**
     * Views reviews for a selected reviewable entity.
     * The user selects an entity type (Activity, Event, or Free Activity), enters the entity's ID,
     * and the method fetches and displays the reviews associated with the entity.
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
                entity = userController.getEventById(id);
                if (entity == null) {
                    System.out.println("Event not found.");
                    return;
                }
            }
            case 1 -> {
                System.out.print("Enter Activity ID: ");
                String id = scanner.nextLine();
                entity = userController.getActivityById(id);
                if (entity == null) {
                    System.out.println("Activity not found.");
                    return;
                }
            }
            case 3 -> {
                System.out.print("Enter Free Activity ID: ");
                String id = scanner.nextLine();
                entity = userController.getFreeActivityById(id);
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

        List<Review> reviews = userController.getReviewByEvent(entity);
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
     * Adds an item to an existing wishlist.
     * The user selects the type of entity (Activity, Free Activity, or Event), enters the entity's ID,
     * and the selected item is added to the specified wishlist.
     */
    private void addToWishlist() {
        System.out.print("Enter Wishlist ID: ");
        String wishlistId = scanner.nextLine();

        Wishlist wishlist = userController.getWishlistById(wishlistId);
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
                entity = userController.getActivityById(activityId);
                break;
            case 2:
                System.out.print("Enter FreeActivity ID: ");
                String freeActivityId = scanner.nextLine();
                entity = userController.getFreeActivityById(freeActivityId);
                break;
            case 3:
                System.out.print("Enter Event ID: ");
                String eventId = scanner.nextLine();
                entity = userController.getEventById(eventId);
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        if (entity == null) {
            System.out.println("Entity not found.");
            return;
        }

        userController.addItemToWishlist(String.valueOf(wishlist.getId()), entity);
        System.out.println("Item added to wishlist successfully.");
    }

    /**
     * Creates a new wishlist for the current user.
     * The user enters a unique Wishlist ID, and an empty wishlist is created and associated with the user.
     */
    private void createWishlist() {
        System.out.print("Enter Wishlist ID: ");
        String id = scanner.nextLine();

        User user = userController.getUserById(String.valueOf(currentUser.getId()));
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        userController.addWishlist(id, user, new ArrayList<>());
        System.out.println("Wishlist created successfully.");
    }
}
