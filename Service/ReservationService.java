package Service;

import Domain.Reservation;
import Domain.User;
import Domain.ActivitySchedule;
import Repository.ReservationRepo;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationService {

    private ReservationRepo reservationRepo;

    public ReservationService(ReservationRepo reservationRepo) {
        this.reservationRepo = reservationRepo;
    }


    public void addReservation(int id, User user, ActivitySchedule activitySchedule, int numberOfPeople, LocalDateTime reservationDate) {

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

    public Reservation getReservationById(int id) {
        Reservation reservation = reservationRepo.read(id);
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation with the specified ID does not exist.");
        }
        return reservation;
    }


    public void updateReservation(int id, User user, ActivitySchedule activitySchedule, int numberOfPeople, LocalDateTime reservationDate) {
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

    public void deleteReservation(int id) {
        Reservation reservation = reservationRepo.read(id);
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation with the specified ID does not exist.");
        }
        reservationRepo.delete(id);
    }

}
