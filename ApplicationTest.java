import Controller.AdminController;
import Controller.UserController;
import Domain.*;
import Parsers.*;
import Presentation.LoginUI;
import Presentation.PresentationAdmin;
import Presentation.PresentationUser;
import Repository.*;
import Service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import SQLParser.*;
import java.io.IOException;
import java.nio.file.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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

    String activityFile = "Files/activities.csv";
    String activityScheduleFile = "Files/activity_schedules.csv";
    String bookingFile = "Files/abookings.csv";
    String eventFile = "Files/aevents.csv";
    String freeActivityFile = "Files/afree_activities.csv";
    String paymentFile = "Files/apayments.csv";
    String reservationFile = "Files/areservations.csv";
    String reviewFile = "Files/areviews.csv";
    String ticketFile = "Files/atickets.csv";
    String userFile = "Files/ausers.csv";
    String wishlistFile = "Files/awishlists.csv";

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
            clearFilesInDirectory("Files");
            activityService = new ActivityService(new FileRepository<>(activityFile, activityParser));
            scheduleService = new ActivityScheduleService(new FileRepository<>(activityScheduleFile, activityScheduleParser));
            bookingService = new BookingService(new FileRepository<>(bookingFile, bookingParser));
            eventService = new EventService(new FileRepository<>(eventFile, eventParser));
            freeActivityService = new FreeActivityService(new FileRepository<>(freeActivityFile, freeActivityParser));
            paymentService = new PaymentService(new FileRepository<>(paymentFile, paymentParser));
            reservationService = new ReservationService(new FileRepository<>(reservationFile, reservationParser));
            reviewService = new ReviewService(new FileRepository<>(reviewFile, reviewParser));
            ticketService = new TicketService(new FileRepository<>(ticketFile, ticketParser));
            userService = new UserService(new FileRepository<>(userFile, userParser));
            wishlistService = new WishlistService(new FileRepository<>(wishlistFile, wishlistParser));
        } else if ("DB".equals(repoType)) {
            DatabaseConnection dbConnection = new DatabaseConnection();
            java.sql.Connection connection = dbConnection.getConnection();
            clearDatabase();
            UserSQLParser userParser = new UserSQLParser();
            DBRepository<User> userRepo = new DBRepository<>(connection, "users", userParser);

            ActivitySQLParser activityParser = new ActivitySQLParser();
            DBRepository<Activity> activityRepo = new DBRepository<>(connection, "activities", activityParser);

            ActivityScheduleSQLParser activityScheduleParser = new ActivityScheduleSQLParser(activityParser, activityRepo);
            DBRepository<ActivitySchedule> activityScheduleRepo = new DBRepository<>(connection, "activity_schedules", activityScheduleParser);

            BookingSQLParser bookingParser = new BookingSQLParser(activityScheduleRepo);

            PaymentSQLParser paymentParser = new PaymentSQLParser(userRepo);

            ReservationSQLParser reservationParser = new ReservationSQLParser(userRepo, activityScheduleRepo);

            DBRepository<Event> eventRepo = new DBRepository<>(connection, "events", new EventSQLParser());
            DBRepository<FreeActivity> freeActivityRepo = new DBRepository<>(connection, "free_activities", new FreeActivitySQLParser());

            ReviewSQLParser reviewParser = new ReviewSQLParser(
                    userRepo,
                    activityRepo,
                    eventRepo,
                    freeActivityRepo
            );

            TicketSQLParser ticketParser = new TicketSQLParser(
                    userRepo,
                    activityRepo,
                    eventRepo,
                    freeActivityRepo
            );

            WishlistSQLParser wishlistParser = new WishlistSQLParser(
                    userRepo,
                    activityRepo,
                    eventRepo,
                    freeActivityRepo
            );

            IRepository<Booking> bookingRepo = new DBRepository<>(connection, "bookings", bookingParser);
            IRepository<Payment> paymentRepo = new DBRepository<>(connection, "payments", paymentParser);
            IRepository<Reservation> reservationRepo = new DBRepository<>(connection, "reservations", reservationParser);
            IRepository<Review> reviewRepo = new DBRepository<>(connection, "reviews", reviewParser);
            IRepository<Ticket> ticketRepo = new DBRepository<>(connection, "tickets", ticketParser);
            IRepository<Wishlist> wishlistRepo = new DBRepository<>(connection, "wishlists", wishlistParser);

            ActivityService activityService = new ActivityService(activityRepo);
            ActivityScheduleService activityScheduleService = new ActivityScheduleService(activityScheduleRepo);
            BookingService bookingService = new BookingService(bookingRepo);
            EventService eventService = new EventService(eventRepo);
            FreeActivityService freeActivityService = new FreeActivityService(freeActivityRepo);
            PaymentService paymentService = new PaymentService(paymentRepo);
            ReservationService reservationService = new ReservationService(reservationRepo);
            ReviewService reviewService = new ReviewService(reviewRepo);
            TicketService ticketService = new TicketService(ticketRepo);
            UserService userService = new UserService(userRepo);
            WishlistService wishlistService = new WishlistService(wishlistRepo);

            AdminController adminController = new AdminController(
                    activityService,
                    userService,
                    activityScheduleService,
                    bookingService,
                    eventService,
                    freeActivityService,
                    paymentService,
                    reservationService,
                    reviewService,
                    ticketService,
                    wishlistService
            );

            UserController userController = new UserController(
                    activityService,
                    userService,
                    activityScheduleService,
                    bookingService,
                    eventService,
                    freeActivityService,
                    paymentService,
                    reservationService,
                    reviewService,
                    ticketService,
                    wishlistService
            );

            PresentationAdmin adminMenu = new PresentationAdmin(adminController);
            PresentationUser userMenu = new PresentationUser(userController);

            RoleBasedMenuService menuService = new RoleBasedMenuService(adminMenu, userMenu);

            LoginUI loginUI = new LoginUI(adminController, userController, menuService);
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

    @Test
    public void testViewUpcomingEvents() {
        String[] repoTypes = {"InMemory", "File", "DB"};
        clearDatabase();
        clearFilesInDirectory("Files");
        for (String repoType : repoTypes) {
            setUp(repoType);

            UserController userController = new UserController(
                    activityService,
                    userService,
                    scheduleService,
                    bookingService,
                    eventService,
                    freeActivityService,
                    paymentService,
                    reservationService,
                    reviewService,
                    ticketService,
                    wishlistService
            );

            eventService.addEvent("3", "Music Concert", "Concert Hall", "100", "ENTERTAINMENT", "0", "2024-12-15T19:00", "2024-12-15T22:00", 50.0);
            eventService.addEvent("2", "Art Exhibition", "Gallery", "50", "CULTURAL", "20", "2024-12-20T10:00", "2024-12-20T14:00", 20.0);

            List<Event> upcomingEvents = userController.getUpcomingEvents();

            assertNotNull(upcomingEvents, "Upcoming events should not be null.");
            assertEquals(2, upcomingEvents.size(), "There should be 2 upcoming events.");
        }
    }

    @Test
    public void testViewActivitySchedules() {
        String[] repoTypes = {"InMemory", "File", "DB"};
        clearDatabase();
        clearFilesInDirectory("Files");

        for (String repoType : repoTypes) {
            setUp(repoType);

            UserController userController = new UserController(
                    activityService,
                    userService,
                    scheduleService,
                    bookingService,
                    eventService,
                    freeActivityService,
                    paymentService,
                    reservationService,
                    reviewService,
                    ticketService,
                    wishlistService
            );

            activityService.addActivity("100", "Yoga Class", "30", "Room A", "SPORTS", "Morning Yoga", 15.0);
            Activity activity = activityService.getActivityById("100");
            assertNotNull(activity, "Activity should not be null after being added.");

            scheduleService.addActivitySchedule("200", activity, "2024-12-10", "09:00", "10:00", "30");
            scheduleService.addActivitySchedule("201", activity, "2024-12-11", "10:00", "11:00", "25");

            List<ActivitySchedule> schedules = scheduleService.getSchedulesForActivity(activity);
            assertEquals(2, schedules.size(), "Activity should have 2 schedules.");

            List<ActivitySchedule> fetchedSchedules = userController.getSchedulesForActivity(activity);

            assertNotNull(fetchedSchedules, "Schedules list should not be null.");
            assertEquals(2, fetchedSchedules.size(), "There should be 2 schedules available for the activity.");
            assertEquals(LocalDate.of(2024, 12, 10), fetchedSchedules.get(0).getDate(), "First schedule date should match.");
            assertEquals(LocalTime.of(9, 0), fetchedSchedules.get(0).getStartTime(), "First schedule start time should match.");
            assertEquals(30, fetchedSchedules.get(0).getAvailableCapacity(), "First schedule available capacity should match.");

            assertEquals(LocalDate.of(2024, 12, 11), fetchedSchedules.get(1).getDate(), "Second schedule date should match.");
            assertEquals(LocalTime.of(10, 0), fetchedSchedules.get(1).getStartTime(), "Second schedule start time should match.");
            assertEquals(25, fetchedSchedules.get(1).getAvailableCapacity(), "Second schedule available capacity should match.");
        }
    }





    private void clearDatabase() {
        if (dataSource != null) {
            try (Connection connection = dataSource.getConnection();
                 Statement stmt = connection.createStatement()) {
                stmt.executeUpdate("DELETE FROM tickets");
                stmt.executeUpdate("DELETE FROM reviews");
                stmt.executeUpdate("DELETE FROM reservations");
                stmt.executeUpdate("DELETE FROM payments");
                stmt.executeUpdate("DELETE FROM bookings");
                stmt.executeUpdate("DELETE FROM activity_schedules");
                stmt.executeUpdate("DELETE FROM activities");
                stmt.executeUpdate("DELETE FROM free_activities");
                stmt.executeUpdate("DELETE FROM events");
                stmt.executeUpdate("DELETE FROM wishlists");
                stmt.executeUpdate("DELETE FROM users");
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to clear database.", e);
            }
        }
    }


    public void clearFilesInDirectory(String directoryPath) {
        try {
            Path dirPath = Paths.get(directoryPath);

            if (Files.exists(dirPath) && Files.isDirectory(dirPath)) {
                Files.walk(dirPath)
                        .filter(Files::isRegularFile)
                        .forEach(file -> {
                            try {
                                Files.delete(file);
                            } catch (IOException e) {
                                e.printStackTrace();
                                throw new RuntimeException("Failed to delete file: " + file, e);
                            }
                        });
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to clear files in directory: " + directoryPath, e);
        }
    }

}