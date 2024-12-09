import Domain.*;
import Parsers.*;
import Repository.*;
import Service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import SQLParser.*;


import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {

    private ActivityService activityService;
    private ActivityScheduleService scheduleService;
    private BookingService bookingService;
    private EventService eventService;
    private FreeActivityService freeActivityService;
    private PaymentService paymentService;
    private ReservationService reservationService;
    private ReviewService reviewService;
    private RoleBasedMenuService roleBasedMenuService;
    private TicketService ticketService;
    private UserService userService;
    private WishlistService wishlistService;

    String activityFile = "activities.csv";
    String activityScheduleFile = "activity_schedules.csv";
    String bookingFile = "bookings.csv";
    String eventFile = "events.csv";
    String freeActivityFile = "free_activities.csv";
    String paymentFile = "payments.csv";
    String reservationFile = "reservations.csv";
    String reviewFile = "reviews.csv";
    String ticketFile = "tickets.csv";
    String userFile = "users.csv";
    String wishlistFile = "wishlists.csv";

    UserParser userParser = new UserParser();
    ActivityParser activityParser = new ActivityParser();
    ActivityScheduleParser activityScheduleParser = new ActivityScheduleParser(activityParser);
    BookingParser bookingParser = new BookingParser(activityScheduleParser);
    EventParser eventParser = new EventParser();
    FreeActivityParser freeActivityParser = new FreeActivityParser();
    PaymentParser paymentParser = new PaymentParser(userParser);
    ReservationParser reservationParser = new ReservationParser(userParser, activityScheduleParser);
    ReviewParser reviewParser = new ReviewParser();
    TicketParser ticketParser = new TicketParser();
    WishlistParser wishlistParser = new WishlistParser();

    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private DataSource dataSource;

    private void setUp(String repoType) {
        if ("InMemory".equals(repoType)) {
            activityService = new ActivityService(new InMemoryRepo<>());
            scheduleService = new ActivityScheduleService(new InMemoryRepo<>());
            bookingService = new BookingService(new InMemoryRepo<>());
            eventService = new EventService(new InMemoryRepo<>());
            freeActivityService = new FreeActivityService(new InMemoryRepo<>());
            paymentService = new PaymentService(new InMemoryRepo<>());
            reservationService = new ReservationService(new InMemoryRepo<>());
            reviewService = new ReviewService(new InMemoryRepo<>());
            ticketService = new TicketService(new InMemoryRepo<>());
            userService = new UserService(new InMemoryRepo<>());
            wishlistService = new WishlistService(new InMemoryRepo<>());
        } else if ("File".equals(repoType)) {
            activityService = new ActivityService(new FileRepository<>(activityFile, activityParser));
            scheduleService = new ActivityScheduleService(new FileRepository<>(activityScheduleFile,activityScheduleParser));
            bookingService = new BookingService(new FileRepository<>(bookingFile,bookingParser));
            eventService = new EventService(new FileRepository<>(eventFile,eventParser));
            freeActivityService = new FreeActivityService(new FileRepository<>(freeActivityFile,freeActivityParser));
            paymentService = new PaymentService(new FileRepository<>(paymentFile,paymentParser));
            reservationService = new ReservationService(new FileRepository<>(reservationFile,reservationParser));
            reviewService = new ReviewService(new FileRepository<>(reviewFile,reviewParser));
            ticketService = new TicketService(new FileRepository<>(ticketFile,ticketParser));
            userService = new UserService(new FileRepository<>(userFile,userParser));
            wishlistService = new WishlistService(new FileRepository<>(wishlistFile,wishlistParser));
        } else if ("DB".equals(repoType)) {
            dataSource = databaseConnection.getDataSource();
            activityService = new ActivityService(new DBRepository<>(dataSource, "Activity",ActivitySQLParser));
            scheduleService = new ActivityScheduleService(new DBRepository<>(dataSource, "ActivitySchedule", ActivityScheduleSQLParser));
            bookingService = new BookingService(new DBRepository<>(dataSource,"Booking", BookingSQLParser));
            eventService = new EventService(new DBRepository<>(dataSource, "Event",EventSQLParser));
            freeActivityService = new FreeActivityService(new DBRepository<>(dataSource,"FreeActivity",FreeActivitySQLParser));
            paymentService = new PaymentService(new DBRepository<>(dataSource, "Payment", PaymentSQLParser));
            reservationService = new ReservationService(new DBRepository<>(dataSource, "Reservation", ReservationSQLParser));
            reviewService = new ReviewService(new DBRepository<>(dataSource, "Review", ReviewSQLParser));
            ticketService = new TicketService(new DBRepository<>(dataSource, "Ticket", TicketSQLParser));
            userService = new UserService(new DBRepository<>(dataSource, "User", UserSQLParser));
            wishlistService = new WishlistService(new DBRepository<>(dataSource, "Wishlist", WishlistSQLParser));
        }
    }

    @Test
    public void testCRUDOperationsForActivity() {
        String[] repoTypes = {"InMemory", "File", "DB"};

        for (String repoType : repoTypes) {
            setUp(repoType);
            activityService.addActivity("1", "Yoga Class", "20", "Room A", "SPORTS", "Morning Yoga", 10.0);
            Activity activity = activityService.getActivityById("1");
            assertNotNull(activity);
            assertEquals("Yoga Class", activity.getName());

            Activity fetchedActivity = activityService.getActivityById("1");
            assertEquals(activity, fetchedActivity);

            activityService.updateActivity("1", "Advanced Yoga", "25", "Room A", "SPORTS", "Advanced Morning Yoga", 12.0);
            Activity updatedActivity = activityService.getActivityById("1");
            assertEquals("Advanced Yoga", updatedActivity.getName());
            assertEquals(25, updatedActivity.getCapacity());

            activityService.deleteActivity("1");
            assertNull(activityService.getActivityById("1"));
        }
    }

    @Test
    public void testCRUDOperationsForActivitySchedule() {
        String[] repoTypes = {"InMemory", "File", "DB"};

        for (String repoType : repoTypes) {
            setUp(repoType);
            Activity activity = new Activity(1, "Yoga Class", 20, "Room A", EventType.SPORTS, "Morning Yoga", 10.0);
            activityService.addActivity("1", "Yoga Class", "20", "Room A", "SPORTS", "Morning Yoga", 10.0);

            scheduleService.addActivitySchedule("1", activity, "2024-12-10", "09:00", "10:00", "15");
            ActivitySchedule schedule = scheduleService.getActivityScheduleById("1");
            assertNotNull(schedule);
            assertEquals(LocalDate.of(2024, 12, 10), schedule.getDate());

            ActivitySchedule fetchedSchedule = scheduleService.getActivityScheduleById("1");
            assertEquals(schedule, fetchedSchedule);

            scheduleService.updateActivitySchedule("1", activity, "2024-12-11", "10:00", "11:00", "20");
            ActivitySchedule updatedSchedule = scheduleService.getActivityScheduleById("1");
            assertEquals(LocalDate.of(2024, 12, 11), updatedSchedule.getDate());

            scheduleService.deleteActivitySchedule("1");
            assertNull(scheduleService.getActivityScheduleById("1"));
        }
    }

    @Test
    public void testCRUDOperationsForBooking() {
        String[] repoTypes = {"InMemory", "File", "DB"};

        for (String repoType : repoTypes) {
            setUp(repoType);

            Activity activity = new Activity(1, "Yoga Class", 20, "Room A", EventType.SPORTS, "Morning Yoga", 10.0);
            activityService.addActivity("1", "Yoga Class", "20", "Room A", "SPORTS", "Morning Yoga", 10.0);
            scheduleService.addActivitySchedule("1", activity, "2024-12-10", "09:00", "10:00", "15");

            ActivitySchedule schedule = scheduleService.getActivityScheduleById("1");

            bookingService.addBooking("1", schedule, "John Doe", "5");
            assertNotNull(bookingService.getBookingById("1"));

            assertEquals("John Doe", bookingService.getBookingById("1").getCustomerName());

            bookingService.updateBooking("1", schedule, "Jane Doe", "6");
            assertEquals("Jane Doe", bookingService.getBookingById("1").getCustomerName());

            bookingService.deleteBooking("1");
            assertNull(bookingService.getBookingById("1"));
        }
    }

    @Test
    public void testCRUDOperationsForEvent() {
        String[] repoTypes = {"InMemory", "File", "DB"};

        for (String repoType : repoTypes) {
            setUp(repoType);

            eventService.addEvent("1", "Music Concert", "Concert Hall", "100", "ENTERTAINMENT", "0", "2024-12-15T19:00", "2024-12-15T22:00", 50.0);

            Event fetchedEvent = eventService.getEventById("1");
            assertNotNull(fetchedEvent);
            assertEquals("Music Concert", fetchedEvent.getName());

            eventService.updateEvent("1", "Jazz Concert", "Concert Hall", "150", "ENTERTAINMENT", "0", "2024-12-16T20:00", "2024-12-16T23:00", 60.0);
            Event updatedEvent = eventService.getEventById("1");
            assertEquals("Jazz Concert", updatedEvent.getName());
            assertEquals(LocalDate.of(2024, 12, 16), updatedEvent.getStartDate().toLocalDate());
            assertEquals(LocalTime.of(20, 0), updatedEvent.getStartDate().toLocalTime());

            eventService.deleteEvent("1");
            assertNull(eventService.getEventById("1"));
        }
    }

    @Test
    public void testCRUDOperationsForFreeActivity() {
        String[] repoTypes = {"InMemory", "File", "DB"};

        for (String repoType : repoTypes) {
            setUp(repoType);
            freeActivityService.addFreeActivity(1, "Yoga for Beginners", "Room A", EventType.RELAXATION, "Beginner level yoga session.");
            FreeActivity freeActivity = freeActivityService.getFreeActivityById(1);

            FreeActivity fetchedActivity = freeActivityService.getFreeActivityById(1);
            assertEquals(freeActivity, fetchedActivity, "The fetched activity should be the same as the original one.");

            freeActivityService.updateFreeActivity("1", "Advanced Yoga", "Room B", "RELAXATION", "Advanced yoga for experienced participants.");

            FreeActivity updatedActivity = freeActivityService.getFreeActivityById(1);
            assertNotNull(updatedActivity, "The updated activity should exist.");
            assertEquals("Advanced Yoga", updatedActivity.getName(), "The name should be updated.");
            assertEquals("Room B", updatedActivity.getLocation(), "The location should be updated.");
            assertEquals("Advanced yoga for experienced participants.", updatedActivity.getProgram(), "The program should be updated.");

            freeActivityService.deleteFreeActivity("1");
            FreeActivity deletedActivity = freeActivityService.getFreeActivityById(1);
            assertNull(deletedActivity, "The free activity should be deleted and not found.");
        }
    }

    @Test
    public void testCRUDOperationsForPayment() {
        String[] repoTypes = {"InMemory", "File", "DB"};

        for (String repoType : repoTypes) {
            setUp(repoType);
            UserService userService = new UserService(new InMemoryRepo<>());
            String userId = "1";
            String username = "JohnDoe";
            String password = "password123";
            Role role = Role.USER;
            userService.addUser(userId, username, password, role);

            User user = userService.getUserById(userId);

            PaymentService paymentService = new PaymentService(new InMemoryRepo<>());
            paymentService.addPayment("1", "50.0", "2024-12-09T10:00", user, "CASH");

            Payment fetchedPayment = paymentService.getPaymentById("1");
            assertNotNull(fetchedPayment);
            assertEquals(50.0, fetchedPayment.getAmount());
            assertEquals("JohnDoe", fetchedPayment.getUser().getUsername());

            User updatedUser = new User(2, "JaneDoe", "password456", Role.USER);
            paymentService.updatePayment("1", "60.0", "2024-12-09T10:00", updatedUser, "CARD");

            Payment updatedPayment = paymentService.getPaymentById("1");
            assertEquals(60.0, updatedPayment.getAmount());
            assertEquals("CARD", updatedPayment.getPaymentMethod());
            assertEquals("JaneDoe", updatedPayment.getUser().getUsername());

            paymentService.deletePayment("1");
            assertNull(paymentService.getPaymentById("1"));
        }
    }

    @Test
    public void testCRUDOperationsForReservation() {
        String[] repoTypes = {"InMemory", "File", "DB"};

        for (String repoType : repoTypes) {
            setUp(repoType);
            User user = new User(1, "John Doe", "password123", Role.USER);
            Activity activity = new Activity(1, "Yoga Class", 20, "Room A", EventType.SPORTS, "Morning Yoga", 10.0);
            ActivitySchedule schedule = new ActivitySchedule(activity, LocalDate.of(2024, 12, 9), LocalTime.of(9, 0), LocalTime.of(10, 0), 15);
            schedule.setId(1);

            LocalDateTime futureReservationDate = LocalDateTime.now().plusHours(1);

            reservationService.addReservation("1", user, schedule, "5", futureReservationDate.toString());

            Reservation fetchedReservation = reservationService.getReservationById("1");
            assertNotNull(fetchedReservation, "Reservation should exist with ID 1.");
            assertEquals(futureReservationDate, fetchedReservation.getReservationDate(), "The reservation date should match.");

            User updatedUser = new User(2, "Jane Doe", "password456", Role.USER);
            LocalDateTime updatedReservationDate = LocalDateTime.now().plusHours(2); // 2 hours from now
            reservationService.updateReservation("1", updatedUser, schedule, "6", updatedReservationDate.toString());

            Reservation updatedReservation = reservationService.getReservationById("1");
            assertNotNull(updatedReservation, "Updated reservation should exist with ID 1.");
            assertEquals(updatedReservationDate, updatedReservation.getReservationDate(), "The reservation date should be updated.");

            reservationService.deleteReservation("1");

            assertThrows(IllegalArgumentException.class, () -> {
                reservationService.getReservationById("1");
            }, "The reservation should have been deleted and should not exist.");
        }
    }

    @Test
    public void testCRUDOperationsForReview() {
        String[] repoTypes = {"InMemory", "File", "DB"};

        for (String repoType : repoTypes) {
            setUp(repoType);

            User user = new User(1, "JohnDoe", "password123", Role.USER);
            userService.addUser("1", "JohnDoe", "password123", Role.USER);

            Activity activity = new Activity(1, "Yoga Class", 20, "Room A", EventType.SPORTS, "Morning Yoga", 10.0);
            activityService.addActivity("1", "Yoga Class", "20", "Room A", "SPORTS", "Morning Yoga", 10.0);


            String reviewDate = LocalDateTime.now().minusMinutes(10).toString();
            reviewService.addReview("1", user, activity, "Great experience, highly recommend!", reviewDate);

            Review review = reviewService.getReviewById(1);
            assertNotNull(review, "Review should not be null.");
            assertEquals("Great experience, highly recommend!", review.getComment(), "Review content should match.");
            assertEquals(user, review.getUser(), "Review should belong to the correct user.");
            assertEquals(activity, review.getReviewableEntity(), "Review should be for the correct activity.");
            assertEquals(LocalDateTime.parse(reviewDate), review.getReviewDate(), "Review date should match the input date.");

            String newComment = "Excellent session, will come back again!";
            String newReviewDate = LocalDateTime.now().toString();
            reviewService.updateReview("1", newComment, newReviewDate);

            Review updatedReview = reviewService.getReviewById(1);
            assertNotNull(updatedReview, "Updated review should not be null.");
            assertEquals(newComment, updatedReview.getComment(), "Review content should be updated.");
            assertEquals(LocalDateTime.parse(newReviewDate), updatedReview.getReviewDate(), "Review date should be updated.");

            reviewService.deleteReview("1");

            Review deletedReview = reviewService.getReviewById(1);
            assertNull(deletedReview, "The review should be deleted and not found.");
        }
    }


    @Test
    public void testCRUDOperationsForTicket() {
        String[] repoTypes = {"InMemory", "File", "DB"};

        for (String repoType : repoTypes) {
            setUp(repoType);

            Event event = new Event(1, "Music Concert", "Concert Hall", 100, EventType.ENTERTAINMENT, 0, LocalDateTime.of(2024, 12, 15, 19, 0), LocalDateTime.of(2024, 12, 15, 22, 0), 50.0);
            User user = new User(1, "JohnDoe", "password123", Role.USER);

            eventService.addEvent("1", "Music Concert", "Concert Hall", "100", "ENTERTAINMENT", "0", "2024-12-15T19:00", "2024-12-15T22:00", 50.0);
            userService.addUser("1", "JohnDoe", "password123", Role.USER);

            ticketService.addTicket("1", event, user, "JaneDoe");

            Ticket fetchedTicket = ticketService.getTicketById("1");
            assertNotNull(fetchedTicket, "Ticket should not be null.");
            assertEquals("JaneDoe", fetchedTicket.getParticipantName(), "Participant name should match.");
            assertEquals("Music Concert", fetchedTicket.getEvent().getName(), "Event name should match.");

            ticketService.updateTicket("1", event, new User(2, "JaneDoe", "password456", Role.USER), "JohnSmith", 0);
            Ticket updatedTicket = ticketService.getTicketById("1");
            assertEquals("JohnSmith", updatedTicket.getParticipantName(), "Participant name should be updated.");
            assertEquals("JaneDoe", updatedTicket.getOwner().getUsername(), "Owner should match the updated user.");

            ticketService.deleteTicket("1");
            assertThrows(IllegalArgumentException.class, () -> {
                ticketService.getTicketById("1");
            }, "The ticket should have been deleted and should not exist.");
        }
    }



    @Test
    public void testCRUDOperationsForUser() {
        String[] repoTypes = {"InMemory", "File", "DB"};

        for (String repoType : repoTypes) {
            setUp(repoType);
            userService.addUser("1", "JohnDoe", "password123", Role.USER);
            User user = userService.getUserById("1");
            assertNotNull(user, "User should not be null after being added.");
            assertEquals("JohnDoe", user.getUsername(), "Username should match.");
            assertEquals("password123", user.getPassword(), "Password should match.");
            assertEquals(Role.USER, user.getRole(), "Role should match.");

            User fetchedUser = userService.getUserById("1");
            assertEquals(user, fetchedUser, "The fetched user should be the same as the created user.");

            userService.updateUser("1", "JaneDoe", "newpassword456", Role.ADMIN);
            User updatedUser = userService.getUserById("1");
            assertEquals("JaneDoe", updatedUser.getUsername(), "Username should be updated.");
            assertEquals("newpassword456", updatedUser.getPassword(), "Password should be updated.");
            assertEquals(Role.ADMIN, updatedUser.getRole(), "Role should be updated.");

            userService.deleteUser("1");
            assertThrows(IllegalArgumentException.class, () -> userService.getUserById("1"),
                    "Attempting to get a deleted user should throw an exception.");
        }
    }


    @Test
    public void testCRUDOperationsForWishlist() {
        String[] repoTypes = {"InMemory", "File", "DB"};

        for (String repoType : repoTypes) {
            setUp(repoType);
            User user = new User(1, "JohnDoe", "password123", Role.USER);
            userService.addUser("1", "JohnDoe", "password123", Role.USER);

            Activity activity1 = new Activity(1, "Yoga Class", 20, "Room A", EventType.SPORTS, "Morning Yoga", 10.0);
            Activity activity2 = new Activity(2, "Dance Class", 25, "Room B", EventType.SPORTS, "Evening Dance", 15.0);


            Wishlist wishlist = new Wishlist(1, user, new ArrayList<>(List.of(activity1, activity2)));
            wishlistService.addWishlist("1", user, new ArrayList<>(List.of(activity1, activity2)));

            Wishlist fetchedWishlist = wishlistService.getWishlistById("1");
            assertNotNull(fetchedWishlist);
            assertEquals(2, fetchedWishlist.getItems().size(), "Wishlist should contain two items.");
            assertTrue(fetchedWishlist.getItems().contains(activity1), "Wishlist should contain 'Yoga Class'.");
            assertTrue(fetchedWishlist.getItems().contains(activity2), "Wishlist should contain 'Dance Class'.");

            Activity activity3 = new Activity(3, "Cooking Class", 30, "Kitchen", EventType.EDUCATIONAL, "Cooking 101", 20.0);
            wishlistService.updateWishlist("1", user, new ArrayList<>(List.of(activity1, activity2, activity3)));

            Wishlist updatedWishlist = wishlistService.getWishlistById("1");
            assertNotNull(updatedWishlist);
            assertEquals(3, updatedWishlist.getItems().size(), "Wishlist should contain three items.");

            wishlistService.removeItemFromWishlist("1", activity2);
            Wishlist modifiedWishlist = wishlistService.getWishlistById("1");
            assertNotNull(modifiedWishlist);
            assertEquals(2, modifiedWishlist.getItems().size(), "Wishlist should contain two items after removal.");
            assertFalse(modifiedWishlist.getItems().contains(activity2), "Wishlist should not contain 'Dance Class' anymore.");

            wishlistService.deleteWishlist("1");
            assertThrows(IllegalArgumentException.class, () -> wishlistService.getWishlistById("1"), "Wishlist should be deleted.");
        }
    }

}

