package Service;

import Domain.Reservation;
import Domain.User;
import Domain.ActivitySchedule;
import Repository.IRepository;
import Exception.*;

import java.time.LocalDateTime;

/**
 * Service class for managing reservations in the system.
 */
public class ReservationService {

    private final IRepository<Reservation> reservationRepo;

    /**
     * Constructs a new {@code ReservationService}.
     *
     * @param reservationRepo the repository for storing and managing reservations.
     */
    public ReservationService(IRepository<Reservation> reservationRepo) {
        this.reservationRepo = reservationRepo;
    }

    /**
     * Adds a new reservation to the repository.
     *
     * @param idString             the unique identifier of the reservation as a string.
     * @param user                 the user making the reservation.
     * @param activitySchedule     the activity schedule being reserved.
     * @param numberOfPeopleString the number of people included in the reservation as a string.
     * @param reservationDateString the date and time of the reservation as a string in ISO-8601 format.
     * @throws EntityAlreadyExistsException if the reservation already exists.
     * @throws ValidationException if validation fails.
     */
    public void addReservation(String idString, User user, ActivitySchedule activitySchedule, String numberOfPeopleString, String reservationDateString) {
        try {
            int id = Integer.parseInt(idString);
            int numberOfPeople = Integer.parseInt(numberOfPeopleString);
            LocalDateTime reservationDate = LocalDateTime.parse(reservationDateString);

            if (reservationRepo.read(id) != null) {
                throw new EntityAlreadyExistsException("A reservation with this ID already exists.");
            }

            validateReservationInputs(user, activitySchedule, numberOfPeople, reservationDate);

            Reservation reservation = new Reservation(id, user, activitySchedule, numberOfPeople, reservationDate);
            reservationRepo.create(reservation);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid number format for ID or number of people.", e);
        }
    }

    /**
     * Retrieves a reservation by its ID.
     *
     * @param id the unique identifier of the reservation as a string.
     * @return the {@code Reservation} object if found.
     * @throws EntityNotFoundException if the reservation does not exist.
     */
    public Reservation getReservationById(String id) {
        try {
            int reservationId = Integer.parseInt(id);
            Reservation reservation = reservationRepo.read(reservationId);
            if (reservation == null) {
                throw new EntityNotFoundException("Reservation with ID " + id + " not found.");
            }
            return reservation;
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format. ID must be a number: " + id, e);
        }
    }

    /**
     * Updates an existing reservation in the repository.
     *
     * @param idString             the unique identifier of the reservation as a string.
     * @param user                 the updated user making the reservation.
     * @param activitySchedule     the updated activity schedule being reserved.
     * @param numberOfPeopleString the updated number of people included in the reservation as a string.
     * @param reservationDateString the updated date and time of the reservation as a string in ISO-8601 format.
     * @throws EntityNotFoundException if the reservation does not exist.
     * @throws ValidationException if validation fails.
     */
    public void updateReservation(String idString, User user, ActivitySchedule activitySchedule, String numberOfPeopleString, String reservationDateString) {
        try {
            int id = Integer.parseInt(idString);
            int numberOfPeople = Integer.parseInt(numberOfPeopleString);
            LocalDateTime reservationDate = LocalDateTime.parse(reservationDateString);

            Reservation existingReservation = reservationRepo.read(id);
            if (existingReservation == null) {
                throw new EntityNotFoundException("Reservation with ID " + id + " not found.");
            }

            validateReservationInputs(user, activitySchedule, numberOfPeople, reservationDate);

            Reservation updatedReservation = new Reservation(id, user, activitySchedule, numberOfPeople, reservationDate);
            reservationRepo.update(updatedReservation);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid number format for ID or number of people.", e);
        }
    }

    /**
     * Deletes a reservation by its ID.
     *
     * @param id the unique identifier of the reservation as a string.
     * @throws EntityNotFoundException if the reservation does not exist.
     */
    public void deleteReservation(String id) {
        try {
            int reservationId = Integer.parseInt(id);
            if (reservationRepo.read(reservationId) == null) {
                throw new EntityNotFoundException("Reservation with ID " + id + " not found.");
            }
            reservationRepo.delete(reservationId);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format. ID must be a number: " + id, e);
        }
    }

    private void validateReservationInputs(User user, ActivitySchedule activitySchedule, int numberOfPeople, LocalDateTime reservationDate) {
        if (user == null) {
            throw new ValidationException("User cannot be null.");
        }
        if (activitySchedule == null) {
            throw new ValidationException("Activity schedule cannot be null.");
        }
        if (numberOfPeople <= 0) {
            throw new ValidationException("Number of people must be a positive value.");
        }
        if (reservationDate == null || reservationDate.isBefore(LocalDateTime.now())) {
            throw new ValidationException("Reservation date must be in the future.");
        }
    }
}
