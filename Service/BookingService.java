package Service;

import Domain.ActivitySchedule;
import Domain.Booking;
import Repository.BookingRepo;

public class BookingService {

    private final BookingRepo bookingRepo;

    public BookingService(BookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    public void addBooking(int id, ActivitySchedule schedule, String customerName, int numberOfPeople) {
        if (bookingRepo.read(id) != null) {
            throw new IllegalArgumentException("A booking with this ID already exists.");
        }

        if (schedule == null) {
            throw new IllegalArgumentException("Activity schedule cannot be null.");
        }

        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty.");
        }

        if (numberOfPeople <= 0) {
            throw new IllegalArgumentException("Number of people must be a positive number.");
        }

        if (schedule.getAvailableCapacity() < numberOfPeople) {
            throw new IllegalArgumentException("Insufficient capacity for the requested booking.");
        }

        Booking booking = new Booking(schedule, customerName, numberOfPeople);
        booking.setId(id);
        bookingRepo.create(booking);
    }

    public Booking getBookingById(int id) {
        return bookingRepo.read(id);
    }

    public void updateBooking(int id, ActivitySchedule schedule, String customerName, int numberOfPeople) {
        Booking existingBooking = bookingRepo.read(id);
        if (existingBooking == null) {
            throw new IllegalArgumentException("Booking with the specified ID does not exist.");
        }

        if (schedule == null) {
            throw new IllegalArgumentException("Activity schedule cannot be null.");
        }

        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty.");
        }

        if (numberOfPeople <= 0) {
            throw new IllegalArgumentException("Number of people must be a positive number.");
        }

        int capacityDifference = numberOfPeople - existingBooking.getNumberOfPeople();
        if (schedule.getAvailableCapacity() < capacityDifference) {
            throw new IllegalArgumentException("Insufficient capacity for the requested update.");
        }

        schedule.reduceCapacity(capacityDifference);
        existingBooking.setSchedule(schedule);
        existingBooking.setCustomerName(customerName);
        existingBooking.setNumberOfPeople(numberOfPeople);

        bookingRepo.update(existingBooking);
    }

    public void deleteBooking(int id) {
        Booking booking = bookingRepo.read(id);
        if (booking == null) {
            throw new IllegalArgumentException("Booking with the specified ID does not exist.");
        }

        ActivitySchedule schedule = booking.getSchedule();
        schedule.reduceCapacity(-booking.getNumberOfPeople()); // Restore the capacity
        bookingRepo.delete(id);
    }
}
