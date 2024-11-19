import Controller.*;
import Domain.*;
import Presentation.LoginUI;
import Presentation.PresentationAdmin;
import Presentation.PresentationUser;
import Repository.*;
import Service.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Main {
    public static void startInMemory(){
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

        userService.addUser("1", "user", "user", Role.USER);
        userService.addUser("2", "admin", "admin", Role.ADMIN);

        activityService.addActivity("101", "Yoga Class", "20", "Community Center", "RELAXATION", "A relaxing yoga session.", 200);
        activityService.addActivity("102", "Cooking Workshop", "15", "Kitchen Studio", "WORKSHOP", "Learn new cooking skills.", 100);
        activityService.addActivity("103", "Painting Class", "10", "Art Studio", "EDUCATIONAL", "Express your creativity through art.", 100);

        Activity yogaActivity = activityService.getActivityById("101");
        activityScheduleService.addActivitySchedule(
                "201", yogaActivity, LocalDate.now().plusDays(1).toString(), LocalTime.of(10, 0).toString(), LocalTime.of(12, 0).toString(), "20");

        eventService.addEvent("301", "Music Concert", "Open Air Theater", "100", "ENTERTAINMENT", "0",
                LocalDateTime.now().plusDays(2).toString(), LocalDateTime.now().plusDays(2).plusHours(3).toString(), 200);
        eventService.addEvent("302", "Tech Conference", "Convention Center", "200", "EDUCATIONAL", "0",
                LocalDateTime.now().plusWeeks(1).toString(), LocalDateTime.now().plusWeeks(1).plusDays(2).toString(), 100);
        eventService.addEvent("303", "Food Festival", "City Square", "150", "SOCIAL", "0",
                LocalDateTime.now().plusMonths(1).toString(), LocalDateTime.now().plusMonths(1).plusDays(1).toString(), 129);

        User testUser = userService.getUserById("1");
        testUser.increaseBalance(2000);
        Event concertEvent = eventService.getEventById("301");

        ticketService.addTicket("401", concertEvent, testUser, "Alice Smith");
        ticketService.addTicket("402", concertEvent, testUser, "Bob Johnson");

        Activity cookingActivity = activityService.getActivityById("102");
        activityScheduleService.addActivitySchedule(
                "202", cookingActivity, LocalDate.now().plusDays(2).toString(), LocalTime.of(14, 0).toString(), LocalTime.of(16, 0).toString(), "15");

        ActivitySchedule yogaSchedule = activityScheduleService.getScheduleById("201");
        ActivitySchedule cookingSchedule = activityScheduleService.getScheduleById("202");

// Adaugă booking-uri pentru utilizatori diferiți
        bookingService.addBooking("501", yogaSchedule, "Alice", "2"); // 2 persoane pentru Yoga
        bookingService.addBooking("502", yogaSchedule, "Bob", "3"); // 3 persoane pentru Yoga
        bookingService.addBooking("503", cookingSchedule, "Charlie", "4"); // 4 persoane pentru Cooking
        bookingService.addBooking("504", cookingSchedule, "Alice", "1");
        bookingService.addBooking("505", cookingSchedule, "Gia", "3");


        PresentationAdmin adminMenu = new PresentationAdmin(adminController);
        PresentationUser userMenu = new PresentationUser(userController);

        RoleBasedMenuService menuService = new RoleBasedMenuService(adminMenu, userMenu);

        LoginUI loginUI = new LoginUI(adminController, userController, menuService);
        loginUI.start();
    }

//    public static void startInFile() {
//                String activityFile = "activities.csv";
//                String activityScheduleFile = "activity_schedules.csv";
//                String bookingFile = "bookings.csv";
//                String eventFile = "events.csv";
//                String freeActivityFile = "free_activities.csv";
//                String paymentFile = "payments.csv";
//                String reservationFile = "reservations.csv";
//                String reviewFile = "reviews.csv";
//                String ticketFile = "tickets.csv";
//                String userFile = "users.csv";
//                String wishlistFile = "wishlists.csv";
//
//                FileRepository activityRepo = new FileRepository(activityFile, new Activity());
//                FileRepository activityScheduleRepo = new FileRepository(activityScheduleFile, new ActivitySchedule());
//                FileRepository bookingRepo = new FileRepository(bookingFile, new Booking());
//                FileRepository eventRepo = new FileRepository(eventFile, new Event());
//                FileRepository freeActivityRepo = new FileRepository(freeActivityFile, new FreeActivity());
//                FileRepository paymentRepo = new FileRepository(paymentFile, new Payment());
//                FileRepository reservationRepo = new FileRepository(reservationFile, new Reservation());
//                FileRepository reviewRepo = new FileRepository(reviewFile, new Review());
//                FileRepository ticketRepo = new FileRepository(ticketFile, new Ticket());
//                FileRepository userRepo = new FileRepository(userFile, new User());
//                FileRepository wishlistRepo = new FileRepository(wishlistFile, new Wishlist());
//
//                ActivityService activityService = new ActivityService(activityRepo);
//                ActivityScheduleService activityScheduleService = new ActivityScheduleService(activityScheduleRepo);
//                BookingService bookingService = new BookingService(bookingRepo);
//                EventService eventService = new EventService(eventRepo);
//                FreeActivityService freeActivityService = new FreeActivityService(freeActivityRepo);
//                PaymentService paymentService = new PaymentService(paymentRepo);
//                ReservationService reservationService = new ReservationService(reservationRepo);
//                ReviewService reviewService = new ReviewService(reviewRepo);
//                TicketService ticketService = new TicketService(ticketRepo);
//                UserService userService = new UserService(userRepo);
//                WishlistService wishlistService = new WishlistService(wishlistRepo);
//
//                AdminController adminController = new AdminController(
//                        activityService,
//                        userService,
//                        activityScheduleService,
//                        bookingService,
//                        eventService,
//                        freeActivityService,
//                        paymentService,
//                        reservationService,
//                        reviewService,
//                        ticketService,
//                        wishlistService
//                );
//                UserController userController = new UserController(
//                        activityService,
//                        userService,
//                        activityScheduleService,
//                        bookingService,
//                        eventService,
//                        freeActivityService,
//                        paymentService,
//                        reservationService,
//                        reviewService,
//                        ticketService,
//                        wishlistService
//                );
//
//                userService.addUser("1", "user", "user", Role.USER);
//                userService.addUser("2", "admin", "admin", Role.ADMIN);
//
//                activityService.addActivity("101", "Yoga Class", "20", "Community Center", "RELAXATION", "A relaxing yoga session.", 200);
//                activityService.addActivity("102", "Cooking Workshop", "15", "Kitchen Studio", "WORKSHOP", "Learn new cooking skills.", 100);
//                activityService.addActivity("103", "Painting Class", "10", "Art Studio", "EDUCATIONAL", "Express your creativity through art.", 100);
//
//                Activity yogaActivity = activityService.getActivityById("101");
//                activityScheduleService.addActivitySchedule(
//                        "201", yogaActivity, LocalDate.now().plusDays(1).toString(), LocalTime.of(10, 0).toString(), LocalTime.of(12, 0).toString(), "20");
//
//                eventService.addEvent("301", "Music Concert", "Open Air Theater", "100", "ENTERTAINMENT", "0",
//                        LocalDateTime.now().plusDays(2).toString(), LocalDateTime.now().plusDays(2).plusHours(3).toString(), 200);
//
//                PresentationAdmin adminMenu = new PresentationAdmin(adminController);
//                PresentationUser userMenu = new PresentationUser(userController);
//
//                RoleBasedMenuService menuService = new RoleBasedMenuService(adminMenu, userMenu);
//                LoginUI loginUI = new LoginUI(adminController, userController, menuService);
//                loginUI.start();
//            }

            public static void main(String[] args) {
                startInMemory();
//                startInFile();
            }
        }


