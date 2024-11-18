import Controller.*;
import Domain.*;
import Presentation.PresentationAdmin;
import Repository.*;
import Service.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        InMemoryRepo activityRepo = new InMemoryRepo();
        InMemoryRepo activityScheduleRepo = new InMemoryRepo();
        InMemoryRepo bookingRepo = new InMemoryRepo<>();
        InMemoryRepo eventRepo = new InMemoryRepo();
        InMemoryRepo freeActivityRepo = new InMemoryRepo();
        InMemoryRepo paymentRepo = new InMemoryRepo();
        InMemoryRepo reservationRepo = new InMemoryRepo();
        InMemoryRepo reviewRepo = new InMemoryRepo();
        InMemoryRepo ticketRepo = new InMemoryRepo();
        InMemoryRepo userRepo = new InMemoryRepo();
        InMemoryRepo wishlistRepo = new InMemoryRepo();

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

        ActivityController activityController = new ActivityController(activityService);
        ActivityScheduleController activityScheduleController = new ActivityScheduleController(activityScheduleService);
        BookingController bookingController = new BookingController(bookingService);
        EventController eventController = new EventController(eventService);
        FreeActivityController freeActivityController = new FreeActivityController(freeActivityService);
        PaymentController paymentController = new PaymentController(paymentService);
        ReservationController reservationController = new ReservationController(reservationService);
        ReviewController reviewController = new ReviewController(reviewService);
        TicketController ticketController = new TicketController(ticketService);
        UserController userController = new UserController(userService);
        WishlistController wishlistController = new WishlistController(wishlistService);

        int userId = 1;
        int userId1 = 2;
        userService.addUser(userId, "user", "user", Role.USER);
        userService.addUser(userId1, "admin", "admin", Role.ADMIN);

        activityService.addActivity(101, "Yoga Class", 20, "Community Center", EventType.RELAXATION, "A relaxing yoga session.", 200);
        activityService.addActivity(102, "Cooking Workshop", 15, "Kitchen Studio", EventType.WORKSHOP, "Learn new cooking skills.", 100);
        activityService.addActivity(103, "Painting Class", 10, "Art Studio", EventType.EDUCATIONAL, "Express your creativity through art.", 100);

        Activity yogaActivity = activityService.getActivityById(101);
        activityScheduleService.addActivitySchedule(
                201, yogaActivity, LocalDate.now().plusDays(1), LocalTime.of(10, 0), LocalTime.of(12, 0), 20);

        eventService.addEvent(301, "Music Concert", "Open Air Theater", 100, EventType.ENTERTAINMENT, 0,
                LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(2).plusHours(3), 200);
        eventService.addEvent(302, "Tech Conference", "Convention Center", 200, EventType.EDUCATIONAL, 0,
                LocalDateTime.now().plusWeeks(1), LocalDateTime.now().plusWeeks(1).plusDays(2), 100);
        eventService.addEvent(303, "Food Festival", "City Square", 150, EventType.SOCIAL, 0,
                LocalDateTime.now().plusMonths(1), LocalDateTime.now().plusMonths(1).plusDays(1), 129);

        User testUser = userService.getUserById(userId);
        testUser.increaseBalance(200);
        Event concertEvent = eventService.getEventById(301);

        ticketService.addTicket(401, concertEvent, testUser, "Alice Smith");
        ticketService.addTicket(402, concertEvent, testUser, "Bob Johnson");

        PresentationAdmin presentationAdmin = new PresentationAdmin(
                activityController,
                activityScheduleController,
                bookingController,
                eventController,
                freeActivityController,
                paymentController,
                reservationController,
                reviewController,
                ticketController,
                userController,
                wishlistController
        );

        presentationAdmin.start();
    }
}

