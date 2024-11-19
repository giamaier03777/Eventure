package Service;

import Domain.Reservation;
import Domain.User;
import Domain.ActivitySchedule;
import Repository.IRepository;
import Repository.InMemoryRepo;

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
     * @param idString            the unique identifier of the reservation as a string.
     * @param user                the user making the reservation.
     * @param activitySchedule    the activity schedule being reserved.
     * @param numberOfPeopleString the number of people included in the reservation as a string.
     * @param reservationDateString the date and time of the reservation as a string in ISO-8601 format.
     * @throws IllegalArgumentException if validation fails or the reservation already exists.
     */
    public void addReservation(String idString, User user, ActivitySchedule activitySchedule, String numberOfPeopleString, String reservationDateString) {
        int id = Integer.parseInt(idString);
        int numberOfPeople = Integer.parseInt(numberOfPeopleString);
        LocalDateTime reservationDate = LocalDateTime.parse(reservationDateString);

        if (reservationRepo.read(id) != null) {
            throw new IllegalArgumentException("A reservation with this ID already exists.");
        }

        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        if (activitySchedule == null) {
            throw new IllegalArgumentException("Activity schedule cannot be null.");
        }

        if (numberOfPeople <= 0) {
            throw new IllegalArgumentException("Number of people must be a positive value.");
        }

        if (reservationDate == null || reservationDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Reservation date must be in the future.");
        }

        Reservation reservation = new Reservation(id, user, activitySchedule, numberOfPeople, reservationDate);
        reservationRepo.create(reservation);
    }

    /**
     * Retrieves a reservation by its ID.
     *
     * @param id the unique identifier of the reservation as a string.
     * @return the {@code Reservation} object if found.
     * @throws IllegalArgumentException if the reservation does not exist.
     */
    public Reservation getReservationById(String id) {
        Reservation reservation = reservationRepo.read(Integer.parseInt(id));
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation with the specified ID does not exist.");
        }
        return reservation;
    }

    /**
     * Updates an existing reservation in the repository.
     *
     * @param idString            the unique identifier of the reservation as a string.
     * @param user                the updated user making the reservation.
     * @param activitySchedule    the updated activity schedule being reserved.
     * @param numberOfPeopleString the updated number of people included in the reservation as a string.
     * @param reservationDateString the updated date and time of the reservation as a string in ISO-8601 format.
     * @throws IllegalArgumentException if validation fails or the reservation does not exist.
     */
    public void updateReservation(String idString, User user, ActivitySchedule activitySchedule, String numberOfPeopleString, String reservationDateString) {
        int id = Integer.parseInt(idString);
        int numberOfPeople = Integer.parseInt(numberOfPeopleString);
        LocalDateTime reservationDate = LocalDateTime.parse(reservationDateString);

        Reservation existingReservation = reservationRepo.read(id);

        if (existingReservation == null) {
            throw new IllegalArgumentException("Reservation with the specified ID does not exist.");
        }

        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        if (activitySchedule == null) {
            throw new IllegalArgumentException("Activity schedule cannot be null.");
        }

        if (numberOfPeople <= 0) {
            throw new IllegalArgumentException("Number of people must be a positive value.");
        }

        if (reservationDate == null || reservationDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Reservation date must be in the future.");
        }

        Reservation updatedReservation = new Reservation(id, user, activitySchedule, numberOfPeople, reservationDate);
        reservationRepo.update(updatedReservation);
    }

    /**
     * Deletes a reservation by its ID.
     *
     * @param id the unique identifier of the reservation as a string.
     * @throws IllegalArgumentException if the reservation does not exist.
     */
    public void deleteReservation(String id) {
        Reservation reservation = reservationRepo.read(Integer.parseInt(id));
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation with the specified ID does not exist.");
        }
        reservationRepo.delete(Integer.parseInt(id));
    }
}
