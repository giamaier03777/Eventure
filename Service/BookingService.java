package Service;

import Domain.ActivitySchedule;
import Domain.Booking;
import Domain.ReviewableEntity;
import Repository.IRepository;
import Exception.*;



import java.util.*;

/**
 * Service class for managing bookings in the system.
 */
public class BookingService {

    private final IRepository<Booking> bookingRepo;

    /**
     * Constructs a new {@code BookingService}.
     *
     * @param bookingRepo the repository for storing and managing bookings.
     */
    public BookingService(IRepository<Booking> bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    /**
     * Adds a new booking to the repository.
     *
     * @param id             the unique identifier for the booking.
     * @param schedule       the activity schedule associated with the booking.
     * @param customerName   the name of the customer making the booking.
     * @param numberOfPeople the number of people included in the booking as a string.
     * @throws ValidationException if validation fails.
     */
    public void addBooking(String id, ActivitySchedule schedule, String customerName, String numberOfPeople) {
        try {
            int bookingId = Integer.parseInt(id);
            int peopleCount = Integer.parseInt(numberOfPeople);


            validateBookingInputs(schedule, customerName, peopleCount);

            Booking booking = new Booking(schedule, customerName, peopleCount);
            booking.setId(bookingId);
            bookingRepo.create(booking);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid number format for ID or number of people.", e);
        }
    }

    /**
     * Retrieves a booking by its ID.
     *
     * @param id the unique identifier of the booking as a string.
     * @return the {@code Booking} object if found.
     * @throws EntityNotFoundException if the booking does not exist.
     */
    public Booking getBookingById(String id) {
        try {
            int bookingId = Integer.parseInt(id);
            Booking booking = bookingRepo.read(bookingId);
            if (booking == null) {
                throw new EntityNotFoundException("Booking with ID " + id + " not found.");
            }
            return booking;
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format. ID must be a number: " + id, e);
        }
    }

    /**
     * Updates an existing booking in the repository.
     *
     * @param idString           the unique identifier of the booking as a string.
     * @param schedule           the updated activity schedule.
     * @param customerName       the updated customer name.
     * @param numberOfPeopleString the updated number of people as a string.
     * @throws EntityNotFoundException if the booking does not exist.
     * @throws ValidationException if validation fails.
     */
    public void updateBooking(String idString, ActivitySchedule schedule, String customerName, String numberOfPeopleString) {
        try {
            int bookingId = Integer.parseInt(idString);
            int updatedPeopleCount = Integer.parseInt(numberOfPeopleString);

            Booking existingBooking = bookingRepo.read(bookingId);
            if (existingBooking == null) {
                throw new EntityNotFoundException("Booking with ID " + idString + " not found.");
            }

            validateBookingInputs(schedule, customerName, updatedPeopleCount);

            int capacityDifference = updatedPeopleCount - existingBooking.getNumberOfPeople();
            if (schedule.getAvailableCapacity() < capacityDifference) {
                throw new ValidationException("Insufficient capacity for the requested update.");
            }

            schedule.reduceCapacity(capacityDifference);
            existingBooking.setSchedule(schedule);
            existingBooking.setCustomerName(customerName);
            existingBooking.setNumberOfPeople(updatedPeopleCount);

            bookingRepo.update(existingBooking);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid number format for ID or number of people.", e);
        }
    }

    /**
     * Deletes a booking by its ID.
     *
     * @param id the unique identifier of the booking as a string.
     * @throws EntityNotFoundException if the booking does not exist.
     */
    public void deleteBooking(String id) {
        try {
            int bookingId = Integer.parseInt(id);
            Booking booking = bookingRepo.read(bookingId);
            if (booking == null) {
                throw new EntityNotFoundException("Booking with ID " + id + " not found.");
            }

            ActivitySchedule schedule = booking.getSchedule();
            schedule.reduceCapacity(-booking.getNumberOfPeople());
            bookingRepo.delete(bookingId);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format. ID must be a number: " + id, e);
        }
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

    private void validateBookingInputs(ActivitySchedule schedule, String customerName, int numberOfPeople) {
        if (schedule == null) {
            throw new ValidationException("Activity schedule cannot be null.");
        }
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new ValidationException("Customer name cannot be empty.");
        }
        if (numberOfPeople <= 0) {
            throw new ValidationException("Number of people must be a positive number.");
        }
        if (schedule.getAvailableCapacity() < numberOfPeople) {
            throw new ValidationException("Insufficient capacity for the requested booking.");
        }
    }
}