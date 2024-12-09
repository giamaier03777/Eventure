package Controller;

import Domain.*;
import Service.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

/**
 * Controller class for administrative operations.
 * Provides methods for managing activities, schedules, bookings, events, free activities, payments,
 * reservations, reviews, tickets, and user wishlists.
 */
public class AdminController {
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

    /**
     * Constructs an AdminController with the required services.
     *
     * @param activityService         the service for managing activities
     * @param userService             the service for managing users
     * @param activityScheduleService the service for managing activity schedules
     * @param bookingService          the service for managing bookings
     * @param eventService            the service for managing events
     * @param freeActivityService     the service for managing free activities
     * @param paymentService          the service for managing payments
     * @param reservationService      the service for managing reservations
     * @param reviewService           the service for managing reviews
     * @param ticketService           the service for managing tickets
     * @param wishlistService         the service for managing wishlists
     */
    public AdminController(ActivityService activityService, UserService userService, ActivityScheduleService activityScheduleService,
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
     * Adds a new activity.
     *
     * @param idString        the ID of the activity as a string
     * @param activityName    the name of the activity
     * @param capacityString  the capacity of the activity as a string
     * @param location        the location of the activity
     * @param eventTypeString the type of the activity
     * @param description     a description of the activity
     * @param price           the price of the activity
     */
    public void addActivity(String idString, String activityName, String capacityString, String location, String eventTypeString, String description, double price) {
        try {
            activityService.addActivity(idString, activityName, capacityString, location, eventTypeString, description, price);
            System.out.println("Activity added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID and capacity must be valid numbers.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
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
     * Retrieves all activities.
     *
     * @return a list of all activities
     */
    public List<Activity> getAllActivities() {
        return activityService.getAllActivities();
    }

    /**
     * Updates an existing activity.
     *
     * @param idString        the ID of the activity as a string
     * @param activityName    the updated name of the activity
     * @param capacityString  the updated capacity of the activity as a string
     * @param location        the updated location of the activity
     * @param eventTypeString the updated type of the activity
     * @param description     the updated description of the activity
     * @param price           the updated price of the activity
     */
    public void updateActivity(String idString, String activityName, String capacityString, String location, String eventTypeString, String description, double price) {
        try {
            activityService.updateActivity(idString, activityName, capacityString, location, eventTypeString, description, price);
            System.out.println("Activity updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID and capacity must be valid numbers.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Deletes an activity.
     *
     * @param idString the ID of the activity as a string
     */
    public void deleteActivity(String idString) {
        try {
            activityService.deleteActivity(idString);
            System.out.println("Activity deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Adds a new activity schedule.
     *
     * @param idString        the ID of the schedule as a string
     * @param activity        the activity associated with the schedule
     * @param dateString      the date of the schedule
     * @param startTimeString the start time of the schedule
     * @param endTimeString   the end time of the schedule
     * @param capacityString  the capacity of the schedule as a string
     */
    public void addActivitySchedule(String idString, Activity activity, String dateString, String startTimeString, String endTimeString, String capacityString) {
        try {
            activityScheduleService.addActivitySchedule(idString, activity, dateString, startTimeString, endTimeString, capacityString);
            System.out.println("Activity schedule added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID and available capacity must be valid numbers.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Retrieves an activity schedule by its ID.
     *
     * @param idString the ID of the schedule as a string
     * @return the schedule if found, null otherwise
     */
    public ActivitySchedule getActivityScheduleById(String idString) {
        try {
            return activityScheduleService.getActivityScheduleById(idString);
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
            return null;
        }
    }

    /**
     * Updates an existing activity schedule.
     *
     * @param idString        the ID of the schedule as a string
     * @param activity        the updated activity
     * @param dateString      the updated date of the schedule
     * @param startTimeString the updated start time of the schedule
     * @param endTimeString   the updated end time of the schedule
     * @param capacityString  the updated capacity of the schedule as a string
     */
    public void updateActivitySchedule(String idString, Activity activity, String dateString, String startTimeString, String endTimeString, String capacityString) {
        try {
            activityScheduleService.updateActivitySchedule(idString, activity, dateString, startTimeString, endTimeString, capacityString);
            System.out.println("Activity schedule updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID and available capacity must be valid numbers.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Deletes an activity schedule.
     *
     * @param idString the ID of the schedule as a string
     */
    public void deleteActivitySchedule(String idString) {
        try {
            activityScheduleService.deleteActivitySchedule(idString);
            System.out.println("Activity schedule deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Retrieves all schedules for a specific activity.
     *
     * @param activity the activity to find schedules for
     * @return a list of schedules for the specified activity
     */
    public List<ActivitySchedule> getSchedulesForActivity(Activity activity) {
        return activityScheduleService.getSchedulesForActivity(activity);
    }


    /**
     * Adds a new booking.
     *
     * @param idString             the ID of the booking as a string
     * @param schedule             the activity schedule for the booking
     * @param customerName         the name of the customer making the booking
     * @param numberOfPeopleString the number of people included in the booking as a string
     */
    public void addBooking(String idString, ActivitySchedule schedule, String customerName, String numberOfPeopleString) {
        try {
            bookingService.addBooking(idString, schedule, customerName, numberOfPeopleString);
            System.out.println("Booking added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID and number of people must be valid numbers.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Retrieves a booking by its ID.
     *
     * @param idString the ID of the booking as a string
     * @return the booking if found, null otherwise
     */
    public Booking getBookingById(String idString) {
        try {
            return bookingService.getBookingById(idString);
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
            return null;
        }
    }

    /**
     * Updates an existing booking.
     *
     * @param idString             the ID of the booking as a string
     * @param schedule             the updated activity schedule for the booking
     * @param customerName         the updated name of the customer
     * @param numberOfPeopleString the updated number of people included in the booking as a string
     */
    public void updateBooking(String idString, ActivitySchedule schedule, String customerName, String numberOfPeopleString) {
        try {
            bookingService.updateBooking(idString, schedule, customerName, numberOfPeopleString);
            System.out.println("Booking updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID and number of people must be valid numbers.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Deletes a booking.
     *
     * @param idString the ID of the booking as a string
     */
    public void deleteBooking(String idString) {
        try {
            bookingService.deleteBooking(idString);
            System.out.println("Booking deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Adds a new event.
     *
     * @param idString          the ID of the event as a string
     * @param eventName         the name of the event
     * @param location          the location of the event
     * @param capacityString    the capacity of the event as a string
     * @param eventTypeString   the type of the event as a string
     * @param currentSizeString the current size of attendees as a string
     * @param startDateString   the start date of the event as a string
     * @param endDateString     the end date of the event as a string
     * @param price             the price per ticket for the event
     */
    public void addEvent(String idString, String eventName, String location, String capacityString, String eventTypeString, String currentSizeString, String startDateString, String endDateString, double price) {
        try {
            eventService.addEvent(idString, eventName, location, capacityString, eventTypeString, currentSizeString, startDateString, endDateString, price);
            System.out.println("Event added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID, capacity, and current size must be valid numbers.");
        } catch (DateTimeParseException e) {
            System.out.println("Start and end dates must be in a valid format (e.g., '2023-12-31T10:15:30').");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
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
     * Updates an existing event.
     *
     * @param idString          the ID of the event as a string
     * @param eventName         the updated name of the event
     * @param location          the updated location of the event
     * @param capacityString    the updated capacity of the event as a string
     * @param eventTypeString   the updated type of the event as a string
     * @param currentSizeString the updated current size of attendees as a string
     * @param startDateString   the updated start date of the event as a string
     * @param endDateString     the updated end date of the event as a string
     * @param price             the updated price per ticket for the event
     */
    public void updateEvent(String idString, String eventName, String location, String capacityString, String eventTypeString, String currentSizeString, String startDateString, String endDateString, double price) {
        try {
            eventService.updateEvent(idString, eventName, location, capacityString, eventTypeString, currentSizeString, startDateString, endDateString, price);
            System.out.println("Event updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID, capacity, and current size must be valid numbers.");
        } catch (DateTimeParseException e) {
            System.out.println("Start and end dates must be in a valid format (e.g., '2023-12-31T10:15:30').");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Deletes an event by its ID.
     *
     * @param idString the ID of the event as a string
     */
    public void deleteEvent(String idString) {
        try {
            eventService.deleteEvent(idString);
            System.out.println("Event deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Searches events based on a keyword.
     *
     * @param keyword the keyword to search for
     * @return a list of events matching the keyword
     */
    public List<Event> searchEvents(String keyword) {
        return null;
    }

    /**
     * Filters events by their type.
     *
     * @param eventType the type of events to filter by
     * @return a list of events of the specified type
     */
    public List<Event> filterByEventType(String eventType) {
        return null;
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
     * Adds a new free activity.
     *
     * @param idString        the ID of the activity as a string
     * @param name            the name of the activity
     * @param location        the location of the activity
     * @param eventTypeString the type of the activity as a string
     * @param program         the program details of the activity
     */
    public void addFreeActivity(String idString, String name, String location, String eventTypeString, String program) {
        try {
            int id = Integer.parseInt(idString);
            EventType eventType = EventType.valueOf(eventTypeString.toUpperCase());

            freeActivityService.addFreeActivity(id, name, location, eventType, program);
            System.out.println("Free activity added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
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
     * Updates an existing free activity.
     *
     * @param idString        the ID of the free activity as a string
     * @param name            the updated name of the activity
     * @param location        the updated location of the activity
     * @param eventTypeString the updated type of the activity as a string
     * @param program         the updated program details of the activity
     */
    public void updateFreeActivity(String idString, String name, String location, String eventTypeString, String program) {
        try {
            freeActivityService.updateFreeActivity(idString, name, location, eventTypeString, program);
            System.out.println("Free activity updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Deletes a free activity by its ID.
     *
     * @param idString the ID of the free activity as a string
     */
    public void deleteFreeActivity(String idString) {
        try {
            freeActivityService.deleteFreeActivity(idString);
            System.out.println("Free activity deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
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
     * Adds a new payment.
     *
     * @param idString      the ID of the payment as a string
     * @param amountString  the amount of the payment as a string
     * @param dateString    the date of the payment as a string
     * @param user          the user making the payment
     * @param paymentMethod the method of payment (e.g., "CARD", "CASH")
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
     * Retrieves a payment by its ID.
     *
     * @param idString the ID of the payment as a string
     * @return the payment if found, null otherwise
     */
    public Payment getPaymentById(String idString) {
        try {
            return paymentService.getPaymentById(idString);
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
            return null;
        }
    }

    /**
     * Updates an existing payment.
     *
     * @param idString      the ID of the payment as a string
     * @param amountString  the new amount of the payment as a string
     * @param dateString    the new date of the payment as a string
     * @param user          the user associated with the payment
     * @param paymentMethod the new payment method
     */
    public void updatePayment(String idString, String amountString, String dateString, User user, String paymentMethod) {
        try {
            paymentService.updatePayment(idString, amountString, dateString, user, paymentMethod);
            System.out.println("Payment updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID and amount must be valid numbers.");
        } catch (DateTimeParseException e) {
            System.out.println("Date must be in a valid format (e.g., '2023-12-31T10:15:30').");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Deletes a payment by its ID.
     *
     * @param idString the ID of the payment as a string
     */
    public void deletePayment(String idString) {
        try {
            paymentService.deletePayment(idString);
            System.out.println("Payment deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        }
    }

    /**
     * Adds a new reservation.
     *
     * @param idString              the ID of the reservation as a string
     * @param user                  the user making the reservation
     * @param activitySchedule      the activity schedule associated with the reservation
     * @param numberOfPeopleString  the number of people as a string
     * @param reservationDateString the reservation date as a string
     */
    public void addReservation(String idString, User user, ActivitySchedule activitySchedule, String numberOfPeopleString, String reservationDateString) {
        try {
            reservationService.addReservation(idString, user, activitySchedule, numberOfPeopleString, reservationDateString);
            System.out.println("Reservation added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID and number of people must be valid numbers.");
        } catch (DateTimeParseException e) {
            System.out.println("Reservation date must be in a valid format (e.g., '2023-12-31T10:15:30').");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Retrieves a reservation by its ID.
     *
     * @param idString the ID of the reservation as a string
     * @return the reservation if found, null otherwise
     */
    public Reservation getReservationById(String idString) {
        try {
            return reservationService.getReservationById(idString);
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
            return null;
        }
    }

    /**
     * Updates an existing reservation.
     *
     * @param idString              the ID of the reservation as a string
     * @param user                  the updated user making the reservation
     * @param activitySchedule      the updated activity schedule
     * @param numberOfPeopleString  the updated number of people as a string
     * @param reservationDateString the updated reservation date as a string
     */
    public void updateReservation(String idString, User user, ActivitySchedule activitySchedule, String numberOfPeopleString, String reservationDateString) {
        try {
            reservationService.updateReservation(idString, user, activitySchedule, numberOfPeopleString, reservationDateString);
            System.out.println("Reservation updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID and number of people must be valid numbers.");
        } catch (DateTimeParseException e) {
            System.out.println("Reservation date must be in a valid format (e.g., '2023-12-31T10:15:30').");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Deletes a reservation by its ID.
     *
     * @param idString the ID of the reservation as a string
     */
    public void deleteReservation(String idString) {
        try {
            reservationService.deleteReservation(idString);
            System.out.println("Reservation deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Adds a new review.
     *
     * @param idString         the ID of the review as a string
     * @param user             the user making the review
     * @param reviewableEntity the entity being reviewed
     * @param comment          the review comment
     * @param reviewDateString the review date as a string
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
     * Retrieves a review by its ID.
     *
     * @param idString the ID of the review as a string
     * @return the review if found, null otherwise
     */
    public Review getReviewById(String idString) {
        try {
            int id = Integer.parseInt(idString);
            return reviewService.getReviewById(id);
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
            return null;
        }
    }

    /**
     * Updates an existing review.
     *
     * @param idString            the ID of the review as a string
     * @param newComment          the updated comment
     * @param newReviewDateString the updated review date as a string
     */
    public void updateReview(String idString, String newComment, String newReviewDateString) {
        try {
            reviewService.updateReview(idString, newComment, newReviewDateString);
            System.out.println("Review updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (DateTimeParseException e) {
            System.out.println("Review date must be in a valid format (e.g., '2023-12-31T10:15:30').");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Deletes a review by its ID.
     *
     * @param idString the ID of the review as a string
     */
    public void deleteReview(String idString) {
        try {
            reviewService.deleteReview(idString);
            System.out.println("Review deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Retrieves reviews for a specific entity.
     *
     * @param event the entity to get reviews for
     * @return a list of reviews for the entity
     */
    public List<Review> getReviewByEvent(ReviewableEntity event) {
        return this.reviewService.getReviewsByEvent(event);
    }

    /**
     * Adds a new ticket.
     *
     * @param idString        the ID of the ticket as a string
     * @param event           the associated event or activity
     * @param owner           the owner of the ticket
     * @param participantName the name of the participant
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
     * Retrieves a ticket by its ID.
     *
     * @param idString the ID of the ticket as a string
     * @return the ticket if found, null otherwise
     */
    public Ticket getTicketById(String idString) {
        try {
            return ticketService.getTicketById(idString);
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
            return null;
        }
    }

    /**
     * Updates an existing ticket.
     *
     * @param idString        the ID of the ticket as a string
     * @param event           the event associated with the ticket
     * @param owner           the owner of the ticket
     * @param participantName the name of the participant
     * @param price           the price of the ticket
     */
    public void updateTicket(String idString, Event event, User owner, String participantName, double price) {
        try {
            ticketService.updateTicket(idString, event, owner, participantName, price);
            System.out.println("Ticket updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Deletes a ticket by its ID.
     *
     * @param idString the ID of the ticket as a string
     */
    public void deleteTicket(String idString) {
        try {
            ticketService.deleteTicket(idString);
            System.out.println("Ticket deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Retrieves a list of available tickets.
     *
     * @return a list of available tickets
     */
    public List<Ticket> getAvailableTickets() {
        return null;
    }


    /**
     * Adds a new user.
     *
     * @param idString  the ID of the user as a string
     * @param username  the username of the user
     * @param password  the password of the user
     * @param role      the role of the user (e.g., ADMIN, USER)
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
     * Updates an existing user.
     *
     * @param idString  the ID of the user as a string
     * @param username  the updated username
     * @param password  the updated password
     * @param role      the updated role
     */
    public void updateUser(String idString, String username, String password, Role role) {
        try {
            userService.updateUser(idString, username, password, role);
            System.out.println("User updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Deletes a user by their ID.
     *
     * @param idString the ID of the user as a string
     */
    public void deleteUser(String idString) {
        try {
            userService.deleteUser(idString);
            System.out.println("User deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
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
     * Adds a new wishlist.
     *
     * @param idString the ID of the wishlist as a string
     * @param user     the user who owns the wishlist
     * @param items    the items to add to the wishlist
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
     * Updates an existing wishlist.
     *
     * @param idString the ID of the wishlist as a string
     * @param user     the updated user who owns the wishlist
     * @param items    the updated items in the wishlist
     */
    public void updateWishlist(String idString, User user, List<ReviewableEntity> items) {
        try {
            wishlistService.updateWishlist(idString, user, items);
            System.out.println("Wishlist updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Deletes a wishlist by its ID.
     *
     * @param idString the ID of the wishlist as a string
     */
    public void deleteWishlist(String idString) {
        try {
            wishlistService.deleteWishlist(idString);
            System.out.println("Wishlist deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
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


}

