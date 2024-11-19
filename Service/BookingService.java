package Service;

import Domain.ActivitySchedule;
import Domain.Booking;
import Domain.ReviewableEntity;
import Repository.InMemoryRepo;

import java.util.*;

/**
 * Service class for managing bookings in the system.
 */
public class BookingService {

    private final InMemoryRepo<Booking> bookingRepo;

    /**
     * Constructs a new {@code BookingService}.
     *
     * @param bookingRepo the repository for storing and managing bookings.
     */
    public BookingService(InMemoryRepo<Booking> bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    /**
     * Adds a new booking to the repository.
     *
     * @param id            the unique identifier for the booking.
     * @param schedule      the activity schedule associated with the booking.
     * @param customerName  the name of the customer making the booking.
     * @param numberOfPeople the number of people included in the booking as a string.
     * @throws IllegalArgumentException if validation fails or the booking already exists.
     */
    public void addBooking(String id, ActivitySchedule schedule, String customerName, String numberOfPeople) {
        if (bookingRepo.read(Integer.parseInt(id)) != null) {
            throw new IllegalArgumentException("A booking with this ID already exists.");
        }

        if (schedule == null) {
            throw new IllegalArgumentException("Activity schedule cannot be null.");
        }

        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty.");
        }

        if (Integer.parseInt(numberOfPeople) <= 0) {
            throw new IllegalArgumentException("Number of people must be a positive number.");
        }

        if (schedule.getAvailableCapacity() < Integer.parseInt(numberOfPeople)) {
            throw new IllegalArgumentException("Insufficient capacity for the requested booking.");
        }

        Booking booking = new Booking(schedule, customerName, Integer.parseInt(numberOfPeople));
        booking.setId(Integer.parseInt(id));
        bookingRepo.create(booking);
    }

    /**
     * Retrieves a booking by its ID.
     *
     * @param id the unique identifier of the booking as a string.
     * @return the {@code Booking} object if found, or {@code null} if not found.
     */
    public Booking getBookingById(String id) {
        return bookingRepo.read(Integer.parseInt(id));
    }

    /**
     * Updates an existing booking in the repository.
     *
     * @param idString          the unique identifier of the booking as a string.
     * @param schedule          the updated activity schedule.
     * @param customerName      the updated customer name.
     * @param numberOfPeopleString the updated number of people as a string.
     * @throws IllegalArgumentException if the booking does not exist or validation fails.
     */
    public void updateBooking(String idString, ActivitySchedule schedule, String customerName, String numberOfPeopleString) {
        int id = Integer.parseInt(idString);
        int numberOfPeople = Integer.parseInt(numberOfPeopleString);
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

    /**
     * Deletes a booking by its ID.
     *
     * @param id the unique identifier of the booking as a string.
     * @throws IllegalArgumentException if the booking does not exist.
     */
    public void deleteBooking(String id) {
        Booking booking = bookingRepo.read(Integer.parseInt(id));
        if (booking == null) {
            throw new IllegalArgumentException("Booking with the specified ID does not exist.");
        }

        ActivitySchedule schedule = booking.getSchedule();
        schedule.reduceCapacity(-booking.getNumberOfPeople());
        bookingRepo.delete(Integer.parseInt(id));
    }

    /**
     * Retrieves a sorted map of the most popular reviewable entities based on bookings.
     *
     * @return a map where the keys are {@code ReviewableEntity} objects, and the values are the total number of participants.
     */
    public Map<ReviewableEntity, Integer> getMostPopularEntities() {
        List<Booking> bookings = bookingRepo.findAll();

        Map<ReviewableEntity, Integer> participationCount = new HashMap<>();

        for (Booking booking : bookings) {
            ReviewableEntity entity = booking.getSchedule().getActivity();
            int currentCount = participationCount.getOrDefault(entity, 0);
            participationCount.put(entity, currentCount + booking.getNumberOfPeople());
        }

        List<Map.Entry<ReviewableEntity, Integer>> sortedEntries = new ArrayList<>(participationCount.entrySet());
        sortedEntries.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        Map<ReviewableEntity, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<ReviewableEntity, Integer> entry : sortedEntries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}
