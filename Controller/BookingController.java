package Controller;

import Domain.ActivitySchedule;
import Domain.Booking;
import Service.BookingService;

public class BookingController {

    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public void addBooking(String idString, ActivitySchedule schedule, String customerName, String numberOfPeopleString) {
        try {
            int id = Integer.parseInt(idString);
            int numberOfPeople = Integer.parseInt(numberOfPeopleString);

            bookingService.addBooking(id, schedule, customerName, numberOfPeople);
            System.out.println("Booking added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID and number of people must be valid numbers.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public Booking getBookingById(String idString) {
        try {
            int id = Integer.parseInt(idString);
            return bookingService.getBookingById(id);
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
            return null;
        }
    }

    public void updateBooking(String idString, ActivitySchedule schedule, String customerName, String numberOfPeopleString) {
        try {
            int id = Integer.parseInt(idString);
            int numberOfPeople = Integer.parseInt(numberOfPeopleString);

            bookingService.updateBooking(id, schedule, customerName, numberOfPeople);
            System.out.println("Booking updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID and number of people must be valid numbers.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteBooking(String idString) {
        try {
            int id = Integer.parseInt(idString);
            bookingService.deleteBooking(id);
            System.out.println("Booking deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
