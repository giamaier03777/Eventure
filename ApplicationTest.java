import Controller.AdminController;
import Controller.UserController;
import Domain.*;
import Parsers.*;
import Presentation.LoginUI;
import Presentation.PresentationAdmin;
import Presentation.PresentationUser;
import Repository.*;
import Service.*;
import org.junit.jupiter.api.Test;
import SQLParser.*;

import java.io.IOException;
import java.nio.file.*;
import javax.sql.DataSource;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        }
    }


    @Test
    public void testCRUDOperationsForActivity() {
        String[] repoTypes = {"InMemory"};

        for (String repoType : repoTypes) {
            setUp(repoType);

            activityService.addActivity("1", "Yoga Class", "20", "Room A", "SPORTS", "Morning Yoga", 10.0);
            Activity activity = activityService.getActivityById("1");
            assertNotNull(activity, "Activity should not be null after being added.");
            assertEquals("Yoga Class", activity.getName(), "Activity name should match.");
            assertEquals(20, activity.getCapacity(), "Activity capacity should match.");

            Activity fetchedActivity = activityService.getActivityById("1");
            assertEquals(activity, fetchedActivity, "The fetched activity should match the original activity.");

            activityService.updateActivity("1", "Advanced Yoga", "25", "Room A", "SPORTS", "Advanced Morning Yoga", 12.0);
            Activity updatedActivity = activityService.getActivityById("1");
            assertNotNull(updatedActivity, "Updated activity should not be null.");
            assertEquals("Advanced Yoga", updatedActivity.getName(), "Updated activity name should match.");
            assertEquals(25, updatedActivity.getCapacity(), "Updated activity capacity should match.");
            assertEquals(12.0, updatedActivity.getPrice(), 0.01, "Updated activity price should match.");

            activityService.deleteActivity("1");
        }
    }

    @Test
    public void testCRUDOperationsForActivitySchedule() {
        String[] repoTypes = {"InMemory"};

        for (String repoType : repoTypes) {
            setUp(repoType);
            Activity activity = new Activity(1, "Yoga Class", 20, "Room A", EventType.SPORTS, "Morning Yoga", 10.0);
            activityService.addActivity("1", "Yoga Class", "20", "Room A", "SPORTS", "Morning Yoga", 10.0);

            scheduleService.addActivitySchedule("1", activity, "2025-12-20", "09:00", "10:00", "15");
            ActivitySchedule schedule = scheduleService.getActivityScheduleById("1");
            assertNotNull(schedule);
            assertEquals(LocalDate.of(2025, 12, 20), schedule.getDate());

            ActivitySchedule fetchedSchedule = scheduleService.getActivityScheduleById("1");
            assertEquals(schedule, fetchedSchedule);

            scheduleService.updateActivitySchedule("1", activity, "2025-12-22", "10:00", "11:00", "20");
            ActivitySchedule updatedSchedule = scheduleService.getActivityScheduleById("1");
            assertEquals(LocalDate.of(2025, 12, 22), updatedSchedule.getDate());

            scheduleService.deleteActivitySchedule("1");
        }
    }

    @Test
    public void testCRUDOperationsForEvent() {
        String[] repoTypes = {"InMemory"};

        for (String repoType : repoTypes) {
            setUp(repoType);

            eventService.addEvent("1", "Music Concert", "Concert Hall", "100", "ENTERTAINMENT", "0", "2025-12-18T19:00", "2025-12-20T22:00", 50.0);

            Event fetchedEvent = eventService.getEventById("1");
            assertNotNull(fetchedEvent);
            assertEquals("Music Concert", fetchedEvent.getName());

            eventService.updateEvent("1", "Jazz Concert", "Concert Hall", "150", "ENTERTAINMENT", "0", "2025-12-20T20:00", "2025-12-21T23:00", 60.0);
            Event updatedEvent = eventService.getEventById("1");
            assertNotNull(updatedEvent);
            assertEquals("Jazz Concert", updatedEvent.getName());
            assertEquals(LocalDateTime.parse("2025-12-20T20:00"), updatedEvent.getStartDate());
            assertEquals(60.0, updatedEvent.getPrice(), 0.001);

            eventService.deleteEvent("1");
        }
    }

    @Test
    public void testCRUDOperationsForFreeActivity() {
        String[] repoTypes = {"InMemory"};

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
        }
    }

    @Test
    public void testCRUDOperationsForPayment() {
        String[] repoTypes = {"InMemory"};

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
        }
    }

    @Test
    public void testCRUDOperationsForReview() {
        String[] repoTypes = {"InMemory"};

        for (String repoType : repoTypes) {
            setUp(repoType);

            User user = new User(1, "JohnDoe", "password123", Role.USER);
            userService.addUser("1", "JohnDoe", "password123", Role.USER);

            Activity activity = new Activity(1, "Yoga Class", 20, "Room A", EventType.SPORTS, "Morning Yoga", 10.0);
            activityService.addActivity("1", "Yoga Class", "20", "Room A", "SPORTS", "Morning Yoga", 10.0);


            String reviewDate = LocalDateTime.now().minusMinutes(10).toString();
            reviewService.addReview("1", user, activity, "Great experience, highly recommend!", reviewDate);

            String newComment = "Excellent session, will come back again!";
            String newReviewDate = LocalDateTime.now().toString();
            reviewService.updateReview("1", newComment, newReviewDate);

            Review updatedReview = reviewService.getReviewById(1);
            assertNotNull(updatedReview, "Updated review should not be null.");
            assertEquals(newComment, updatedReview.getComment(), "Review content should be updated.");
            assertEquals(LocalDateTime.parse(newReviewDate), updatedReview.getReviewDate(), "Review date should be updated.");

            reviewService.deleteReview("1");
        }
    }


    @Test
    public void testCRUDOperationsForTicket() {
        String[] repoTypes = {"InMemory"};

        for (String repoType : repoTypes) {
            setUp(repoType);

            Event event = new Event(1, "Music Concert", "Concert Hall", 100, EventType.ENTERTAINMENT, 0, LocalDateTime.of(2024, 12, 15, 19, 0), LocalDateTime.of(2024, 12, 15, 22, 0), 50.0);
            User user = new User(1, "JohnDoe", "password123", Role.USER);

            eventService.addEvent("1", "Music Concert", "Concert Hall", "100", "ENTERTAINMENT", "0", "2025-12-20T19:00", "2025-12-22T22:00", 50.0);
            userService.addUser("1", "JohnDoe", "password123", Role.USER);

            ticketService.addTicket("1", event, user, "JaneDoe");


            Ticket fetchedTicket = ticketService.getTicketById("1");
            assertNotNull(fetchedTicket, "Ticket should not be null.");
            assertEquals("JaneDoe", fetchedTicket.getParticipantName(), "Participant name should match.");
            assertEquals("Music Concert", fetchedTicket.getEvent().getName(), "Event name should match.");

            ticketService.updateTicket("1", event, new User(2, "JaneDoe", "password456", Role.USER), "JohnSmith");
            Ticket updatedTicket = ticketService.getTicketById("1");
            assertEquals("JohnSmith", updatedTicket.getParticipantName(), "Participant name should be updated.");
            assertEquals("JaneDoe", updatedTicket.getOwner().getUsername(), "Owner should match the updated user.");

            ticketService.deleteTicket("1");
        }
    }


    @Test
    public void testCRUDOperationsForUser() {
        String[] repoTypes = {"InMemory"};

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
        }
    }


    @Test
    public void testCRUDOperationsForWishlist() {
        String[] repoTypes = {"InMemory"};

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

            Activity activity3 = new Activity(3, "Cooking Class", 30, "Kitchen", EventType.EDUCATIONAL, "Cooking 101", 20.0);
            wishlistService.updateWishlist("1", user, new ArrayList<>(List.of(activity1, activity2, activity3)));

            Wishlist updatedWishlist = wishlistService.getWishlistById("1");
            assertNotNull(updatedWishlist);
            assertEquals(3, updatedWishlist.getItems().size(), "Wishlist should contain three items.");

            wishlistService.updateWishlist("1", user, new ArrayList<>(List.of(activity1, activity3)));

            Wishlist modifiedWishlist = wishlistService.getWishlistById("1");
            assertNotNull(modifiedWishlist);
            assertEquals(2, modifiedWishlist.getItems().size(), "Wishlist should contain two items after removal.");

            wishlistService.deleteWishlist("1");
        }
    }


    @Test
    public void testViewUpcomingEvents() {
        String[] repoTypes = {"InMemory"};
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

            eventService.addEvent("1", "Music Concert", "Concert Hall", "100", "ENTERTAINMENT", "0", "2025-12-20T19:00", "2025-12-25T22:00", 50.0);
            eventService.addEvent("2", "Music Concert", "Concert Hall", "100", "ENTERTAINMENT", "0", "2025-12-20T19:00", "2025-12-25T22:00", 50.0);

            List<Event> upcomingEvents = userController.getUpcomingEvents();

            assertNotNull(upcomingEvents, "Upcoming events should not be null.");
            assertEquals(2, upcomingEvents.size(), "There should be 2 upcoming events.");
        }
    }

    @Test
    public void testViewActivitySchedules() {
        String[] repoTypes = {"InMemory"};

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

            scheduleService.addActivitySchedule("200", activity, "2025-12-20", "09:00", "10:00", "30");
            scheduleService.addActivitySchedule("201", activity, "2025-12-20", "10:00", "11:00", "25");

            List<ActivitySchedule> schedules = scheduleService.getSchedulesForActivity(activity);
            assertNotNull(schedules, "Schedules list should not be null.");
            assertEquals(2, schedules.size(), "Activity should have 2 schedules.");

            List<ActivitySchedule> fetchedSchedules = userController.getSchedulesForActivity(activity);

            assertNotNull(fetchedSchedules, "Fetched schedules list should not be null.");
            assertEquals(2, fetchedSchedules.size(), "There should be 2 schedules available for the activity.");

            assertEquals(LocalDate.of(2025, 12, 20), fetchedSchedules.get(0).getDate(), "First schedule date should match.");
            assertEquals(LocalTime.of(9, 0), fetchedSchedules.get(0).getStartTime(), "First schedule start time should match.");
            assertEquals(30, fetchedSchedules.get(0).getAvailableCapacity(), "First schedule available capacity should match.");

            assertEquals(LocalDate.of(2025, 12, 20), fetchedSchedules.get(1).getDate(), "Second schedule date should match.");
            assertEquals(LocalTime.of(10, 0), fetchedSchedules.get(1).getStartTime(), "Second schedule start time should match.");
            assertEquals(25, fetchedSchedules.get(1).getAvailableCapacity(), "Second schedule available capacity should match.");
        }
    }

    @Test
    public void testBookAndPayForTickets() {
        String[] repoTypes = {"InMemory"};

        for (String repoType : repoTypes) {
            setUp(repoType);

            User user = new User(1, "JohnDoe", "password123", Role.USER);
            userService.addUser("1", "JohnDoe", "password123", Role.USER);

            Event event = new Event(1, "Music Concert", "Concert Hall", 100, EventType.ENTERTAINMENT, 0, LocalDateTime.of(2024, 12, 15, 19, 0), LocalDateTime.of(2024, 12, 15, 22, 0), 50.0);
            eventService.addEvent("1", "Music Concert", "Concert Hall", "100", "ENTERTAINMENT", "0", "2025-12-18T19:00", "2025-12-22T22:00", 50.0);

            int numTickets = 2;
            double totalCost = numTickets * event.getPrice();

            paymentService.addPayment("1", String.valueOf(totalCost), LocalDateTime.now().toString(), user, "CASH");

            Payment payment = paymentService.getPaymentById("1");
            assertNotNull(payment, "Payment should not be null.");
            assertEquals(totalCost, payment.getAmount(), "Payment amount should match.");
            assertEquals("CASH", payment.getPaymentMethod(), "Payment method should match.");

            for (int i = 0; i < numTickets; i++) {
                String ticketId = String.valueOf(i + 1);
                String participantName = "Participant " + (i + 1);
                ticketService.addTicket(ticketId, event, user, participantName);

                Ticket ticket = ticketService.getTicketById(ticketId);
                assertNotNull(ticket, "Ticket should not be null.");
                assertEquals(participantName, ticket.getParticipantName(), "Ticket participant name should match.");
                assertEquals(event, ticket.getEvent(), "Ticket event should match.");
            }
        }
    }

    @Test
    public void testGetMostPopularEntities() {
        InMemoryRepo<Activity> activityRepo = new InMemoryRepo<>();
        InMemoryRepo<ActivitySchedule> scheduleRepo = new InMemoryRepo<>();
        InMemoryRepo<Booking> bookingRepo = new InMemoryRepo<>();

        Activity yogaActivity = new Activity(
                100,
                "Yoga Class",
                20,
                "Studio A",
                EventType.SPORTS,
                "A relaxing yoga session",
                15.0
        );
        Activity cookingActivity = new Activity(
                200,
                "Cooking Workshop",
                15,
                "Kitchen A",
                EventType.EDUCATIONAL,
                "Learn to cook delicious meals",
                25.0
        );

        activityRepo.create(yogaActivity);
        activityRepo.create(cookingActivity);

        ActivitySchedule schedule1 = new ActivitySchedule(
                yogaActivity,
                LocalDate.of(2025, 12, 20),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                20
        );
        schedule1.setId(1);

        ActivitySchedule schedule2 = new ActivitySchedule(
                cookingActivity,
                LocalDate.of(2025, 12, 20),
                LocalTime.of(14, 0),
                LocalTime.of(16, 0),
                15
        );
        schedule2.setId(2);

        scheduleRepo.create(schedule1);
        scheduleRepo.create(schedule2);

        Booking booking1 = new Booking(schedule1, "John Doe", 5);
        Booking booking2 = new Booking(schedule2, "Jane Doe", 8);
        Booking booking3 = new Booking(schedule1, "Alice", 3);
        booking1.setId(1);
        booking2.setId(2);
        booking3.setId(3);

        bookingRepo.create(booking1);
        bookingRepo.create(booking2);
        bookingRepo.create(booking3);

        BookingService bookingService = new BookingService(bookingRepo);

        Map<ReviewableEntity, Integer> mostPopularEntities = bookingService.getMostPopularEntities();

        assertNotNull(mostPopularEntities, "The result map should not be null.");
        assertEquals(2, mostPopularEntities.size(), "The result map should contain two activities.");

        assertEquals(8, mostPopularEntities.get(yogaActivity), "Yoga Class should have 8 participants.");
        assertEquals(8, mostPopularEntities.get(cookingActivity), "Cooking Workshop should have 8 participants.");

        List<Map.Entry<ReviewableEntity, Integer>> sortedEntries = new ArrayList<>(mostPopularEntities.entrySet());
        assertTrue(sortedEntries.contains(Map.entry(yogaActivity, 8)), "Yoga Class should be present with 8 participants.");
        assertTrue(sortedEntries.contains(Map.entry(cookingActivity, 8)), "Cooking Workshop should be present with 8 participants.");
    }

}

