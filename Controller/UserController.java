package Controller;

import Domain.*;
import Service.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

/**
 * Controller for user-related operations, providing methods to interact with activities,
 * events, tickets, payments, reviews, and wishlists.
 */
public class UserController {

    private final ActivityService activityService;
    private final UserService userService;
    private final ActivityScheduleService activityScheduleService;
    private final BookingService bookingService;
    private final EventService eventService;
    private final FreeActivityService freeActivityService;
    private final PaymentService paymentService;
    private final ReservationService reservationService;
    private final ReviewService reviewService;
    private final TicketService ticketService;
    private final WishlistService wishlistService;

    public UserController(ActivityService activityService, UserService userService, ActivityScheduleService activityScheduleService,
                          BookingService bookingService, EventService eventService, FreeActivityService freeActivityService,
                          PaymentService paymentService, ReservationService reservationService, ReviewService reviewService,
                          TicketService ticketService, WishlistService wishlistService) {
        this.activityService = activityService;
        this.userService = userService;
        this.activityScheduleService = activityScheduleService;
        this.bookingService = bookingService;
        this.eventService = eventService;
        this.freeActivityService = freeActivityService;
        this.paymentService = paymentService;
        this.reservationService = reservationService;
        this.reviewService = reviewService;
        this.ticketService = ticketService;
        this.wishlistService = wishlistService;
    }

    /**
     * Retrieves an activity by its ID.
     *
     * @param idString the ID of the activity as a string
     * @return the activity if found, null otherwise
     */
    public Activity getActivityById(String idString) {
        try {
            return activityService.getActivityById(idString);
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
            return null;
        }
    }

    /**
     * Retrieves a list of all activities.
     *
     * @return a list of all activities
     */
    public List<Activity> getAllActivities() {
        return activityService.getAllActivities();
    }

    /**
     * Retrieves schedules for a given activity.
     *
     * @param activity the activity for which schedules are to be retrieved
     * @return a list of activity schedules
     */
    public List<ActivitySchedule> getSchedulesForActivity(Activity activity) {
        return activityScheduleService.getSchedulesForActivity(activity);
    }

    /**
     * Retrieves an event by its ID.
     *
     * @param idString the ID of the event as a string
     * @return the event if found, null otherwise
     */
    public Event getEventById(String idString) {
        try {
            return eventService.getEventById(idString);
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
            return null;
        }
    }

    /**
     * Retrieves a list of upcoming events.
     *
     * @return a list of upcoming events
     */
    public List<Event> getUpcomingEvents() {
        return eventService.getUpcomingEvents();
    }

    /**
     * Retrieves a list of all events.
     *
     * @return a list of all events
     */
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    /**
     * Retrieves a free activity by its ID.
     *
     * @param idString the ID of the free activity as a string
     * @return the free activity if found, null otherwise
     */
    public FreeActivity getFreeActivityById(String idString) {
        try {
            int id = Integer.parseInt(idString);
            return freeActivityService.getFreeActivityById(id);
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
            return null;
        }
    }

    /**
     * Retrieves a list of all free activities.
     *
     * @return a list of all free activities
     */
    public List<FreeActivity> getAllFreeActivities() {
        return freeActivityService.getAllFreeActivities();
    }

    /**
     * Adds a payment for a user.
     *
     * @param idString      the ID of the payment as a string
     * @param amountString  the amount of the payment as a string
     * @param dateString    the date of the payment as a string
     * @param user          the user making the payment
     * @param paymentMethod the method of payment
     */
    public void addPayment(String idString, String amountString, String dateString, User user, String paymentMethod) {
        try {
            paymentService.addPayment(idString, amountString, dateString, user, paymentMethod);
            System.out.println("Payment added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID and amount must be valid numbers.");
        } catch (DateTimeParseException e) {
            System.out.println("Date must be in a valid format (e.g., '2023-12-31T10:15:30').");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Adds a review for a reviewable entity.
     *
     * @param idString         the ID of the review as a string
     * @param user             the user making the review
     * @param reviewableEntity the entity being reviewed
     * @param comment          the comment for the review
     * @param reviewDateString the date of the review as a string
     */
    public void addReview(String idString, User user, ReviewableEntity reviewableEntity, String comment, String reviewDateString) {
        try {
            reviewService.addReview(idString, user, reviewableEntity, comment, reviewDateString);
            System.out.println("Review added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (DateTimeParseException e) {
            System.out.println("Review date must be in a valid format (e.g., '2023-12-31T10:15:30').");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Retrieves reviews for a specific event.
     *
     * @param event the reviewable entity (event) to get reviews for
     * @return a list of reviews for the specified event
     */
    public List<Review> getReviewByEvent(ReviewableEntity event) {
        return this.reviewService.getReviewsByEvent(event);
    }

    /**
     * Adds a ticket for an event or activity.
     *
     * @param idString        the ID of the ticket as a string
     * @param event           the event or activity associated with the ticket
     * @param owner           the owner of the ticket
     * @param participantName the name of the participant for the ticket
     */
    public void addTicket(String idString, ReviewableEntity event, User owner, String participantName) {
        try {
            ticketService.addTicket(idString, event, owner, participantName);
            System.out.println("Ticket added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Adds a new user to the system.
     *
     * @param idString the ID of the user as a string
     * @param username the username of the new user
     * @param password the password for the new user
     * @param role     the role of the new user (e.g., ADMIN, USER)
     */
    public void addUser(String idString, String username, String password, Role role) {
        try {
            userService.addUser(idString, username, password, role);
            System.out.println("User added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param idString the ID of the user as a string
     * @return the user if found, null otherwise
     */
    public User getUserById(String idString) {
        try {
            return userService.getUserById(idString);
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
            return null;
        }
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user
     * @return the user if found, null otherwise
     */
    public User getUserByUsername(String username) {
        return userService.getUserByUsername(username);
    }

    /**
     * Generates a new unique user ID.
     *
     * @return a new unique user ID
     */
    public int generateNewUserId() {
        return userService.generateNewUntakenId();
    }

    /**
     * Adds a wishlist for a user.
     *
     * @param idString the ID of the wishlist as a string
     * @param user     the owner of the wishlist
     * @param items    the initial items in the wishlist
     */
    public void addWishlist(String idString, User user, List<ReviewableEntity> items) {
        try {
            wishlistService.addWishlist(idString, user, items);
            System.out.println("Wishlist added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Retrieves a wishlist by its ID.
     *
     * @param idString the ID of the wishlist as a string
     * @return the wishlist if found, null otherwise
     */
    public Wishlist getWishlistById(String idString) {
        try {
            return wishlistService.getWishlistById(idString);
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
            return null;
        }
    }

    /**
     * Adds an item to a wishlist.
     *
     * @param idString the ID of the wishlist as a string
     * @param entity   the item to be added to the wishlist
     */
    public void addItemToWishlist(String idString, ReviewableEntity entity) {
        try {
            wishlistService.addItemToWishlist(idString, entity);
            System.out.println("Item added to wishlist successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Filters activities by their capacity.
     *
     * @param minCapacity the minimum capacity for filtering activities
     * @return a list of activities that meet the capacity requirement
     */
    public List<Activity> filterActivitiesByCapacity(int minCapacity) {
        try {
            return activityService.filterActivitiesByCapacity(minCapacity);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Filters activities by their category.
     *
     * @param categoryInput the category to filter activities by
     * @return a list of activities in the specified category
     */
    public List<Activity> filterActivitiesByCategory(String categoryInput) {
        try {
            return activityService.filterActivitiesByCategory(categoryInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Generates a unique ID for a ticket.
     *
     * @return the generated unique ID as a string
     */
    public String generateUniqueId() {
        try {
            return String.valueOf(ticketService.generateUniqueId());
        } catch (Exception e) {
            System.out.println("Error generating unique ID: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves events sorted by price in ascending order.
     *
     * @return a list of events sorted by price (ascending)
     */
    public List<Event> getEventsSortedByPriceAsc() {
        return eventService.getEventsSortedByPriceAsc();
    }

    /**
     * Retrieves events sorted by price in descending order.
     *
     * @return a list of events sorted by price (descending)
     */
    public List<Event> getEventsSortedByPriceDesc() {
        return eventService.getEventsSortedByPriceDesc();
    }

    /**
     * Retrieves events sorted by name.
     *
     * @return a list of events sorted by name
     */
    public List<Event> getEventsSortedByName() {
        return eventService.getEventsSortedByName();
    }

    /**
     * Retrieves activities sorted by name.
     *
     * @return a list of activities sorted by name
     */
    public List<Activity> getActivitiesSortedByName() {
        return activityService.getActivitiesSortedByName();
    }

    /**
     * Retrieves free activities sorted by name.
     *
     * @return a list of free activities sorted by name
     */
    public List<FreeActivity> getFreeActivitiesSortedByName() {
        return freeActivityService.getFreeActivitiesSortedByName();
    }

    /**
     * Retrieves the most popular entities based on booking counts.
     *
     * @return a map of reviewable entities to their popularity counts
     */
    public Map<ReviewableEntity, Integer> getMostPopularEntities() {
        return bookingService.getMostPopularEntities();
    }

    public void bookAndPayForTickets(User currentUser, int choice, String entityId, int numTickets, String paymentMethod) {
        try {
            if (choice != 1 && choice != 2) {
                throw new IllegalArgumentException("Invalid choice. Please select 1 for Event or 2 for Activity.");
            }

            Event event = null;
            Activity activity = null;

            if (choice == 1) {
                event = getEventById(entityId);
                if (event == null) {
                    throw new IllegalArgumentException("Event not found.");
                }
            } else {
                activity = getActivityById(entityId);
                if (activity == null) {
                    throw new IllegalArgumentException("Activity not found.");
                }
            }

            double ticketCost = (event != null) ? event.getPrice() : activity.getPrice();
            int availableTickets = (event != null)
                    ? event.getCapacity() - event.getCurrentSize()
                    : activity.getCapacity() - activity.getCurrentSize();

            if (availableTickets < numTickets) {
                throw new IllegalArgumentException("Insufficient tickets available. Only " + availableTickets + " tickets left.");
            }

            double totalCost = numTickets * ticketCost;
            if (currentUser.getBalance() < totalCost) {
                throw new IllegalArgumentException("Insufficient balance. You need " + totalCost + " euros, but have only " + currentUser.getBalance() + " euros.");
            }

            currentUser.setBalance(currentUser.getBalance() - totalCost);

            String paymentId = (event != null) ? String.valueOf(event.getId()) : String.valueOf(activity.getId());
            addPayment(paymentId, String.valueOf(totalCost), LocalDateTime.now().toString(), currentUser, paymentMethod);

            for (int i = 0; i < numTickets; i++) {
                String participantName = "Participant " + (i + 1);
                String ticketId = String.valueOf(generateUniqueId());
                addTicket(ticketId, event != null ? event : activity, currentUser, participantName);
            }

            System.out.println("Payment successful! Your booking is confirmed.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Booking failed: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred during booking or payment.", e);
        }
    }



}