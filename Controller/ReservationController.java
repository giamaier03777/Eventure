package Controller;

import Domain.Reservation;
import Domain.User;
import Domain.ActivitySchedule;
import Service.ReservationService;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public void addReservation(String idString, User user, ActivitySchedule activitySchedule, String numberOfPeopleString, String reservationDateString) {
        try {
            int id = Integer.parseInt(idString);
            int numberOfPeople = Integer.parseInt(numberOfPeopleString);
            LocalDateTime reservationDate = LocalDateTime.parse(reservationDateString);

            reservationService.addReservation(id, user, activitySchedule, numberOfPeople, reservationDate);
            System.out.println("Reservation added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID and number of people must be valid numbers.");
        } catch (DateTimeParseException e) {
            System.out.println("Reservation date must be in a valid format (e.g., '2023-12-31T10:15:30').");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public Reservation getReservationById(String idString) {
        try {
            int id = Integer.parseInt(idString);
            return reservationService.getReservationById(id);
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
            return null;
        }
    }

    public void updateReservation(String idString, User user, ActivitySchedule activitySchedule, String numberOfPeopleString, String reservationDateString) {
        try {
            int id = Integer.parseInt(idString);
            int numberOfPeople = Integer.parseInt(numberOfPeopleString);
            LocalDateTime reservationDate = LocalDateTime.parse(reservationDateString);

            reservationService.updateReservation(id, user, activitySchedule, numberOfPeople, reservationDate);
            System.out.println("Reservation updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID and number of people must be valid numbers.");
        } catch (DateTimeParseException e) {
            System.out.println("Reservation date must be in a valid format (e.g., '2023-12-31T10:15:30').");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteReservation(String idString) {
        try {
            int id = Integer.parseInt(idString);
            reservationService.deleteReservation(id);
            System.out.println("Reservation deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
