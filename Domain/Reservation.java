package Domain;

import Domain.ActivitySchedule;
import Domain.User;

import java.time.LocalDateTime;

public class Reservation {
    private int id;
    private User user;
    private ActivitySchedule activitySchedule;
    private int numberOfPeople;
    private LocalDateTime reservationDate;

    public Reservation(int id, User user, ActivitySchedule activitySchedule, int numberOfPeople, LocalDateTime reservationDate){
        this.id = id;
        this.user = user;
        this.activitySchedule = activitySchedule;
        this.numberOfPeople = numberOfPeople;
        this.reservationDate = reservationDate;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ActivitySchedule getActivitySchedule() {
        return activitySchedule;
    }

    public void setActivitySchedule(ActivitySchedule activitySchedule) {
        this.activitySchedule = activitySchedule;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }
}