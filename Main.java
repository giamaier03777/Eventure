import Controller.*;
import Domain.*;
import Repository.*;
import Service.*;

public class Main {
    public static void main(String[] args) {
        ActivityRepo activityRepo = new ActivityRepo();
        ActivityScheduleRepo activityScheduleRepo = new ActivityScheduleRepo();
        BookingRepo bookingRepo = new BookingRepo();
        EventRepo eventRepo = new EventRepo();
        FreeActivityRepo freeActivityRepo = new FreeActivityRepo();
        PaymentRepo paymentRepo = new PaymentRepo();
        ReservationRepo reservationRepo = new ReservationRepo();
        ReviewRepo reviewRepo = new ReviewRepo();
        TicketRepo ticketRepo = new TicketRepo();
        UserRepo userRepo = new UserRepo();
        WishlistRepo wishlistRepo = new WishlistRepo();

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

        Presentation presentation = new Presentation(
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

        presentation.start();
    }
}
