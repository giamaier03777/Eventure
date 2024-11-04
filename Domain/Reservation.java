import java.time.LocalDateTime;

public class Reservation {
    private User user;
    private ActivitySchedule activitySchedule;
    private int numberOfPeople;
    private LocalDateTime reservationDate;

    public(User user, ActivitySchedule activitySchedule, int numberOfPeople, LocalDateTime reservationDate){
        this.user = user;
        this.activitySchedule = activitySchedule;
        this.numberOfPeople = numberOfPeople;
        this.reservationDate = reservationDate;
    }

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